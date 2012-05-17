package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.plugin.java.JavaPlugin;

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

    public String getLocale() {
        return getConfig().getString("Main.locale");
    }
}
