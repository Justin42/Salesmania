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
public class AuctionStart extends CommandHandler {
    Salesmania plugin;
    public AuctionStart(Salesmania plugin) {
        super(plugin);
        this.plugin = getPlugin();
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Locale.getMessage("Console.cantStartAuction"));
            return false;
        }

        Player player = (Player) sender;
        Auction auction = plugin.getAuction();
        ItemStack itemStack = player.getItemInHand();
        if(!player.hasPermission("salesmania.auction.start")) {
            player.sendMessage(String.format(
                    Locale.getMessage("Permission.noPermission"),
                    Locale.getMessage("Permisson.Auction.start")));
            return false;
        }
        switch(auction.start(player, itemStack, Long.valueOf(args[0]))) {
            case RUNNING:
                player.sendMessage(Locale.getMessage("Auction.alreadyStarted"));
                return false;
            case COOLDOWN:
                player.sendMessage(Locale.getMessage("Auction.cooldown"));
                return false;
        }
        return false;
    }
}
