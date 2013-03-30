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

public class TownyChatAdapter implements ChannelAdapter {
    Chat townyChat;
    Salesmania plugin;

    public TownyChatAdapter(Salesmania plugin) {
        townyChat = (Chat) plugin.getServer().getPluginManager().getPlugin("TownyChat");
        this.plugin = plugin;
    }

    @Override
    public void broadcast(String channelName, String[] message, AuctionIgnoreList ignoreList) {
        Channel channel = townyChat.getChannelsHandler().getChannel(channelName);
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if(channel.isPresent(player.getName())) {
                if(ignoreList != null && ignoreList.isIgnored(player)) continue;
                player.sendMessage(message);
            }
        }
    }

    @Override
    public void broadcast(WorldGroup worldGroup, String[] message, AuctionIgnoreList ignoreList) {
        for(String channelName : worldGroup.getChannels()) {
            broadcast(channelName, message, ignoreList);
        }
    }

}
