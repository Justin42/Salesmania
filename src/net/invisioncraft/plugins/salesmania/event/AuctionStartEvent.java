package net.invisioncraft.plugins.salesmania.event;

import net.invisioncraft.plugins.salesmania.Auction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Owner: Justin
 * Date: 5/23/12
 * Time: 4:42 AM
 */
public class AuctionStartEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    Player owner;
    ItemStack itemStack;
    long startingBid;
    Auction auction;
    public AuctionStartEvent(Auction auction) {
        owner = auction.getOwner();
        itemStack = auction.getItemStack();
        startingBid = auction.getCurrentBid();
        this.auction = auction;
    }

    public Player getOwner() {
        return owner;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public long getStartingBid() {
        return startingBid;
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
