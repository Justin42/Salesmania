package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.Auction;
import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 10:25 AM
 */
public class AuctionBid extends CommandHandler {
    Salesmania plugin;
    public AuctionBid(Salesmania plugin) {
        super(plugin);
        plugin = getPlugin();
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Locale.getMessage("Console.cantBid"));
        }
        Player player = (Player) sender;
        long bidAmount = Long.valueOf(args[0]);
        ItemStack itemStack = plugin.getAuction().getItemStack();
        Auction auction = plugin.getAuction();
        switch(auction.bid(player, bidAmount)) {
            case SUCCESS:
                player.sendMessage(String.format(
                        Locale.getMessage("Bidding.bidSuccess"),
                        bidAmount, itemStack.getType().name()));
                return true;
            case OVER_MAX:
                player.sendMessage(String.format(
                        Locale.getMessage("Bidding.overMax"),
                        auction.getMaxBid()));
                return true;
            case UNDER_MIN:
                player.sendMessage(String.format(
                        Locale.getMessage("Bidding.underMin"),
                        auction.getMinBid()));
                return false;
            case NOT_RUNNING:
                player.sendMessage(Locale.getMessage("Bidding.notRunning"));
                return false;
            case COOLDOWN:
                player.sendMessage(String.format(
                        Locale.getMessage("Bidding.cooldown"),
                        auction.getCooldownTime()));
                return false;
            case WINNING:
                player.sendMessage(Locale.getMessage("Bidding.playerWinning"));
                return false;
        }

        return false;
    }
}
