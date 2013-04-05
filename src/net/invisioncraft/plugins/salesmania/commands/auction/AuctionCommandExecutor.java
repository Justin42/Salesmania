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

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;

public class AuctionCommandExecutor implements CommandExecutor {
    private Salesmania plugin;

    public enum AuctionCommand {
        START, S,
        BID, B,
        END,
        CANCEL,
        INFO,
        IGNORE,
        ENABLE,
        DISABLE,
        ALL, NONE
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
        AuctionCommand auctionCommand;

        // Syntax
        if(args.length < 1) {
            sender.sendMessage(locale.getMessageList("Syntax.Auction.auction").toArray(new String[0]));
            return false;
        }

        if(label.equalsIgnoreCase("bid")) auctionCommand = AuctionCommand.BID;
        else try {
            auctionCommand = AuctionCommand.valueOf(args[0].toUpperCase());
            // Reserved for region configuration.
            if (auctionCommand == AuctionCommand.ALL | auctionCommand == AuctionCommand.NONE) {
                throw new IllegalArgumentException();
            }
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
                if(label.equalsIgnoreCase("bid")) {
                    // We need to set "bid"' as the first argument
                    ArrayList<String> newArgs = new ArrayList<String>(args.length+1);
                    newArgs.add("bid");
                    Collections.addAll(newArgs, args);
                    args = newArgs.toArray(new String[newArgs.size()]);
                }
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
