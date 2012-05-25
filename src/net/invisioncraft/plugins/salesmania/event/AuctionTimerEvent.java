package net.invisioncraft.plugins.salesmania.event;

import net.invisioncraft.plugins.salesmania.Auction;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Owner: Justin
 * Date: 5/25/12
 * Time: 12:54 AM
 */
public class AuctionTimerEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    Auction auction;
    private long remainingTime;

    public AuctionTimerEvent(Auction auction) {
        remainingTime = auction.getTimeRemaining();
        this.auction = auction;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public Auction getAuction() {
        return auction;
    }

    @Override
    public HandlerList getHandlers() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
