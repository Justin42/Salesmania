/*
This file is part of Salesmania.

    Salesmania is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Salesmania is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Salesmania.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.invisioncraft.plugins.salesmania.listeners;

import net.invisioncraft.plugins.salesmania.Auction;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.channels.ChannelManager;
import net.invisioncraft.plugins.salesmania.configuration.AuctionIgnoreList;
import net.invisioncraft.plugins.salesmania.configuration.AuctionSettings;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.configuration.LocaleHandler;
import net.invisioncraft.plugins.salesmania.event.AuctionEvent;
import net.invisioncraft.plugins.salesmania.util.ItemManager;
import net.invisioncraft.plugins.salesmania.util.MsgUtil;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroupManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AuctionEventListener implements Listener {
    Salesmania plugin;
    AuctionIgnoreList auctionIgnoreList;
    AuctionSettings auctionSettings;
    Economy economy;
    LocaleHandler localeHandler;
    ChannelManager channelManager;
    WorldGroupManager worldGroupManager;

    public AuctionEventListener(Salesmania plugin) {
        this.plugin = plugin;
        auctionSettings =  plugin.getSettings().getAuctionSettings();
        auctionIgnoreList = plugin.getAuctionIgnoreList();
        economy = plugin.getEconomy();
        localeHandler = plugin.getLocaleHandler();
        channelManager = plugin.getChannelManager();
        worldGroupManager = plugin.getWorldGroupManager();
    }

    public void onAuctionReload(AuctionEvent auctionEvent) {
        auctionSettings =  plugin.getSettings().getAuctionSettings();
        auctionIgnoreList = plugin.getAuctionIgnoreList();
        economy = plugin.getEconomy();
        localeHandler = plugin.getLocaleHandler();
    }

    @EventHandler
    public void onAuctionEvent(AuctionEvent auctionEvent) {
        auctionSettings = plugin.getSettings().getAuctionSettings();
        auctionIgnoreList = plugin.getAuctionIgnoreList();
        economy = plugin.getEconomy();
        localeHandler = plugin.getLocaleHandler();

        switch (auctionEvent.getEventType()) {
            case BID: onAuctionBidEvent(auctionEvent); break;
            case END: onAuctionEndEvent(auctionEvent); break;
            case START: onAuctionStartEvent(auctionEvent); break;
            case TIMER: onAuctionTimerEvent(auctionEvent); break;
            case CANCEL: onAuctionCancelEvent(auctionEvent); break;
            case ENABLE: onAuctionEnableEvent(auctionEvent); break;
            case DISABLE: onAuctionDisableEvent(auctionEvent); break;
            case RELOAD: onAuctionReload(auctionEvent); break;
            case QUEUED: onAuctionQueue(auctionEvent); break;
        }
    }

    private void processTax(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();
        OfflinePlayer owner = auction.getOwner();
        Locale locale = localeHandler.getLocale(owner.getPlayer());
        double taxAmount = 0;
        switch(auctionEvent.getEventType()) {
            case START:
                taxAmount = auction.getStartTax();
                break;
            case END:
                taxAmount = auction.getEndTax();
                break;
            default: return;
        }
        if(taxAmount != 0) {
            economy.withdrawPlayer(owner.getName(), taxAmount);
            if(auctionSettings.useTaxAccount()) {
                economy.depositPlayer(auctionSettings.getTaxAccount(), taxAmount);
            }
            if(owner.isOnline()) {
            owner.getPlayer().sendMessage(String.format(locale.getMessage("Auction.tax"),
                    taxAmount));
            }
        }
    }

    private void onAuctionTimerEvent(AuctionEvent auctionEvent) {
        long timeRemaining = auctionEvent.getAuction().getTimeRemaining();
        List<Long> notifyTimes = auctionSettings.getNofityTime();
        OfflinePlayer player = auctionEvent.getAuction().getOwner();
        WorldGroup worldGroup = worldGroupManager.getGroup(player);
        if(notifyTimes.contains(timeRemaining)) {
            for(Locale locale : localeHandler.getLocales()) {
                String message =
                        locale.getMessage("Auction.tag") +
                        String.format(locale.getMessage("Auction.timeRemaining"), timeRemaining);
                channelManager.broadcast(worldGroup, message, locale.getPlayers());
            }

        }
    }

    private void onAuctionStartEvent(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();
        OfflinePlayer player = auctionEvent.getAuction().getOwner();
        WorldGroup worldGroup = worldGroupManager.getGroup(player);
        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            ArrayList<String> infoList = locale.getMessageList("Auction.startInfo");
            infoList = auction.infoReplace(infoList);
            infoList = auction.enchantReplace(infoList,
                    locale.getMessage("Auction.enchant"),
                    locale.getMessage("Auction.enchantInfo"), locale);
            infoList = MsgUtil.addPrefix(infoList, locale.getMessage("Auction.tag"));
            String[] message = infoList.toArray(new String[infoList.size()]);
            channelManager.broadcast(worldGroup, message, locale.getPlayers());
        }
    }

    private void onAuctionQueue(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();

        // Take item
        ItemManager.takeItem(auction.getOwner().getPlayer(), auction.getItemStack());

        // Tax
        processTax(auctionEvent);
    }

    public void onAuctionBidEvent(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();
        // Anti-Snipe
        if(auction.getTimeRemaining() < auctionSettings.getSnipeTime()) {
            auction.setTimeRemaining(auction.getTimeRemaining() + auctionSettings.getSnipeValue());
        }

        // Give back last bid
        if(auction.getLastWinner() != null) {
            OfflinePlayer player = auction.getLastWinner();
            economy.depositPlayer(player.getName(), auction.getLastBid());
            if(player.getPlayer().isOnline()) {
                Locale locale = plugin.getLocaleHandler().getLocale(player.getPlayer());
                player.getPlayer().sendMessage(String.format(
                        locale.getMessage("Auction.Bidding.outBid"), auction.getWinner().getName()));
            }
        }

        // Take new bid
        economy.withdrawPlayer(auction.getWinner().getName(), auction.getBid());

        WorldGroup worldGroup = plugin.getWorldGroupManager().getGroup(auction.getOwner());
        worldGroup.getAuctionQueue().update();

        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            String message = locale.getMessage("Auction.tag") +
            String.format(locale.getMessage("Auction.bidRaised"),
                auction.getBid(), auction.getWinner().getName());
            channelManager.broadcast(worldGroup, message, locale.getPlayers());
        }
    }

    public void onAuctionEndEvent(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();
        WorldGroup worldGroup = plugin.getWorldGroupManager().getGroup(auction.getOwner());

        // NO BIDS
        if(auctionEvent.getAuction().getWinner() == null) {
            // Broadcast
            for(Locale locale : localeHandler.getLocales()) {
                String message =
                        locale.getMessage("Auction.tag") +
                        locale.getMessage("Auction.noBids");
                channelManager.broadcast(worldGroup, message, locale.getPlayers());
            }

            // Tax
            if(auctionSettings.taxIfNoBids()) {
                processTax(auctionEvent);
            }

            // Give back item to owner
            giveItem(auction.getOwner(), auction.getItemStack(), auction.getWorldGroup());

        }

        // BIDS
        else  {

            // Broadcast
            for(Locale locale : localeHandler.getLocales()) {
                ArrayList<String> infoList = locale.getMessageList("Auction.endInfo");
                infoList = auction.infoReplace(infoList);
                infoList = auction.enchantReplace(infoList,
                        locale.getMessage("Auction.enchant"),
                        locale.getMessage("Auction.enchantInfo"), locale);
                infoList = MsgUtil.addPrefix(infoList, locale.getMessage("Auction.tag"));
                String[] message = infoList.toArray(new String[infoList.size()]);
                channelManager.broadcast(worldGroup, message, locale.getPlayers());
            }

            // Give money to owner
            economy.depositPlayer(auction.getOwner().getName(), auction.getBid());

            // Tax
            processTax(auctionEvent);

            // Give item to winner
            giveItem(auction.getWinner(), auction.getItemStack(), auction.getWorldGroup());
        }

        worldGroup.getAuctionQueue().remove();
        worldGroup.getAuctionQueue().startCooldown();
    }

    public void onAuctionCancelEvent(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();
        WorldGroup worldGroup = worldGroupManager.getGroup(auctionEvent.getAuction().getOwner());

        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            String message = locale.getMessage("Auction.tag") +
                    locale.getMessage("Auction.canceled");
            channelManager.broadcast(worldGroup, message, locale.getPlayers());
        }

        // Give back bid
        if(auction.getWinner() != null) {
            economy.depositPlayer(auction.getWinner().getName(), auction.getBid());
        }

        // Give back item to owner
        giveItem(auction.getOwner(), auction.getItemStack(), auction.getWorldGroup());

        worldGroup.getAuctionQueue().remove();
        worldGroup.getAuctionQueue().startCooldown();
    }

    public void onAuctionEnableEvent(AuctionEvent auctionEvent) {
        // Start the queue
        WorldGroup worldGroup = plugin.getWorldGroupManager().getGroup(auctionEvent.getAuction().getOwner());
        worldGroup.getAuctionQueue().start();

        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            String message = locale.getMessage("Auction.tag") +
                    locale.getMessage("Auction.enabled");
            channelManager.broadcast(worldGroup, message, locale.getPlayers());
        }
    }

    public void onAuctionDisableEvent(AuctionEvent auctionEvent) {
        // Stop the queue
        WorldGroup worldGroup = plugin.getWorldGroupManager().getGroup(auctionEvent.getAuction().getOwner());
        worldGroup.getAuctionQueue().stop();

        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            String message = locale.getMessage("Auction.tag") +
                    locale.getMessage("Auction.disabled");
            channelManager.broadcast(worldGroup, message, locale.getPlayers());
        }
    }

    private void giveItem(OfflinePlayer player, ItemStack itemStack, WorldGroup worldGroup) {
        if(player.isOnline()) {
            Locale locale = plugin.getLocaleHandler().getLocale(player.getPlayer());
            HashMap<Integer, ItemStack> remainingItems = player.getPlayer().getInventory().addItem(itemStack);
            if(!remainingItems.isEmpty()) {
                plugin.getItemStash().store(player, new ArrayList<ItemStack>(remainingItems.values()), worldGroup);
                player.getPlayer().sendMessage(locale.getMessage("Stash.itemsWaiting"));
            }
        }
        else plugin.getItemStash().store(player, itemStack, worldGroup);
    }
}
