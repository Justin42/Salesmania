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

import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class AuctionInfo extends CommandHandler {
    public AuctionInfo(Salesmania plugin) {
        super(plugin);
    }
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = localeHandler.getLocale(sender);
        if(!plugin.getAuction().isRunning()) {
            sender.sendMessage(locale.getMessage("Auction.notRunning"));
            return false;
        }
        ArrayList<String> infoList = locale.getMessageList("Auction.info");
        infoList = plugin.getAuction().infoReplace(infoList);
        infoList = plugin.getAuction().enchantReplace(infoList,
                locale.getMessage("Auction.enchant"),
                locale.getMessage("Auction.enchantInfo"), locale);
        sender.sendMessage(infoList.toArray(new String[0]));
        return true;
    }
}
