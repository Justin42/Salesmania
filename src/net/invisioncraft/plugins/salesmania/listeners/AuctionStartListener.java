package net.invisioncraft.plugins.salesmania.listeners;

import net.invisioncraft.plugins.salesmania.Auction;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Owner: Justin
 * Date: 5/25/12
 * Time: 1:23 AM
 */
public class AuctionStartListener implements Listener {
    Salesmania plugin;
    Auction auction;
    @EventHandler
    public void onAuctionStart(AuctionStartEvent startEvent) {
        auction = startEvent.getAuction();
        plugin = auction.getPlugin();
        broadcast();
    }

    public void broadcast() {
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            Locale locale = plugin.getLocaleHandler().getLocale(player);
            for(String message : locale.getMessageList("Auction.startInfo")) {
                message = auction.infoReplace(message);
                player.sendMessage(message);
            }
        }
    }
}
