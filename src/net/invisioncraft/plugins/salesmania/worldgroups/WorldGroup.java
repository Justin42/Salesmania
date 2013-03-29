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

package net.invisioncraft.plugins.salesmania.worldgroups;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;

public class WorldGroup {
    ArrayList<World> worldList;
    Salesmania plugin;

    public WorldGroup(Salesmania plugin, String[] worlds) {
        worldList = new ArrayList<World>(worlds.length);
        updateWorlds(worlds);
        this.plugin = plugin;
    }

    public World[] getWorlds() {
        return worldList.toArray(new World[worldList.size()]);
    }

    public Player[] getPlayers() {
        ArrayList<Player> playersInGroup = new ArrayList<Player>();
        for(World world : worldList) {
            playersInGroup.addAll(world.getPlayers());
        }
        return playersInGroup.toArray(new Player[playersInGroup.size()]);
    }

    public void updateWorlds(String[] worlds) {
        for(String worldName : worlds) {
            World world = plugin.getServer().getWorld(worldName);
            if(!worldList.contains(world)) {
                worldList.add(world);
            }
        }
    }
}
