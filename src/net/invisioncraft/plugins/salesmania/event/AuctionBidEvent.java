package net.invisioncraft.plugins.salesmania.event;

import net.invisioncraft.plugins.salesmania.Auction;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * Owner: Justin
 * Date: 5/23/12
 * Time: 2:29 AM
 */
public class AuctionBidEvent {
    private static final HandlerList handlers = new HandlerList();
    Player lastWinner;
    long lastBid;
    Player winner;
    long bid;
    Auction auction;
    public AuctionBidEvent(Auction auction, Player winner, long bid) {
        lastWinner = auction.getWinner();
        lastBid = auction.getCurrentBid();
        this.winner = winner;
        this.bid = bid;
        this.auction = auction;
    }

    public Player getLastWinner() {
        return lastWinner;
    }

    public long getLastBid() {
        return lastBid;
    }

    public Player getWinner() {
        return winner;
    }

    public long getBid() {
        return bid;
    }

    public Auction getAuction() {
        return auction;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
