package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 5/17/12
 * Time: 9:49 AM
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

public class AuctionCommandExecutor implements CommandExecutor {
    protected Salesmania plugin;

    enum AuctionCommand {
        START, S,
        BID, B,
        END,
        CANCEL,
        INFO,
        IGNORE,
        ENABLE,
        DISABLE
    }

    AuctionStart auctionStart;
    AuctionBid auctionBid;
    AuctionEnd auctionEnd;
    AuctionCancel auctionCancel;
    AuctionInfo auctionInfo;
    AuctionIgnore auctionIgnore;
    AuctionEnable auctionEnable;
    public AuctionCommandExecutor(Salesmania plugin) {
        this.plugin = plugin;

        // Initialize command handlers
        auctionStart = new AuctionStart(plugin);
        auctionEnd = new AuctionEnd(plugin);
        auctionCancel = new AuctionCancel(plugin);
        auctionBid = new AuctionBid(plugin);
        auctionInfo = new AuctionInfo(plugin);
        auctionIgnore = new AuctionIgnore(plugin);
        auctionEnable = new AuctionEnable(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = plugin.getLocaleHandler().getLocale(sender);
        AuctionCommand auctionCommand = null;

        // Syntax
        if(args.length < 1) {
            sender.sendMessage(locale.getMessageList("Syntax.Auction.auction").toArray(new String[0]));
            return false;
        }
        try {
            auctionCommand = AuctionCommand.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException ex) {
            sender.sendMessage(locale.getMessageList("Syntax.Auction.auction").toArray(new String[0]));
            return false;
        }
        switch(auctionCommand) {

            case START:
            case S:
                auctionStart.execute(sender, command, label, args);
                break;

            case BID:
            case B:
                auctionBid.execute(sender, command, label, args);
                break;

            case END:
                auctionEnd.execute(sender, command, label, args);
                break;

            case CANCEL:
                auctionCancel.execute(sender, command, label, args);
                break;

            case INFO:
                auctionInfo.execute(sender, command, label, args);
                break;

            case IGNORE:
                auctionIgnore.execute(sender, command, label, args);
                break;

            case ENABLE:
                auctionEnable.execute(sender, command, label, args);
                break;

            case DISABLE:
                auctionEnable.execute(sender, command, label, args);
                break;

            default:
                return false;
        }
        return true;
    }
}
