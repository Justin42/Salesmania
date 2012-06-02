package net.invisioncraft.plugins.salesmania.listeners;

import net.invisioncraft.plugins.salesmania.Auction;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.AuctionSettings;
import net.invisioncraft.plugins.salesmania.configuration.IgnoreAuction;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.event.AuctionEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 5/25/12
 * Time: 5:08 AM
 */
public class AuctionEventListener implements Listener {
    AuctionEvent auctionEvent;
    Salesmania plugin;
    Auction auction;
    IgnoreAuction ignoreAuction;
    AuctionSettings auctionSettings;
    @EventHandler
    public void onAuctionEvent(AuctionEvent auctionEvent) {
        this.auctionEvent = auctionEvent;
        auction = auctionEvent.getAuction();
        plugin = auction.getPlugin();
        auctionSettings = plugin.getSettings().getAuctionSettings();
        ignoreAuction = plugin.getIgnoreAuction();
        switch (auctionEvent.getEventType()) {
            case BID: onAuctionBidEvent(); break;
            case END: onAuctionEndEvent(); break;
            case START: onAuctionStartEvent(); break;
            case TIMER: onAuctionTimerEvent(); break;
            case CANCEL: onAuctionCancelEvent(); break;
            case ENABLE: onAuctionEnableEvent(); break;
            case DISABLE: onAuctionDisableEvent(); break;
        }
    }

    private void onAuctionTimerEvent() {
        long timeRemaining = auctionEvent.getAuction().getTimeRemaining();
        List<Long> notifyTimes = auctionSettings.getNofityTime();
        if(notifyTimes.contains(timeRemaining)) {
            for(Player player : plugin.getServer().getOnlinePlayers()) {
                if(ignoreAuction.isIgnored(player)) continue;
                Locale locale = plugin.getLocaleHandler().getLocale(player);
                player.sendMessage(String.format(
                        locale.getMessage("Auction.tag") + locale.getMessage("Auction.timeRemaining"),
                        timeRemaining));
            }
        }
    }

    private void onAuctionStartEvent() {
        // Logging
        Locale locale = plugin.getLocaleHandler().getLocale(plugin.getServer().getConsoleSender());
        List<String> infoList = locale.getMessageList("Auction.startInfo");
        infoList = auction.infoReplace(infoList);
        infoList = auction.enchantReplace(infoList,
                locale.getMessage("Auction.enchant"),
                locale.getMessage("Auction.enchantInfo"), locale);
        plugin.getLogger().info(ChatColor.stripColor(infoList.toString()));

        // Broadcast
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if(ignoreAuction.isIgnored(player)) continue;
            locale = plugin.getLocaleHandler().getLocale(player);
            infoList = locale.getMessageList("Auction.startInfo");
            infoList = auction.infoReplace(infoList);
            infoList = auction.enchantReplace(infoList,
                    locale.getMessage("Auction.enchant"),
                    locale.getMessage("Auction.enchantInfo"), locale);
            infoList = auction.addTag(infoList, locale.getMessage("Auction.tag"));
            player.sendMessage(infoList.toArray(new String[0]));
        }
    }

    public void onAuctionBidEvent() {
        // Anti-Snipe
        if(auction.getTimeRemaining() < auctionSettings.getSnipeTime()) {
            auction.setTimeRemaining(auction.getTimeRemaining() + auctionSettings.getSnipeValue());
        }

        // Logging
        Locale locale = plugin.getLocaleHandler().getLocale(plugin.getServer().getConsoleSender());
        plugin.getLogger().info(ChatColor.stripColor(String.format(locale.getMessage("Auction.bidRaised"),
                auction.getCurrentBid(), auction.getWinner().getName())));

        // Broadcast
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if(ignoreAuction.isIgnored(player)) continue;
            locale = plugin.getLocaleHandler().getLocale(player);
            String message = locale.getMessage("Auction.tag");
            message += String.format(locale.getMessage("Auction.bidRaised"),
                    auction.getCurrentBid(), auction.getWinner().getName());
            player.sendMessage(message);
        }
    }

    public void onAuctionEndEvent() {
        Locale locale = plugin.getLocaleHandler().getLocale(plugin.getServer().getConsoleSender());
        // Broadcast
        if(plugin.getAuction().getWinner() == plugin.getAuction().getOwner()) {
            // Logging
            locale = plugin.getLocaleHandler().getLocale(plugin.getServer().getConsoleSender());
            plugin.getLogger().info(ChatColor.stripColor(locale.getMessage("Auction.noBids")));

            for(Player player : plugin.getServer().getOnlinePlayers()) {
                if(ignoreAuction.isIgnored(player)) continue;
                locale = plugin.getLocaleHandler().getLocale(player);
                player.sendMessage(locale.getMessage("Auction.tag") + locale.getMessage("Auction.noBids"));
            }
        }
        else  {
            // Logging
            List<String> infoList = locale.getMessageList("Auction.endInfo");
            infoList = auction.infoReplace(infoList);
            infoList = auction.enchantReplace(infoList,
                    locale.getMessage("Auction.enchant"),
                    locale.getMessage("Auction.enchantInfo"), locale);
            plugin.getLogger().info(ChatColor.stripColor(infoList.toString()));

            for(Player player : plugin.getServer().getOnlinePlayers()) {
                if(ignoreAuction.isIgnored(player)) continue;
                locale = plugin.getLocaleHandler().getLocale(player);
                infoList = locale.getMessageList("Auction.endInfo");
                infoList = auction.infoReplace(infoList);
                infoList = auction.enchantReplace(infoList,
                        locale.getMessage("Auction.enchant"),
                        locale.getMessage("Auction.enchantInfo"), locale);
                infoList = auction.addTag(infoList, locale.getMessage("Auction.tag"));
                player.sendMessage(infoList.toArray(new String[0]));
            }
        }
    }

    public void onAuctionCancelEvent() {
        // Logging
        Locale locale = plugin.getLocaleHandler().getLocale(plugin.getServer().getConsoleSender());
        plugin.getLogger().info(locale.getMessage("Auction.canceled"));

        // Broadcast
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if(ignoreAuction.isIgnored(player)) continue;
            locale = plugin.getLocaleHandler().getLocale(player);
            player.sendMessage(locale.getMessage("Auction.tag") + locale.getMessage("Auction.canceled"));
        }
    }

    public void onAuctionEnableEvent() {
        // Logging
        Locale locale = plugin.getLocaleHandler().getLocale(plugin.getServer().getConsoleSender());
        plugin.getLogger().info(locale.getMessage("Auction.enabled"));

        // Broadcast
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if(ignoreAuction.isIgnored(player)) continue;
            locale = plugin.getLocaleHandler().getLocale(player);
            player.sendMessage(locale.getMessage("Auction.tag") + locale.getMessage("Auction.enabled"));
        }
    }

    public void onAuctionDisableEvent() {
        // Logging
        Locale locale = plugin.getLocaleHandler().getLocale(plugin.getServer().getConsoleSender());
        plugin.getLogger().info(locale.getMessage("Auction.disabled"));

        // Broadcast
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            if(ignoreAuction.isIgnored(player)) continue;
            locale = plugin.getLocaleHandler().getLocale(player);
            player.sendMessage(locale.getMessage("Auction.tag") + locale.getMessage("Auction.disabled"));
        }
    }
}
