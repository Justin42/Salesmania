package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Owner: Justin
 * Date: 5/29/12
 * Time: 7:32 PM
 */
public class LocaleSettings {
    private FileConfiguration config;
    protected LocaleSettings(Settings settings) {
        config = settings.getConfig();
    }

    public String getDefaultLocale() {
        return config.getString("Main.defaultLocale");
    }

    public List<String> getLocales() {
        return config.getStringList("Main.availableLocale");
    }
}
