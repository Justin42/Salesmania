package net.invisioncraft.plugins.salesmania.commands.salesmania;

import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Justin
 * Date: 5/23/12
 * Time: 8:01 PM
 */
public class Locale extends CommandHandler {
    enum LocaleCommand {
        LIST,
        SET
    }

    public Locale(Salesmania plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        LocaleCommand localeCommand = LocaleCommand.valueOf(args[1].toUpperCase());
        switch(localecommand) {
            case LIST:
                return false;
            case SET:
                return false;
            default:
                return false;
        }
    }
}
