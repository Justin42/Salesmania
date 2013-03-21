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

public class AuctionCancel extends CommandHandler {
    public AuctionCancel(Salesmania plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = plugin.getLocaleHandler().getLocale(sender);
        boolean hasPermission = false;
        Auction currentAuction = plugin.getAuctionQueue().getCurrentAuction();
        if(currentAuction != null) {
            if((sender instanceof Player)) {
                if(sender == currentAuction.getOwner() | sender.hasPermission("salesmania.auction.cancel")) {
                    hasPermission = true;
                }
            }
            if(!hasPermission) {
                sender.sendMessage(
                        locale.getMessage("Permission.noPermission") +
                        locale.getMessage("Permission.Auction.cancel"));
                return false;
            }

            switch(currentAuction.cancel()) {
                // TODO no reason the auction shouldn't be running. it may be better to check the AuctionQueue for this.
                case NOT_RUNNING:
                    sender.sendMessage(locale.getMessage("Auction.notRunning"));
                    return true;
            }
            return false;
        }
        return false;
    }
}
