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

package net.invisioncraft.plugins.salesmania.commands.stash;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.commands.auction.AuctionCommandExecutor;
import net.invisioncraft.plugins.salesmania.configuration.ItemStash;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.configuration.RegionSettings;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroupManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static net.invisioncraft.plugins.salesmania.commands.auction.AuctionCommandExecutor.*;

public class StashCommandExecutor implements CommandExecutor {
    private ItemStash itemStash;
    private Salesmania plugin;
    private WorldGroupManager worldGroupManager;
    private RegionSettings regionSettings;

    public StashCommandExecutor(Salesmania plugin) {
        this.plugin = plugin;
        itemStash = plugin.getItemStash();
        worldGroupManager = plugin.getWorldGroupManager();
        regionSettings = plugin.getSettings().getRegionSettings();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        Locale locale = plugin.getLocaleHandler().getLocale(player);

        WorldGroup worldGroup = worldGroupManager.getGroup(player);

        if(!regionSettings.isAllowed(player, AuctionCommand.COLLECT)) {
            player.sendMessage(locale.getMessage("Auction.regionDisabled"));
            return false;
        }

        if(itemStash.hasItems(player, worldGroup)) {
            ArrayList<ItemStack> remainingItems = new ArrayList<ItemStack>();
            for(ItemStack itemStack : itemStash.collect(player, worldGroup)) {
                ItemStack remaining = plugin.getItemManager().giveItem(player, itemStack, worldGroup, false);
                if(remaining.getAmount() != 0) {
                    plugin.getLogger().info(remaining.toString());
                    remainingItems.add(remaining);
                }
            }
            if(!remainingItems.isEmpty()) {
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
