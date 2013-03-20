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

package net.invisioncraft.plugins.salesmania.event;

import net.invisioncraft.plugins.salesmania.Auction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AuctionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    EventType eventType;
    Auction auction;

    public enum EventType {
        BID,
        END,
        CANCEL,
        START,
        TIMER,
        ENABLE,
        DISABLE,
        RELOAD,
        QUEUED
    }

    public AuctionEvent(Auction auction, EventType eventType) {
        this.eventType = eventType;
        this.auction = auction;
    }

    public Auction getAuction() {
        return auction;
    }

    public EventType getEventType() {
        return eventType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
