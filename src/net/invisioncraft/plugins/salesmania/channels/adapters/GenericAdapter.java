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

package net.invisioncraft.plugins.salesmania.channels.adapters;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.AuctionIgnoreList;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GenericAdapter implements ChannelAdapter {
    private Salesmania plugin;
    private AuctionIgnoreList auctionIgnoreList;

    public GenericAdapter(Salesmania plugin) {
        this.plugin = plugin;
        auctionIgnoreList = plugin.getAuctionIgnoreList();
    }

    @Override
    public void broadcast(String channelName, String[] message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String[] message) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String[] message, ArrayList<Player> players) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String message, ArrayList<Player> players) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
