package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Owner: Justin
 * Date: 5/16/12
 * Time: 7:29 PM
 */
public class Locale extends Configuration {
    Salesmania salesMania;
    public Locale(JavaPlugin plugin) {
        super(plugin);
        setFilename(salesMania.getSettings().getLocale() + ".yml");
        init();
    }


}
