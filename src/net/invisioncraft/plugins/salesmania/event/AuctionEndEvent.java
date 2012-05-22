package net.invisioncraft.plugins.salesmania.event;

import net.invisioncraft.plugins.salesmania.Auction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Owner: Jacob
 * Date: 5/21/12
 * Time: 6:16 PM
 */

public class AuctionEndEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Auction auction;
    private Auction.AuctionStatus status;
    private Player winner;
    private long winningBid;
    private ItemStack itemStack;

    public AuctionEndEvent(Auction auction, Auction.AuctionStatus status) {
        this.status = status;
        this.auction = auction;
        winner = auction.getWinner();
        winningBid = auction.getCurrentBid();
        itemStack = auction.getItemStack();
    }

    public Auction getAuction() {
        return auction;
    }

    public Auction.AuctionStatus getStatus() {
        return status;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Player getWinner() {
        return winner;
    }

    public long getWinningBid() {
        return winningBid;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}