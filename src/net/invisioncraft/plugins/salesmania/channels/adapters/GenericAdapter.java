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

    public void broadcast(String channelName, String[] message) {

    }

    @Override
    public void broadcast(WorldGroup worldGroup, String[] message) {
        for(Player player : worldGroup.getPlayers()) {
            if(!auctionIgnoreList.isIgnored(player)) {
                player.sendMessage(message);
            }
        }
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String message) {
        broadcast(worldGroup, new String[]{message});
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String[] message, ArrayList<Player> players) {
        for(Player player : worldGroup.getPlayers()) {
            if(players.contains(player) && auctionIgnoreList.isIgnored(player)) {
                player.sendMessage(message);
            }
        }
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String message, ArrayList<Player> players) {
        broadcast(worldGroup, new String[]{message}, players);
    }
}
