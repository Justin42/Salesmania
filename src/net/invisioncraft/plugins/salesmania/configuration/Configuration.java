package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * Owner: Justin
 * Date: 5/16/12
 * Time: 10:00 PM
 */
public class Configuration {
    private FileConfiguration customConfig;
    private File customConfigFile;
    private Salesmania plugin;
    private String filename;

    public Configuration(Salesmania plugin, String filename) {
        this.plugin = plugin;
        this.filename = filename;
        plugin.registerConfig(this);
        reload();
        save();
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
            customConfig.options().copyDefaults(true);
        }
    }

    public FileConfiguration getConfig() {
        if(customConfig == null) reload();
        return customConfig;
    }

    public void save() {
        if (customConfig == null || customConfigFile == null) {
            return;
        }
        try {
            customConfig.save(customConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + customConfigFile, ex);
        }
    }

}
