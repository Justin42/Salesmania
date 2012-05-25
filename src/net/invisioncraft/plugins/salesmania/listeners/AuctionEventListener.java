package net.invisioncraft.plugins.salesmania.listeners;

import net.invisioncraft.plugins.salesmania.event.AuctionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Owner: Justin
 * Date: 5/25/12
 * Time: 5:08 AM
 */
public class AuctionEventListener implements Listener {
    @EventHandler
    public void onAuctionEvent(AuctionEvent auctionEvent) {
        switch (auctionEvent.getEventType()) {
            case BID:
                break;
            case END:
                break;
            case START:
                break;
            case TIMER:
                break;
        }
    }
}
