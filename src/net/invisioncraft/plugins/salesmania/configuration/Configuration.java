package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;

/**
 * Owner: Justin
 * Date: 5/16/12
 * Time: 10:00 PM
 */
public class Configuration {
    private FileConfiguration customConfig;
    private File customConfigFile;
    private JavaPlugin plugin;
    private String filename;

    public Configuration(JavaPlugin plugin, String filename) {
        this.plugin = plugin;
        this.filename = filename;
        reload();
    }

    public String getFilename() {
        return filename;
    }

    public void reload() {
        if(customConfigFile == null) {
            customConfigFile = new File(plugin.getDataFolder(), filename);
        }
        customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

        InputStream defaultConfigStream = plugin.getResource(filename);
        if(defaultConfigStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigStream);
            customConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if(customConfig == null) reload();
        return customConfig;
    }
}
