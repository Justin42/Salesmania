package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 5/22/12
 * Time: 5:51 AM
 */
public class LocaleHandler {
    private static FileConfiguration config;
    private Salesmania plugin;
    private HashMap<String, Locale> localeMap;
    private LocaleSettings localeSettings;
    public LocaleHandler(Salesmania plugin) {
        this.plugin = plugin;
        localeMap = new HashMap<String, Locale>();
        localeSettings = plugin.getSettings().getLocaleSettings();
        localeMap.put(localeSettings.getDefaultLocale(), new Locale(plugin, localeSettings.getDefaultLocale()));
        config = new Configuration(plugin, "playerLocale.yml").getConfig();
        loadLocales();
    }

    public void loadLocales() {
        for(String localeName : localeSettings.getLocales()) {
            getLocale(localeName);
        }
    }

    public Locale getLocale(CommandSender sender) {
        String localeName;
        if(config.contains(sender.getName())) localeName = config.getString(sender.getName());
        else localeName = localeSettings.getDefaultLocale();
        return getLocale(localeName);

    }

    public boolean setLocale(CommandSender sender, String locale) {
        if(localeSettings.getLocales().contains(locale)) {
            config.set(sender.getName(), locale);
            return true;
        }
        else return false;
    }

    public Locale getDefaultLocale() {
        return localeMap.get(localeSettings.getDefaultLocale());
    }

    private Locale getLocale(String localeName) {
        Locale locale = localeMap.get(localeName);
        if(locale == null) {
            if(localeSettings.getLocales().contains(localeName)) {
                return localeMap.put(localeName, new Locale(plugin, localeName));
            }
            else return localeMap.get(localeSettings.getDefaultLocale());
        }
        else return locale;
    }
}
