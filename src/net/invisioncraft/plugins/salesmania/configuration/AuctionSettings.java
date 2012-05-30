package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Owner: Justin
 * Date: 5/29/12
 * Time: 4:07 PM
 */
public class AuctionSettings {
    private FileConfiguration config;
    protected AuctionSettings(Settings settings) {
        config = settings.getConfig();
    }

    public boolean getAllowCreative() {
        return config.getBoolean("Auction.allowCreative");
    }

    // Bidding
    public long getCooldown() {
        return config.getLong("Auction.cooldown");
    }

    public float getMinStart() {
        return config.getLong("Auction.minStart");
    }

    public float getMaxStart() {
        return config.getLong("Auction.maxStart");
    }

    public float getMinIncrement() {
        return config.getInt("Auction.Bidding.minIncrement");
    }

    public float getMaxIncrement() {
        return config.getInt("Auction.Bidding.maxIncrement");
    }

    public int getDefaultTime() {
        return config.getInt("Auction.Bidding.defaultTime");
    }

    public long getMaxTime() {
        return config.getLong("Auction.maxTime");
    }

    public List<Long> getNofityTime() {
        return config.getLongList("Auction.notifyTime");
    }

    public int getSnipeTime() {
        return config.getInt("Auction.Bidding.snipeTime");
    }

    public List<String> getBlacklist() {
        return config.getStringList("Auction.Blacklist");
    }

}