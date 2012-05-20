package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 10:25 AM
 */
public class AuctionBid extends AuctionCommand {

    public AuctionBid(Salesmania plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Locale.getMessage("Bidding.consoleCantBid"));
        }
        Player player = (Player) sender;
        long bidAmount = Long.valueOf(args[0]);

        switch(plugin.getAuction().bid(player, bidAmount)) {
            case SUCCESS:
                player.sendMessage(Locale.getMessage("Bidding.budSuccess"));
                return true;
        }

        return false;
    }
}
