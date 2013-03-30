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
import net.invisioncraft.plugins.salesmania.channels.adapters.HeroChatAdapter;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroupManager;

public class ChannelManager {
    ChannelAdapter channelAdapter;
    WorldGroupManager worldGroupManager;

    public ChannelManager(Salesmania plugin) {
        worldGroupManager = plugin.getWorldGroupManager();
        channelAdapter = new HeroChatAdapter(); // Specific adapter should be detected during plugin loading
    }

    public broadcast(WorldGroup worldGroup, String message) {
        for(String channelName : worldGroup.getChannels()) {
            channelAdapter.broadcast(channelName, message);
        }
    }

}
