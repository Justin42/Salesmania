package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Owner: Justin
 * Date: 5/16/12
 * Time: 7:20 PM
 */
public class Settings extends Configuration {
    private AuctionSettings auctionSettings;
    private DatabaseSettings databaseSettings;
    private LocaleSettings localeSettings;
    private LogSettings logSettings;
    public Settings(JavaPlugin plugin) {
        super(plugin, "config.yml");
        auctionSettings = new AuctionSettings(this);
        databaseSettings = new DatabaseSettings(this);
        localeSettings = new LocaleSettings(this);
        logSettings = new LogSettings(this);
    }

    public AuctionSettings getAuctionSettings() {
        return auctionSettings;
    }

    public DatabaseSettings getDatabaseSettings() {
        return databaseSettings;
    }

    public LocaleSettings getLocaleSettings() {
        return localeSettings;
    }

    public LogSettings getLogSettings() {
        return logSettings;
    }
}
