package net.invisioncraft.plugins.salesmania;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 10:27 AM
 */
public abstract class CommandHandler {
    private Salesmania plugin;

    public CommandHandler(Salesmania plugin) {
        this.plugin = plugin;
    }

    public Salesmania getPlugin() {
        return plugin;
    }

    public abstract boolean execute(CommandSender sender, Command command, String label, String[] args);
}
