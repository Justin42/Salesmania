package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;

/**
 * Owner: Justin
 * Date: 5/16/12
 * Time: 7:29 PM
 */
public class Locale  {
    Salesmania salesMania;
    private static Configuration config;

    public static void init(Salesmania plugin, String locale) {
        config = new Configuration(plugin, locale + ".yml");
    }

    public static String getMessage(String path) {
        return config.getConfig().getString(path);
    }


}
