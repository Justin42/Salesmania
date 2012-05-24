package net.invisioncraft.plugins.salesmania.commands.salesmania;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Owner: Justin
 * Date: 5/23/12
 * Time: 8:08 PM
 */
public class SalesmaniaCommandExecutor extends CommandExecutor {
    protected Salesmania plugin;


   public SalesmaniaCommandExecutor(Salesmania plugin) {
        this.plugin = plugin;
        localeCommand = new Locale(plugin);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
