package net.invisioncraft.plugins.salesmania;

import net.invisioncraft.plugins.salesmania.configuration.Settings;
import net.invisioncraft.plugins.salesmania.event.AuctionEndEvent;
import net.invisioncraft.plugins.salesmania.event.AuctionStartEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Owner: Justin
 * Date: 5/17/12
 * Time: 3:59 PM
 */
public class Auction {
    private static long TICKS_PER_SECOND = 20;
    private Salesmania plugin;
    private Settings settings;

    private boolean isRunning = false;
    private boolean inCooldown = false;

    private Player currentWinner;
    private Player owner;
    private long currentBid;

    private ItemStack itemStack;

    private long timeRemaining = 0;

    private Runnable cooldownRunnable = new Runnable() {
        @Override
        public void run() {
            inCooldown = false;
        }
    };

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            timeRemaining -= 1;
            if(timeRemaining == 0) end();
        }
    };

    public static enum AuctionStatus {
        OVER_MAX,
        UNDER_MIN,
        SUCCESS,
        FAILURE,
        RUNNING,
        COOLDOWN,
        WINNING,
        NOT_RUNNING,
        CANCELED
    }

    public Auction(Salesmania plugin) {
        this.plugin = plugin;
        this.settings = plugin.getSettings();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isInCooldown() {
        return inCooldown;
    }

    public Player getWinner() {
        return currentWinner;
    }

    public Player getOwner() {
        return owner;
    }

    public long getCurrentBid() {
        return currentBid;
    }

    public long getMaxBid() {
        return currentBid + settings.getMaxIncrement();
    }

    public long getMinBid() {
        return currentBid + settings.getMinIncrement();
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public AuctionStatus start(Player player, ItemStack itemStack, long startBid)  {
        if(isRunning()) return AuctionStatus.RUNNING;
        if(isInCooldown()) return AuctionStatus.COOLDOWN;
        if(startBid < settings.getMinStart()) return AuctionStatus.UNDER_MIN;
        if(startBid > settings.getMaxStart()) return AuctionStatus.OVER_MAX;

        currentBid = startBid;
        this.itemStack = itemStack;
        this.owner = player;
        timeRemaining = settings.getDefaultTime();
        Bukkit.getServer().getPluginManager().callEvent(new AuctionStartEvent(this));
        return AuctionStatus.SUCCESS;
    }

    public AuctionStatus bid(Player player, long bid) {
        if(!isRunning) return AuctionStatus.NOT_RUNNING;
        if(currentBid + bid > bid + settings.getMaxIncrement()) return AuctionStatus.OVER_MAX;
        if(currentBid + bid < bid + settings.getMinIncrement()) return AuctionStatus.UNDER_MIN;
        if(currentWinner != null && currentWinner == player) return AuctionStatus.WINNING;

        currentWinner = player;
        currentBid = bid;
        return AuctionStatus.SUCCESS;
    }

    public void end() {
        Bukkit.getServer().getPluginManager().callEvent(new AuctionEndEvent(this, AuctionStatus.SUCCESS));
        isRunning = false;
        inCooldown = true;
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,cooldownRunnable, settings.getCooldown()*TICKS_PER_SECOND);
    }

    public void cancel() {
        Bukkit.getServer().getPluginManager().callEvent(new AuctionEndEvent(this, AuctionStatus.CANCELED));
        isRunning = false;
        inCooldown = true;
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, cooldownRunnable, settings.getCooldown()*TICKS_PER_SECOND);
    }

    public String infoReplace(String info) {
        info.replace("%owner%", owner.getName());
        info.replace("%quantity%", String.valueOf(itemStack.getAmount()));
        info.replace("%item%", itemStack.getType().name());
        info.replace("%bid%", String.valueOf(currentBid));
        info.replace("%winner%", currentWinner.getName());
        return info;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }
}
