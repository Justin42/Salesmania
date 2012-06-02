package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.AuctionSettings;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.event.AuctionEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 6/1/12
 * Time: 11:28 PM
 */
public class AuctionEnable extends CommandHandler {
    AuctionSettings auctionSettings;
    public AuctionEnable(Salesmania plugin) {
        super(plugin);
        auctionSettings = plugin.getSettings().getAuctionSettings();
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = localeHandler.getLocale(sender);

        if(args[0].equalsIgnoreCase("enable") && sender.hasPermission("salesmania.auction.enable")) {
            if(auctionSettings.getEnabled()) {
                sender.sendMessage(locale.getMessage("Auction.alreadyEnabled"));
            }
            else {
                sender.sendMessage(locale.getMessage("Auction.enabled"));
                plugin.getServer().getPluginManager().callEvent(new AuctionEvent(plugin.getAuction(), AuctionEvent.EventType.ENABLE));
            }
        }

        else if(args[0].equalsIgnoreCase("disable") && sender.hasPermission("salesmania.auction.disable")) {
            if(!auctionSettings.getEnabled()) {
                sender.sendMessage(locale.getMessage("Auction.alreadyDisabled"));
            }
            else {
                sender.sendMessage(locale.getMessage("Auction.disabled"));
                plugin.getServer().getPluginManager().callEvent(new AuctionEvent(plugin.getAuction(), AuctionEvent.EventType.DISABLE));
            }
        }
        return true;
    }
}
