package net.invisioncraft.plugins.salesmania.commands.salesmania;

import net.invisioncraft.plugins.salesmania.CommandHandler;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Justin
 * Date: 5/23/12
 * Time: 8:01 PM
 */
public class LocaleCommand extends CommandHandler {
    enum LocaleCommands {
        LIST,
        SET
    }

    public LocaleCommand(Salesmania plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        LocaleCommands localeCommand = LocaleCommands.valueOf(args[1].toUpperCase());
        Locale locale = plugin.getLocaleHandler().getLocale(sender);
        switch(localeCommand) {
            case LIST:
                String localeList = "";
                for (String localeName : plugin.getSettings().getLocales()) {
                    localeList.concat(locale + " ");
                }
                sender.sendMessage(String.format(
                    locale.getMessage("Locale.list"),
                    localeList));
            case SET:
                if(plugin.getLocaleHandler().setLocale(sender, args[2])) {
                    locale = plugin.getLocaleHandler().getLocale(sender);
                    sender.sendMessage(String.format(
                            locale.getMessage("Locale.changed"),
                            locale.getName()));
                }
                else {
                    sender.sendMessage(String.format(
                            locale.getMessage("Locale.notFound"),
                            args[2]));
                }
            default:
                return false;
        }
    }
}
