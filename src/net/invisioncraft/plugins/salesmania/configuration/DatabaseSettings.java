package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Owner: Justin
 * Date: 5/29/12
 * Time: 9:37 PM
 */
public class DatabaseSettings {
    private FileConfiguration config;
    public DatabaseSettings(Settings settings) {
        config = settings.getConfig();
    }

    public String getMysqlHost() {
        return config.getString("Auction.Logging.mysqlHost");
    }

    public int getMysqlPort() {
        return config.getInt("Auction.Logging.mysqlPort");
    }

    public String getMysqlUsername() {
        return config.getString("Auction.Logging.mysqlUsername");
    }

    public String getMysqlPassword() {
        return config.getString("Auction.Logging.mysqlPassword");
    }

    public String getMysqlDatabase() {
        return config.getString("Auction.Logging.mysqlDatabase");
    }

    public String getMysqlTable() {
        return config.getString("Auction.Logging.mysqlTable");
    }

}
