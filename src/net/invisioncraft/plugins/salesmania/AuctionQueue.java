package net.invisioncraft.plugins.salesmania;

import net.invisioncraft.plugins.salesmania.configuration.AuctionSettings;
import net.invisioncraft.plugins.salesmania.configuration.Configuration;
import net.invisioncraft.plugins.salesmania.configuration.ConfigurationHandler;
import net.invisioncraft.plugins.salesmania.event.AuctionEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Justin
 * Date: 3/19/13
 * Time: 9:17 PM
 */
public class AuctionQueue extends LinkedList<Auction> {
    private Salesmania plugin;
    private QueueConfig queueConfig;

    private boolean isRunning;
    private boolean isCooldown = false;
    private long cooldownRemaining;

    private Auction currentAuction = null;
    private static long TICKS_PER_SECOND = 20;
    private Integer timerID;

    private class QueueConfig extends Configuration {
        public QueueConfig(Salesmania plugin) {
            super(plugin, "auctionQueue.yml");
        }

        public void loadQueue(AuctionQueue queue) {
        }

        public void saveAuction(Auction auction) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("itemstack", auction.getItemStack());
            dataMap.put("bid", auction.getBid());
            dataMap.put("winner", auction.getWinner());
            config.set(auction.getOwner().getName(), dataMap);
            save();
        }

        public void removeAuction(Auction auction) {
            ConfigurationSection section = config.getConfigurationSection(auction.getOwner().getName());
            section.set("itemstack", null);
            section.set("bid", null);
            section.set("winner", null);
            save();
        }
    }

    public AuctionQueue(Salesmania plugin) {
        this.plugin = plugin;
        queueConfig = new QueueConfig(plugin);
        queueConfig.loadQueue(this);
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if(currentAuction.isRunning()) {
                currentAuction.timerTick();
            }
            else { // If current auction isn't running, we can safely assume it has ended.
                nextAuction();
            }
            if(isCooldown) {
                if(cooldownRemaining > 0) {
                    cooldownRemaining--;
                }
                else isCooldown = false;
            }
        }
    };

    public void load() {
        queueConfig.loadQueue(this);
    }

    public int playerSize(Player player) {
        int count = 0;
        for(Auction auction : this) {
            if(auction.getOwner().equals(player)) count++;
        }
        return count;
    }

    public void nextAuction() {
        poll();
        if(size() != 0) {
            currentAuction = peek();
        } else currentAuction = null;
        cooldownRemaining = plugin.getSettings().getAuctionSettings().getCooldown();
        if(cooldownRemaining != 0) {
            isCooldown = true;
        }
    }

    // TODO implement start and stop methods + scheduling
    public void start() {
        if(!isRunning) {
            isRunning = true;
            if(size() != 0) {
                currentAuction = peek();
                plugin.getServer().getPluginManager().callEvent(new AuctionEvent(null, AuctionEvent.EventType.QUEUE_STARTED));
                timerID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, timerRunnable, TICKS_PER_SECOND, TICKS_PER_SECOND);
            }
        }
    }

    public void stop() {
        if(isRunning) {
            plugin.getServer().getScheduler().cancelTask(timerID);
            plugin.getServer().getPluginManager().callEvent(new AuctionEvent(null, AuctionEvent.EventType.QUEUE_STOPPED));
            isRunning = false;
        }
    }

    public Auction getCurrentAuction() {
        return currentAuction;
    }

    @Override
    public boolean add(Auction auction) {
        if(super.add(auction)) {
            plugin.getServer().getPluginManager().callEvent(new AuctionEvent(auction, AuctionEvent.EventType.QUEUED));
            return true;
        }
        return false;
    }
}
