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
import net.invisioncraft.plugins.salesmania.configuration.WorldGroupSettings;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WorldGroupManager {
    private WorldGroupSettings worldGroupSettings;
    private Salesmania plugin;
    private ArrayList<WorldGroup> worldGroups;

    public WorldGroupManager(Salesmania plugin) {
        worldGroupSettings = plugin.getSettings().getWorldGroupSettings();
        this.plugin = plugin;
    }

    public void update() {
        worldGroups = worldGroupSettings.parseGroups();
    }

    public ArrayList<WorldGroup> getWorldGroups() {
        return worldGroups;
    }

    public WorldGroup getGroup(Player player) {
        for(WorldGroup worldGroup : worldGroups) {
            if(worldGroup.hasPlayer(player)) return worldGroup;
        }
        return null;
    }
}
