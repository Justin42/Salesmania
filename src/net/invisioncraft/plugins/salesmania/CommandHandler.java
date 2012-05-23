package net.invisioncraft.plugins.salesmania;

import net.invisioncraft.plugins.salesmania.configuration.LocaleHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 10:27 AM
 */
public abstract class CommandHandler {
    protected Salesmania plugin;
    protected LocaleHandler localeHandler;

    public CommandHandler(Salesmania plugin) {
        this.plugin = plugin;
        localeHandler = plugin.getLocaleHandler();
    }

    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
}
