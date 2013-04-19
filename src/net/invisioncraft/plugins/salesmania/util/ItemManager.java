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

package net.invisioncraft.plugins.salesmania.util;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.configuration.RegionSettings;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroupManager;
import net.milkbowl.vault.item.Items;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ItemManager {
    private static Logger consoleLogger = Salesmania.consoleLogger;
    private Salesmania plugin;
    private RegionSettings regionSettings;
    private WorldGroupManager worldGroupManager;

    public ItemManager(Salesmania plugin) {
        this.plugin = plugin;
        regionSettings = plugin.getSettings().getRegionSettings();
        worldGroupManager = plugin.getWorldGroupManager();
    }

    public static int getQuantity(Player player, ItemStack itemStack) {
        int quantity = 0;
        for(Map.Entry<Integer, ? extends ItemStack> entry : player.getInventory().all(itemStack.getTypeId()).entrySet()) {
            // Check for data value + enchants
            if(!itemStack.isSimilar(entry.getValue())) continue;
            quantity += entry.getValue().getAmount();
        }
        return quantity;
    }

    public static boolean takeItem(Player player, ItemStack itemStack) {
        int remainingQuantity = itemStack.getAmount();
        for(Map.Entry<Integer, ? extends ItemStack> entry : player.getInventory().all(itemStack.getTypeId()).entrySet()) {
            if(!itemStack.isSimilar(entry.getValue())) continue;

            if(remainingQuantity == 0) break;
            ItemStack stack = entry.getValue();

            if(remainingQuantity >= stack.getAmount()) {
                remainingQuantity -= stack.getAmount();
                player.getInventory().removeItem(stack);
            }

            else if (remainingQuantity < stack.getAmount()) {
                stack.setAmount(stack.getAmount()-remainingQuantity);
                remainingQuantity -= remainingQuantity;
            }
        }
        if(remainingQuantity != 0) {
            consoleLogger.severe("Could not take expected quantity! Item duplication may have occurred.");
            return false;
        }
        else return true;
    }

    public ItemStack giveItem(OfflinePlayer player, ItemStack itemStack, WorldGroup worldGroup, boolean stashNotify) {
        ItemStack remainingItems = itemStack.clone();
        if(player.isOnline()) {
            Player onlinePlayer = player.getPlayer();
            Locale locale = plugin.getLocaleHandler().getLocale(player.getPlayer());
            // Region
            if(regionSettings.shouldStash(player.getPlayer())) {
                plugin.getItemStash().store(player, itemStack.clone(), worldGroup);
                if(stashNotify)player.getPlayer().sendMessage(locale.getMessage("Auction.regionStashed"));
                return remainingItems;
            }

            // World group
            if(worldGroupManager.getGroup(onlinePlayer) != worldGroup) {
                plugin.getItemStash().store(player, itemStack.clone(), worldGroup);
                player.getPlayer().sendMessage(String.format(locale.getMessage("Stash.itemsWaitingInGroup"), worldGroup.getGroupName()));
            }
            else {
                ItemStack[] inventory = onlinePlayer.getInventory().getContents();

                // Try to place into existing stack
                for(ItemStack currentStack : onlinePlayer.getInventory().all(remainingItems.getType()).values()) {
                    int placeableQuantity = 0;
                    if(currentStack.isSimilar(remainingItems) && currentStack.getData().equals(remainingItems.getData())) {
                        placeableQuantity = remainingItems.getMaxStackSize() - currentStack.getAmount();
                    }
                    if(placeableQuantity > 0) {
                        currentStack.setAmount(currentStack.getAmount() + placeableQuantity);
                        remainingItems.setAmount(remainingItems.getAmount() - placeableQuantity);
                    }
                }

                // Place remaining amount into any available slots.
                for(int inventoryPosition = 0; inventoryPosition < inventory.length; inventoryPosition++) {
                    ItemStack currentStack = inventory[inventoryPosition];
                    int placeableQuantity = 0;

                    if(currentStack == null) {
                        placeableQuantity = remainingItems.getMaxStackSize();
                    }
                    else if(currentStack.isSimilar(remainingItems) && currentStack.getData().equals(remainingItems.getData())) {
                        placeableQuantity = remainingItems.getMaxStackSize() - currentStack.getAmount();
                    }

                    if(placeableQuantity > remainingItems.getAmount()) {
                        placeableQuantity = remainingItems.getAmount();
                    }
                    if(placeableQuantity > 0) {
                        ItemStack place = remainingItems.clone();
                        place.setAmount(placeableQuantity);
                        onlinePlayer.getInventory().setItem(inventoryPosition, place);
                        remainingItems.setAmount(remainingItems.getAmount() - place.getAmount());
                    }

                    if(remainingItems.getAmount() == 0) break;
                }

                // Place remaining into item stash
                if(remainingItems.getAmount() > 0) {
                    plugin.getItemStash().store(player, remainingItems, worldGroup);
                    if(stashNotify)player.getPlayer().sendMessage(locale.getMessage("Stash.itemsWaiting"));
                    return remainingItems;
                }
            }
        }
        else plugin.getItemStash().store(player, itemStack, worldGroup);
        return remainingItems;
    }

    public ItemStack giveItem(OfflinePlayer player, ItemStack itemStack, WorldGroup worldGroup) {
        return giveItem(player, itemStack, worldGroup, true);
    }

    public static String getName(ItemStack itemStack) {
        // Spawner names
        String itemName;
        if(itemStack.getTypeId() == Material.MOB_SPAWNER.getId()) {
            itemName = EntityType.fromId((int) itemStack.getData().getData()).getName()
                    + " Spawner";
        }
        else try {
            itemName = Items.itemByStack(itemStack).getName();
        } catch (NullPointerException ex) {
            itemName = itemStack.getType().name();
        }
        return itemName;
    }

    public static Map<Enchantment, Integer> getEnchants(ItemStack itemStack) {
        if(!itemStack.getEnchantments().isEmpty()) return itemStack.getEnchantments();
        else if(itemStack.hasItemMeta() && itemStack.getItemMeta() instanceof EnchantmentStorageMeta) {
            return ((EnchantmentStorageMeta) itemStack.getItemMeta()).getStoredEnchants();
        }
        else return new HashMap<>();
    }
}
