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

package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

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
        plugin.registerConfig(config);
        config.registerHandler(this);
        update();
        loadLocales();
        updateLocales();
    }

    private void loadLocales() {
        for(String localeName : localeSettings.getLocales()) {
            Locale locale = new Locale(plugin, localeName);
            registerLocale(locale);
        }
        localeMap.get(localeSettings.getDefaultLocale()).
                addUser(plugin.getServer().getConsoleSender());
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
        if(!localeMap.containsKey(localeName)) return false;
        getLocale(user).removeUser(user);
        localeMap.get(localeName).addUser(user);
        fileConfig.set(user.getName(), localeName);
        config.save();
        return true;
    }

    public void updateLocale(Player player) {
        Locale locale = getLocale(player);
        if(player.isOnline()) locale.addUser(player);
        else locale.removeUser(player);
    }

    public void updateLocales() {
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            updateLocale(player);
        }
    }

    @Override
    public void update() {
        fileConfig = config.getConfig();
    }
}
