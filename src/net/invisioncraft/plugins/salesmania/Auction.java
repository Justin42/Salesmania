package net.invisioncraft.plugins.salesmania;

import org.bukkit.entity.Player;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 3:59 PM
 */
public class Auction {
    private static Salesmania plugin;

    private boolean isRunning = false;
    private Player winner;

    private long currentBid;

    public Auction(Salesmania plugin) {
        this.plugin = plugin;
    }

    public void reset() {

    }

    public boolean isRunning() {
        return isRunning;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean bid(Player player, long bid) {
        return false;
    }
}
