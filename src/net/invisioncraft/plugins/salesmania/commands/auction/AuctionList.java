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
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.commands.CommandHandler;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuctionList extends CommandHandler {

    public AuctionList(Salesmania plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = localeHandler.getLocale(sender);

        if(!(sender instanceof Player)) {
            sender.sendMessage(locale.getMessage("Auction.Console.cantConsole"));
            return false;
        }

        Player player = (Player) sender;
        WorldGroup worldGroup = plugin.getWorldGroupManager().getGroup(player);
        if(worldGroup == null) {
            sender.sendMessage(locale.getMessage("Auction.worldDisabled"));
            return false;
        }

        // Todo allow multiple pages for long queues
        if(worldGroup.getAuctionQueue().size() == 0) {
            player.sendMessage(locale.getMessage("Auction.queueListNone"));
        }
        else {
            player.sendMessage(locale.getMessage("Auction.queueListHeader"));
            for(Auction auction : worldGroup.getAuctionQueue()) {
                String message;
                if(auction.getOwner().isOnline() && auction.getOwner().getPlayer() == player) {
                    message = locale.getMessage("Auction.queueListOwn");
                }
                else message = locale.getMessage("Auction.queueListOther");

                message = auction.infoReplace(message);
                message = auction.enchantReplace(message, "", locale.getMessage("Auction.enchantInfo"), locale);
                player.sendMessage(message);
            }
        }
        return true;
    }
}
