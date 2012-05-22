package net.invisioncraft.plugins.salesmania.events;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
/**
 * Created with IntelliJ IDEA.
 * User: Jacob_2
 * Date: 21/05/12
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */

public class AuctionEndEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private String message;

    public CustomEvent(String example) {

    }

    public String getMessage() {
        return message;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}