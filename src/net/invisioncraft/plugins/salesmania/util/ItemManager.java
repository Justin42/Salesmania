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
import net.milkbowl.vault.item.Items;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.logging.Logger;

public class ItemManager {
    private static Logger consoleLogger = Salesmania.consoleLogger;

    public static int getQuantity(Player player, ItemStack itemStack) {
        int quantity = 0;
        for(Map.Entry<Integer, ? extends ItemStack> entry : player.getInventory().all(itemStack.getTypeId()).entrySet()) {
            // Check for data value + enchants
            if(!compareItem(entry.getValue(), itemStack)) continue;
            quantity += entry.getValue().getAmount();
        }
        return quantity;
    }

    public static boolean takeItem(Player player, ItemStack itemStack) {
        int remainingQuantity = itemStack.getAmount();
        for(Map.Entry<Integer, ? extends ItemStack> entry : player.getInventory().all(itemStack.getTypeId()).entrySet()) {
            if(!compareItem(entry.getValue(), itemStack)) continue;

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
            consoleLogger.severe("Could not take expected quantity!");
            return false;
        }
        else return true;
    }

    public static boolean compareItem(ItemStack stack1, ItemStack stack2) {
        if(!stack1.getType().equals(stack2.getType())) return false;
        if(!stack1.getEnchantments().equals(stack2.getEnchantments())) return false;
        if(!stack1.getData().equals(stack2.getData())) return false;
        if(stack1.getDurability() != stack2.getDurability()) return false;
        return true;
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
}
