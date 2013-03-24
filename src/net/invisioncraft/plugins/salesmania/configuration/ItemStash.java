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

package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.logging.Logger;

public class ItemStash extends Configuration {
    private Logger logger = Logger.getLogger(ItemStash.class.getName());
    public ItemStash(Salesmania plugin) {
        super(plugin, "itemStash.yml");
    }

    @SuppressWarnings("unchecked")
    public void store(Player player, ItemStack itemStack) {
        logger.info("Storing item stack for player '" + player.getName() + "' " + itemStack.toString());
        ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
        if(hasItems(player)) {
            try { stackList = (ArrayList<ItemStack>) config.get(player.getName()); }
            catch (ClassCastException ex) {
                corruptionWarning(player);
                return;
            }
        }
        stackList.add(itemStack.clone());
        config.set(player.getName(), stackList);
        save();
    }

    private void corruptionWarning(Player player) {
        Logger.getLogger(ItemStash.class.getName())
                .severe("Stash seems corrupted. Couldn't retrieve stash for player: " + player.getName());
    }

    @SuppressWarnings("unchecked")
    public void store(Player player, ArrayList<ItemStack> itemStacks) {
        for(ItemStack itemStack : itemStacks) {
            logger.info("Storing item stack for player '" + player.getName() + "' " + itemStack.toString());
        }
        if(hasItems(player)) {
            try {
                ArrayList<ItemStack> stackList = (ArrayList<ItemStack>) config.get(player.getName());
                stackList.addAll(itemStacks);
                config.set(player.getName(), stackList);
            }
            catch (ClassCastException ex) {
                corruptionWarning(player);
                return;
            }
        }
        else {
            config.set(player.getName(), itemStacks);
        }
        save();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<ItemStack> collect(Player player) {
        ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
        if(hasItems(player)) {
            try {
                stackList = (ArrayList<ItemStack>) config.get(player.getName());
                config.set(player.getName(), null);
                save();
            }
            catch (ClassCastException ex) {
                corruptionWarning(player);
                return null;
            }
        }
        return stackList;
    }

    public boolean hasItems(Player player) {
        return config.contains(player.getName());
    }
}
