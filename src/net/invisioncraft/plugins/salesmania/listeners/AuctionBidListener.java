package net.invisioncraft.plugins.salesmania.listeners;

import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.event.AuctionBidEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Owner: Justin
 * Date: 5/25/12
 * Time: 1:24 AM
 */
public class AuctionBidListener implements Listener {
    @EventHandler
    public void onAuctionBid(AuctionBidEvent bidEvent) {
        Salesmania plugin = bidEvent.getAuction().getPlugin();

        for(Player player : Bukkit.getOnlinePlayers()) {
            Locale locale = plugin.getLocaleHandler().getLocale(player);
            String message = locale.getMessage("Auction.tag") +
                    String.format(locale.getMessage("Auction.bidRaised"),
                            bidEvent.getBid(), bidEvent.getBid());
            player.sendMessage(message);
        }

    }
}
