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
public class SalesmaniaCommandExecutor implements CommandExecutor {
    protected Salesmania plugin;
    protected Locale localeCommand;

    enum SalesmaniaCommand {
        LOCALE,
    }

    public SalesmaniaCommandExecutor(Salesmania plugin) {
        this.plugin = plugin;
        localeCommand = new Locale(plugin);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        SalesmaniaCommand salesmaniaCommand = SalesmaniaCommand.valueOf(args[0].toUpperCase());
        switch(salesmaniaCommand) {
            case LOCALE:
                localeCommand.execute(commandSender, command, label, args);
                return true;
            default:
                return false;
        }
    }
}
