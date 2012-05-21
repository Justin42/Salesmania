package net.invisioncraft.plugins.salesmania.commands.auction;

import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 10:25 AM
 */
public class AuctionEnd extends CommandHandler {
    Salesmania plugin;
    public AuctionEnd(Salesmania plugin) {
        super(plugin);
        this.plugin = getPlugin();
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
