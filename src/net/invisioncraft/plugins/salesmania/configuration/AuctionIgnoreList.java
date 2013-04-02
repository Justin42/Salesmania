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
import org.bukkit.entity.Player;

import java.util.List;

public class AuctionIgnoreList extends Configuration {
    public AuctionIgnoreList(Salesmania plugin) {
        super(plugin, "auctionIgnore.yml");
    }

    public boolean isIgnored(Player player) {
        List<String> ignoreList = getConfig().getStringList("Ignore");
        return ignoreList.contains(player.getName());
    }

    public boolean setIgnore(Player player, boolean ignored) {
        List<String> ignoreList = getConfig().getStringList("Ignore");
        if(ignored) {
            if(ignoreList.contains(player.getName())) return ignored;
            else {
                ignoreList.add(player.getName());
                getPlugin().getLogger().info(player.getName() + " is now ignoring auction broadcasts.");
            }
        }
        else {
            if(ignoreList.contains(player.getName())) {
                ignoreList.remove(player.getName());
                getPlugin().getLogger().info(player.getName() + " is no longer ignoring auction broadcasts.");
            }
            else return ignored;
        }
        config.set("Ignore", ignoreList);
        save();
        return ignored;
    }

    public boolean toggleIgnore(Player player) {
        List<String> ignoreList = getConfig().getStringList("Ignore");
        if(ignoreList.contains(player.getName())) {
            return setIgnore(player, false);
        }
        else {
            ignoreList.add(player.getName());
            return setIgnore(player, true);
        }
    }
}
