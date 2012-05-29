package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Justin
 * Date: 5/29/12
 * Time: 11:15 AM
 */
public class AuctionIgnore extends CommandHandler {
    public AuctionIgnore(Salesmania plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = localeHandler.getLocale(sender);
        if(plugin.getIgnoreAuction().toggleIgnore(sender)) {
            sender.sendMessage(locale.getMessage("Auction.ignoring"));
        }
        else {
            sender.sendMessage(locale.getMessage("Auction.notIgnoring"));
        }
        return true;
    }
}
