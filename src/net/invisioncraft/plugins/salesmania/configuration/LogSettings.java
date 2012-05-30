package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Owner: Justin
 * Date: 5/29/12
 * Time: 7:49 PM
 */
public class LogSettings {
    private FileConfiguration config;
    protected LogSettings(Settings settings) {
        config = settings.getConfig();
    }
}
