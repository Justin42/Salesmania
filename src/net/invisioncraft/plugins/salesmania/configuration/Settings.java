package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Owner: Justin
 * Date: 5/16/12
 * Time: 7:20 PM
 */
public class Settings extends Configuration {

    public Settings(JavaPlugin plugin) {
        super(plugin);
        setFilename("config.yml");
        init();
    }

    // Main
    public String getLocale() {
        return getConfig().getString("Main.locale");
    }

    public boolean getAllowCreative() {
        return getConfig().getBoolean("Main.allow creative");
    }

    // Bidding
    public int getMinStart() {
        return getConfig().getInt("Bidding.min start");
    }

    public int getMaxStart() {
        return getConfig().getInt("Bidding.max start");
    }

    public int getMinIncrement() {
        return getConfig().getInt("Bidding.min increment");
    }

    public int getMaxIncrement() {
        return getConfig().getInt("Bidding.max increment");
    }

    public int getDefaultTime() {
        return getConfig().getInt("Bidding.default time");
    }

    public int getSnipeTime() {
        return getConfig().getInt("Bidding.snipe time");
    }

    // Logging
    public boolean isLoggingEnabled() {
        return getConfig().getBoolean("Logging.enabled");
    }

    public String getLogFilename() {
        return getConfig().getString("Logging.filename");
    }

    public String getLogType() {
        return getConfig().getString("Logging.type");
    }

    public String getMysqlHost() {
        return getConfig().getString("Logging.mysql host");
    }

    public int getMysqlPort() {
        return getConfig().getInt("Logging.mysql port");
    }

    public String getMysqlUsername() {
        return getConfig().getString("Logging.mysql username");
    }

    public String getMysqlPassword() {
        return getConfig().getString("Logging.mysql password");
    }

    public String getMysqlDatabase() {
        return getConfig().getString("Logging.mysql database");
    }

    public String getMysqlTable() {
        return getConfig().getString("Logging.mysql table");
    }

    // Blacklist
    public List<String> getBlacklist() {
        return getConfig().getStringList("Blacklist");
    }
}
