package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
public class LocaleHandler implements ConfigurationHandler {
    private FileConfiguration fileConfig;
    private Configuration config;
    private Salesmania plugin;
    private LocaleSettings localeSettings;
    HashMap<String, Locale> localeMap;

    public LocaleHandler(Salesmania plugin) {
        this.plugin = plugin;
        localeSettings = plugin.getSettings().getLocaleSettings();
        localeMap = new HashMap<String, Locale>();
        config = new Configuration(plugin, "playerLocale.yml");
        config.registerHandler(this);
        update();
        loadLocales();
    }

    private void loadLocales() {
        for(String localeName : localeSettings.getLocales()) {
            Locale locale = new Locale(plugin, localeName);
            registerLocale(locale);
        }
    }

    public void registerLocale(Locale locale) {
        plugin.getLogger().info(String.format(
                "Registered locale '%s'", locale.getName()));
        localeMap.put(locale.getName(), locale);
    }

    public Locale[] getLocales() {
        return localeMap.values().toArray(new Locale[0]);
    }

    public Locale getLocale(CommandSender user) {
        String localeName;
        if(fileConfig.contains(user.getName())) localeName = fileConfig.getString(user.getName());
        else localeName = localeSettings.getDefaultLocale();
        return localeMap.get(localeName);
    }

    public boolean setLocale(CommandSender user, String localeName) {
        if(localeMap.containsKey(localeName)) return false;
        for(Locale locale : getLocales()) locale.removeUser(user);
        localeMap.get(localeName).addUser(user);
        fileConfig.set(user.getName(), localeName);
        return true;
    }

    public void updateLocale(Player player) {
        Locale locale = getLocale(player);
        if(player.isOnline()) locale.addUser(player);
        else locale.removeUser(player);
    }

    @Override
    public void update() {
        fileConfig = config.getConfig();
    }
}
