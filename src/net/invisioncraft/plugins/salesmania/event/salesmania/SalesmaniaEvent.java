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

package net.invisioncraft.plugins.salesmania.event.salesmania;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SalesmaniaEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    protected Salesmania plugin;

    public SalesmaniaEvent(Salesmania plugin) {
        this.plugin = plugin;
    }

    public Salesmania getPlugin() {
        return plugin;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
