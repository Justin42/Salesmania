package net.invisioncraft.plugins.salesmania.util;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Owner: Justin
 * Date: 6/3/12
 * Time: 2:52 AM
 */
public class ItemManager {
    private Salesmania plugin;

    public ItemManager(Salesmania plugin) {
        this.plugin = plugin;
    }

    public static int getQuantity(Player player, int itemID) {
        int quantity = 0;
        for(Map.Entry<Integer, ? extends ItemStack> entry : player.getInventory().all(itemID).entrySet()) {
            quantity += entry.getValue().getAmount();
        }
        return quantity;
    }

    public static int getQuantity(Player player, ItemStack itemStack) {
        return getQuantity(player, itemStack.getTypeId());
    }
}
