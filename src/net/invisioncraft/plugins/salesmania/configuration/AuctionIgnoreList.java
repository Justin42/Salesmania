package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 5/29/12
 * Time: 2:19 PM
 */
/*
Copyright 2012 Byte 2 O Software LLC
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
public class AuctionIgnoreList extends Configuration implements IgnoreList {
    public AuctionIgnoreList(Salesmania plugin) {
        super(plugin, "auctionIgnore.yml");
    }

    public boolean isIgnored(CommandSender sender) {
        List<String> ignoreList = getConfig().getStringList("Ignore");
        if(ignoreList.contains(sender.getName())) return true;
        else return false;
    }

    public void setIgnore(CommandSender sender, boolean ignored) {
        List<String> ignoreList = getConfig().getStringList("Ignore");
        ignoreList.remove(sender.getName());
        config.set("Ignore", ignoreList);
        save();
    }

    public boolean toggleIgnore(CommandSender sender) {
        boolean hasIgnored;
        List<String> ignoreList = getConfig().getStringList("Ignore");
        if(ignoreList.contains(sender.getName())) {
            ignoreList.remove(sender.getName());
            hasIgnored = false;
        }
        else {
            ignoreList.add(sender.getName());
            hasIgnored = true;
        }
        config.set("Ignore", ignoreList);
        save();
        return hasIgnored;
    }
}
