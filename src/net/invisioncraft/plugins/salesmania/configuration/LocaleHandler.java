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
/*
Copyright 2012 Byte 2 O Software LLC
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
