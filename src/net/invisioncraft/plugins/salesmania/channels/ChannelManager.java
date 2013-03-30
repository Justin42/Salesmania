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

package net.invisioncraft.plugins.salesmania.channels;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.channels.adapters.ChannelAdapter;
import net.invisioncraft.plugins.salesmania.channels.adapters.GenericAdapter;
import net.invisioncraft.plugins.salesmania.channels.adapters.HeroChatAdapter;
import net.invisioncraft.plugins.salesmania.channels.adapters.TownyChatAdapter;
import net.invisioncraft.plugins.salesmania.configuration.AuctionIgnoreList;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import org.bukkit.plugin.PluginManager;

public class ChannelManager {
    private Salesmania plugin;
    private ChannelAdapter channelAdapter;

    public ChannelManager(Salesmania plugin) {
        this.plugin = plugin;

        // Channel support
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        if(pluginManager.getPlugin("TownyChat") != null && pluginManager.getPlugin("TownyChat").isEnabled()) {
            channelAdapter = new TownyChatAdapter(plugin);
            plugin.getLogger().info("TownyChat channel support enabled.");
        }
        else if(pluginManager.getPlugin("Herochat") != null && pluginManager.getPlugin("Herochat").isEnabled()) {
            channelAdapter = new HeroChatAdapter(plugin);
            plugin.getLogger().info("Herochat channel support enabled.");
        }
        else {
            channelAdapter = new GenericAdapter(plugin);
            plugin.getLogger().info("No channel support detected, using world specific broadcasts instead.");
        }
    }

    // Wrapper methods
    public void broadcast(String channelName, String[] message, AuctionIgnoreList ignoreList) {
        channelAdapter.broadcast(channelName, message, ignoreList);
    }


    public void broadcast(WorldGroup worldGroup, String[] message, AuctionIgnoreList ignoreList) {
        channelAdapter.broadcast(worldGroup, message, ignoreList);
    }
}
