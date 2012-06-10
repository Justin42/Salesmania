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

import org.bukkit.configuration.file.FileConfiguration;

public class DatabaseSettings implements ConfigurationHandler {
    private FileConfiguration config;
    private Settings settings;
    protected DatabaseSettings(Settings settings) {
        this.settings = settings;
        update();
    }

    public String getMysqlHost() {
        return config.getString("Auction.Logging.mysqlHost");
    }

    public int getMysqlPort() {
        return config.getInt("Auction.Logging.mysqlPort");
    }

    public String getMysqlUsername() {
        return config.getString("Auction.Logging.mysqlUsername");
    }

    public String getMysqlPassword() {
        return config.getString("Auction.Logging.mysqlPassword");
    }

    public String getMysqlDatabase() {
        return config.getString("Auction.Logging.mysqlDatabase");
    }

    public String getMysqlTable() {
        return config.getString("Auction.Logging.mysqlTable");
    }

    @Override
    public void update() {
        config = settings.getConfig();
    }
}
