package net.invisioncraft.plugins.salesmania;

import net.invisioncraft.plugins.salesmania.configuration.Settings;
import org.bukkit.entity.Player;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 3:59 PM
 */
public class Auction {
    private Salesmania plugin;
    private Settings settings;

    private boolean isRunning = false;

    private Player currentWinner;
    private long currentBid;

    public Auction(Salesmania plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettings();
    }

    public void reset() {

    }

    public boolean isRunning() {
        return isRunning;
    }

    public Player getWinner() {
        return currentWinner;
    }

    public boolean bid(Player player, long bid) {
        if(currentBid + bid > bid + settings.getMaxIncrement()) {
            return false;
        }
        if(currentBid + bid < bid + settings.getMinIncrement()) {
            return false;
        }
        currentWinner = player;
        currentBid = bid;
        return true;
    }

}
