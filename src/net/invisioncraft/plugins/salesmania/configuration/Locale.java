package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Owner: Justin
 * Date: 5/16/12
 * Time: 7:29 PM
 */
public class Locale  {
    Salesmania salesMania;
    private static FileConfiguration config;

    public Locale(Salesmania plugin, String locale) {
        config = new Configuration(plugin, locale + ".yml").getConfig();
    }

    public String getMessage(String path) {
        return config.getString(path);
    }
}
