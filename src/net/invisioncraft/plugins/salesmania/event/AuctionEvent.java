package net.invisioncraft.plugins.salesmania.event;

import net.invisioncraft.plugins.salesmania.Auction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Owner: Justin
 * Date: 5/25/12
 * Time: 4:46 AM
 */
public class AuctionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    EventType eventType;
    Auction auction;

    public enum EventType {
        BID,
        END,
        CANCEL,
        START,
        TIMER
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
