package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 10:27 AM
 */
public class AuctionCancel extends CommandHandler {
    public AuctionCancel(Salesmania plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Locale locale = plugin.getLocaleHandler().getLocale(sender);

        if(!(sender instanceof Player)) {
            plugin.getAuction().cancel();
            return true;
        }
        Player player = (Player) sender;

        if(player.hasPermission("salesmania.auction.cancel") |
                player == plugin.getAuction().getOwner()) {
            plugin.getAuction().cancel();
            return true;
        }
        else {
            sender.sendMessage(String.format(
                    locale.getMessage("Permission.noPermission"),
                    locale.getMessage("Permission.Auction.cancel")));
            return false;
        }
    }
}
