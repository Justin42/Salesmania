package net.invisioncraft.plugins.salesmania;

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

    private class QueueConfig extends Configuration {
        public QueueConfig(Salesmania plugin) {
            super(plugin, "auctionQueue.yml");
        }

        public void loadQueue(AuctionQueue queue) {
        }

        public void saveAuction(Auction auction) {
            HashMap dataMap = new HashMap<String, Object>();
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

    @Override
    public boolean add(Auction auction) {
        if(super.add(auction)) {
            plugin.getServer().getPluginManager().callEvent(new AuctionEvent(auction, AuctionEvent.EventType.QUEUED));
            return true;
        }
        return false;
    }
}
