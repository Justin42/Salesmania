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
import net.invisioncraft.plugins.salesmania.configuration.RegionSettings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;

public class AuctionCommandExecutor implements CommandExecutor {
    private Salesmania plugin;

    public static enum AuctionCommand {
        START, S,
        BID, B,
        END,
        CANCEL,
        INFO,
        IGNORE,
        ENABLE,
        DISABLE,
        COLLECT,
        LIST, L, QUEUE, Q,
        ALL, NONE
    }

    private AuctionStart auctionStart;
    private AuctionBid auctionBid;
    private AuctionEnd auctionEnd;
    private AuctionCancel auctionCancel;
    private AuctionInfo auctionInfo;
    private AuctionIgnore auctionIgnore;
    private AuctionEnable auctionEnable;
    private AuctionList auctionList;

    RegionSettings regionSettings;

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
        auctionList = new AuctionList(plugin);

        regionSettings = plugin.getSettings().getRegionSettings();
    }

    @Override
    // TODO This is messy...
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = plugin.getLocaleHandler().getLocale(sender);
        AuctionCommand auctionCommand;

        // Syntax
        if(args.length < 1) {
            ArrayList<String> messageList = locale.getMessageList("Syntax.Auction.auction");
            sender.sendMessage(messageList.toArray(new String[messageList.size()]));
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
            ArrayList<String> messageList = locale.getMessageList("Syntax.Auction.auction");
            sender.sendMessage(messageList.toArray(new String[messageList.size()]));
            return false;
        }

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(!regionSettings.isAllowed(player, auctionCommand)) {
                player.sendMessage(locale.getMessage("Auction.regionDisabled"));
                return false;
            }
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
                    ArrayList<String> newArgs = new ArrayList<>(args.length+1);
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

            case LIST:
            case L:
            case QUEUE:
            case Q:
               auctionList.execute(sender, command, label, args);
                break;

            default:
                return false;
        }
        return true;
    }
}
