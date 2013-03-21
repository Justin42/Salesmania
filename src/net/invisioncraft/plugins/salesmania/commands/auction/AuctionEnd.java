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

package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.Auction;
import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuctionEnd extends CommandHandler {
    public AuctionEnd(Salesmania plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = plugin.getLocaleHandler().getLocale(sender);
        Auction currentAuction = plugin.getAuctionQueue().getCurrentAuction();
        if(currentAuction != null) {
            if(!(sender instanceof Player)) {
                currentAuction.end();
                return true;
            }

            Player player = (Player) sender;
            if(player.hasPermission("salesmania.auction.end") | player == currentAuction.getOwner()) {
                if(currentAuction.isRunning()) currentAuction.end();
                else sender.sendMessage(locale.getMessage("Auction.notRunning"));
                return true;
            }
            else {
                sender.sendMessage(String.format(
                        locale.getMessage("Permission.noPermission"),
                        locale.getMessage("Permission.Auction.end")));
                return false;
            }
        }
        return false;
    }
}
