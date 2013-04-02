/*
This file is part of Salesmania.

    Salesmania is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Salesmania is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Salesmania.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.invisioncraft.plugins.salesmania.commands.salesmania;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

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
        Locale locale = plugin.getLocaleHandler().getLocale(sender);
        SalesmaniaCommand salesmaniaCommand;

        // Syntax
        if(args.length < 1) {
            ArrayList<String> messageList = locale.getMessageList("Syntax.Salesmania.salesmania");
            sender.sendMessage(messageList.toArray(new String[messageList.size()]));
            return false;
        }
        try {
            salesmaniaCommand = SalesmaniaCommand.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException ex) {
            ArrayList<String> messageList = locale.getMessageList("Syntax.Salesmania.salesmania");
            sender.sendMessage(messageList.toArray(new String[messageList.size()]));
            return false;
        }

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
