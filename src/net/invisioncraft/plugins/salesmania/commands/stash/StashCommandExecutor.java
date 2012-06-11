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

package net.invisioncraft.plugins.salesmania.commands.stash;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.ItemStash;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class StashCommandExecutor implements CommandExecutor {
    ItemStash itemStash;
    Salesmania plugin;
    public StashCommandExecutor(Salesmania plugin) {
        this.plugin = plugin;
        itemStash = plugin.getItemStash();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        Locale locale = plugin.getLocaleHandler().getLocale(player);

        if(itemStash.hasItems(player)) {
            ArrayList<ItemStack> remainingItems = new ArrayList<ItemStack>();
            for(ItemStack itemStack : itemStash.collect(player)) {
                remainingItems.addAll(player.getInventory().addItem(itemStack).values());
            }
            if(!remainingItems.isEmpty()) {
                itemStash.store(player, remainingItems);
                player.sendMessage(locale.getMessage("Stash.inventoryFull"));
            }
            else player.sendMessage(locale.getMessage("Stash.gotItems"));
        }
        else {
            player.sendMessage(locale.getMessage("Stash.noItems"));
        }

        return true;
    }
}
