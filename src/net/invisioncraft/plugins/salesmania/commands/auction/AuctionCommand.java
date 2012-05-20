package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 10:27 AM
 */
public abstract class AuctionCommand {
    Salesmania plugin;

    public AuctionCommand(Salesmania plugin) {
        this.plugin = plugin;
    }

    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
}
