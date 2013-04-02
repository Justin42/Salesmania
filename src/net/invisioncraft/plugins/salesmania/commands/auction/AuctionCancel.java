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

package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.Auction;
import net.invisioncraft.plugins.salesmania.commands.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuctionCancel extends CommandHandler {
    public AuctionCancel(Salesmania plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = plugin.getLocaleHandler().getLocale(sender);
        if(!(sender instanceof Player)) {
            sender.sendMessage(locale.getMessage("Auction.Console.cantCancel"));
            return false;
        }

        Player player = (Player) sender;
        WorldGroup worldGroup = plugin.getWorldGroupManager().getGroup(player);
        if(worldGroup == null) {
            sender.sendMessage(locale.getMessage("Auction.worldDisabled"));
            return false;
        }

        Auction currentAuction = worldGroup.getAuctionQueue().getCurrentAuction();
        if(currentAuction != null) {
            if(player == currentAuction.getOwner() | player.hasPermission("salesmania.auction.cancel")) {
                if(!currentAuction.isRunning()) {
                    player.sendMessage(locale.getMessage("Auction.notRunning"));
                    return false;
                }
                currentAuction.cancel();
                return true;
            }
            else {
                sender.sendMessage(
                        locale.getMessage("Permission.noPermission") +
                        locale.getMessage("Permission.Auction.cancel"));
            }
        }
        else {
            player.sendMessage(locale.getMessage("Auction.notRunning"));
        }
        return false;
    }
}
