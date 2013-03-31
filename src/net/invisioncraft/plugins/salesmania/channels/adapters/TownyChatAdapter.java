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

import com.palmergames.bukkit.TownyChat.Chat;
import com.palmergames.bukkit.TownyChat.channels.Channel;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.AuctionIgnoreList;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TownyChatAdapter implements ChannelAdapter {
    private Chat townyChat;
    private Salesmania plugin;
    private AuctionIgnoreList auctionIgnoreList;

    public TownyChatAdapter(Salesmania plugin) {
        townyChat = (Chat) plugin.getServer().getPluginManager().getPlugin("TownyChat");
        this.plugin = plugin;
        auctionIgnoreList = plugin.getAuctionIgnoreList();
    }

    public void broadcast(String channelName, String[] message) {
        Channel channel = townyChat.getChannelsHandler().getChannel(channelName);
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if(channel.isPresent(player.getName())) {
                if(auctionIgnoreList.isIgnored(player)) continue;
                player.sendMessage(message);
            }
        }
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String[] message) {
        for(String channelName : worldGroup.getChannels()) {
            broadcast(channelName, message);
        }
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String message) {
        broadcast(worldGroup, new String[]{message});
    }

    // TODO This is a problem...
    @Override
    public void broadcast(WorldGroup worldGroup, String[] message, ArrayList<Player> playerList) {
        for(String channelName : worldGroup.getChannels()) {
            Channel channel = townyChat.getChannelsHandler().getChannel(channelName);
            for(Player player : worldGroup.getPlayers()) {
                if(channel.isPresent(player.getName()) && playerList.contains(player) && !auctionIgnoreList.isIgnored(player)) {
                    player.sendMessage(message);
                }
            }
        }
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String message, ArrayList<Player> players) {
        broadcast(worldGroup, new String[]{message}, players);
    }

}
