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

import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.AuctionSettings;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.event.AuctionEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class AuctionEnable extends CommandHandler {
    AuctionSettings auctionSettings;
    public AuctionEnable(Salesmania plugin) {
        super(plugin);
        auctionSettings = plugin.getSettings().getAuctionSettings();
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = localeHandler.getLocale(sender);

        if(args[0].equalsIgnoreCase("enable")) {
            if(!sender.hasPermission("salesmania.auction.enable")) {
                sender.sendMessage(String.format(
                        locale.getMessage("Permission.noPermission"),
                        locale.getMessage("Permission.Auction.enable")));
                return false;
            }
            if(auctionSettings.getEnabled()) {
                sender.sendMessage(locale.getMessage("Auction.alreadyEnabled"));
            }
            else {
                auctionSettings.setEnabled(true);
                plugin.getServer().getPluginManager().callEvent(new AuctionEvent(null, AuctionEvent.EventType.ENABLE));
                sender.sendMessage(locale.getMessage("Auction.enabled"));
            }
        }

        else if(args[0].equalsIgnoreCase("disable")) {
            if(!sender.hasPermission("salesmania.auction.disable")) {
                sender.sendMessage(String.format(
                        locale.getMessage("Permission.noPermission"),
                        locale.getMessage("Permission.Auction.disable")));
                return false;
            }
            if(!auctionSettings.getEnabled()) {
                sender.sendMessage(locale.getMessage("Auction.alreadyDisabled"));
            }
            else {
                auctionSettings.setEnabled(false);
                plugin.getServer().getPluginManager().callEvent(new AuctionEvent(null, AuctionEvent.EventType.DISABLE));
                sender.sendMessage(locale.getMessage("Auction.disabled"));
            }
        }
        return true;
    }
}
