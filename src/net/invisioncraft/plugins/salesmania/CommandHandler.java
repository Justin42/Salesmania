package net.invisioncraft.plugins.salesmania;

import net.invisioncraft.plugins.salesmania.configuration.LocaleHandler;
import net.invisioncraft.plugins.salesmania.configuration.Settings;
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
    protected Settings settings;

    public CommandHandler(Salesmania plugin) {
        this.plugin = plugin;
        localeHandler = plugin.getLocaleHandler();
        settings = plugin.getSettings();
    }

    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
}
