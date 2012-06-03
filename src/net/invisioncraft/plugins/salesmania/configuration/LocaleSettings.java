package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 5/29/12
 * Time: 7:32 PM
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
public class LocaleSettings implements ConfigurationHandler {
    private FileConfiguration config;
    private Settings settings;
    protected LocaleSettings(Settings settings) {
        this.settings = settings;
        update();
    }

    public String getDefaultLocale() {
        return config.getString("Main.defaultLocale");
    }

    public List<String> getLocales() {
        return config.getStringList("Main.availableLocale");
    }

    @Override
    public void update() {
        config = settings.getConfig();
    }
}
