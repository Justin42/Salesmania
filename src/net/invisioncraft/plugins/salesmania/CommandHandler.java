package net.invisioncraft.plugins.salesmania;

import net.invisioncraft.plugins.salesmania.configuration.LocaleHandler;
import net.invisioncraft.plugins.salesmania.configuration.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 5/17/12
 * Time: 10:27 AM
 */
/*
Copyright 2012 Byte 2 O Software LLC
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
