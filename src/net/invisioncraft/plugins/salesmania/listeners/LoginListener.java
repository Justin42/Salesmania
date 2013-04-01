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

package net.invisioncraft.plugins.salesmania.listeners;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.configuration.LocaleHandler;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroupManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LoginListener implements Listener {
    private Salesmania plugin;
    private LocaleHandler localeHandler;
    private WorldGroupManager worldGroupManager;

    public LoginListener(Salesmania plugin) {
        this.plugin = plugin;
        localeHandler = plugin.getLocaleHandler();
        worldGroupManager = plugin.getWorldGroupManager();
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        localeHandler.updateLocale(player);
        Locale locale = localeHandler.getLocale(player);

        WorldGroup worldGroup = worldGroupManager.getGroup(player);
        if(worldGroup != null && plugin.getItemStash().hasItems(player, worldGroup)) {
            player.sendMessage(locale.getMessage("Stash.itemsWaiting"));
        }
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        Locale locale = localeHandler.getLocale(player);

        WorldGroup worldGroup = worldGroupManager.getGroup(player);
        if(worldGroup != null && plugin.getItemStash().hasItems(player, worldGroup)) {
            player.sendMessage(locale.getMessage("Stash.itemsWaiting"));
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        localeHandler.updateLocale(event.getPlayer());
        plugin.getWorldGroupManager().getCache().savePlayer(event.getPlayer(), event.getPlayer().getWorld());
    }
}
