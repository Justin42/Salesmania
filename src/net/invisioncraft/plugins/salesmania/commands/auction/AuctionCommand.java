package net.invisioncraft.plugins.salesmania.commands.auction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 10:27 AM
 */
public abstract class AuctionCommand {
    JavaPlugin plugin;

    public AuctionCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
}
