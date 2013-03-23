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

    private boolean isRunning = false;
    private boolean isCooldown = false;
    private long cooldownRemaining;

    private Auction currentAuction = null;
    private static long TICKS_PER_SECOND = 20;
    private Integer timerID;

    AuctionSettings auctionSettings;

    private class QueueConfig extends Configuration {
        public QueueConfig(Salesmania plugin) {
            super(plugin, "auctionQueue.yml");
        }

        protected void loadQueue(AuctionQueue queue) {
        }

        // TODO implement saving and loading of queue
        protected void saveAuction(Auction auction) {
            HashMap<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("itemstack", auction.getItemStack());
            dataMap.put("bid", auction.getBid());
            dataMap.put("winner", auction.getWinner());
            config.createSection(auction.getOwner().getName(), dataMap);
            save();
            plugin.getLogger().info("Auction saved");
        }

        protected void removeAuction(Auction auction) {
            config.set(auction.getOwner().getName(), null);
            save();
            plugin.getLogger().info("Auction removed");
        }
    }

    public AuctionQueue(Salesmania plugin) {
        this.plugin = plugin;
        queueConfig = new QueueConfig(plugin);
        queueConfig.loadQueue(this);
        auctionSettings = plugin.getSettings().getAuctionSettings();
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if(currentAuction == null) {
                if(size() == 0) stop();
            }
            else {
                if(isCooldown) {
                    if(cooldownRemaining > 0) {
                        cooldownRemaining--;
                    } else {
                        isCooldown = false;
                    }
                }

                else if(currentAuction.isRunning()) {
                    currentAuction.timerTick();
                } else {
                    nextAuction();
                }
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

    public boolean nextAuction() {
        if(size() != 0) {
            currentAuction = peek();
            if(!isCooldown) currentAuction.start();
        } else {
            currentAuction = null;
            return false;
        }
        return true;
    }

    public void startCooldown() {
        cooldownRemaining = plugin.getSettings().getAuctionSettings().getCooldown();
        isCooldown = true;
    }

    public void start() {
        if(!isRunning) {
            isRunning = true;
            if(size() != 0) {
                plugin.getServer().getPluginManager().callEvent(new AuctionEvent(null, AuctionEvent.EventType.QUEUE_STARTED));
                currentAuction = peek();
                currentAuction.start();
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
            queueConfig.saveAuction(auction);
            plugin.getServer().getPluginManager().callEvent(new AuctionEvent(auction, AuctionEvent.EventType.QUEUED));
            if(auctionSettings.getEnabled() && !isRunning) {
                start();
            }
            return true;
        }
        return false;
    }

    @Override
    public Auction remove() {
        Auction auction = super.remove();
        queueConfig.removeAuction(auction);
        return auction;
    }
}
