package net.invisioncraft.plugins.salesmania.configuration;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 5/29/12
 * Time: 7:49 PM
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
public class LogSettings implements ConfigurationHandler {
    private FileConfiguration config;
    private Settings settings;

    protected LogSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void update() {
        config = settings.getConfig();
    }
}
