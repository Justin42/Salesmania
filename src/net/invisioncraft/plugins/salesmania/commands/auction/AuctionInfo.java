package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Justin
 * Date: 5/24/12
 * Time: 7:21 AM
 */
public class AuctionInfo extends CommandHandler {
    public AuctionInfo(Salesmania plugin) {
        super(plugin);
    }
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
