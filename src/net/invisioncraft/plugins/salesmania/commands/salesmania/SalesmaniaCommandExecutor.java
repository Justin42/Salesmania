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
    protected LocaleCommand localeCommand;

    enum SalesmaniaCommand {
        LOCALE,
        RELOAD
    }

    public SalesmaniaCommandExecutor(Salesmania plugin) {
        this.plugin = plugin;
        localeCommand = new LocaleCommand(plugin);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        SalesmaniaCommand salesmaniaCommand = SalesmaniaCommand.valueOf(args[0].toUpperCase());
        switch(salesmaniaCommand) {
            case LOCALE:
                localeCommand.execute(sender, command, label, args);
                return true;
            case RELOAD:
                if(!sender.hasPermission("salesmania.admin.reload")) return false;
                plugin.reloadConfig(sender);
                return true;
            default:
                return false;
        }
    }
}
