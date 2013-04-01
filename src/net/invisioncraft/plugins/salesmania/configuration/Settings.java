/*
This file is part of Salesmania.

    Salesmania is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Salesmania is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Salesmania.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;

public class Settings extends Configuration {
    private AuctionSettings auctionSettings;
    private DatabaseSettings databaseSettings;
    private LocaleSettings localeSettings;
    private LogSettings logSettings;
    private WorldGroupSettings worldGroupSettings;
    private AuctionQueueSettings auctionQueueSettings;

    public Settings(Salesmania plugin) {
        super(plugin, "config.yml");

        auctionSettings = new AuctionSettings(this);
        databaseSettings = new DatabaseSettings(this);
        localeSettings = new LocaleSettings(this);
        logSettings = new LogSettings(this);
        worldGroupSettings = new WorldGroupSettings(this);

        auctionQueueSettings = new AuctionQueueSettings(plugin);

        registerHandler(auctionSettings);
        registerHandler(databaseSettings);
        registerHandler(localeSettings);
        registerHandler(logSettings);
        registerHandler(worldGroupSettings);
    }

    public AuctionSettings getAuctionSettings() {
        return auctionSettings;
    }

    public AuctionQueueSettings getAuctionQueueSettings() {
        return auctionQueueSettings;
    }

    public DatabaseSettings getDatabaseSettings() {
        return databaseSettings;
    }

    public LocaleSettings getLocaleSettings() {
        return localeSettings;
    }

    public LogSettings getLogSettings() {
        return logSettings;
    }

    public WorldGroupSettings getWorldGroupSettings() {
        return worldGroupSettings;
    }
}
