/*
Copyright 2012 Byte 2 O Software LLC
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.invisioncraft.plugins.salesmania.listeners;

import net.invisioncraft.plugins.salesmania.Auction;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.configuration.AuctionIgnoreList;
import net.invisioncraft.plugins.salesmania.configuration.AuctionSettings;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.configuration.LocaleHandler;
import net.invisioncraft.plugins.salesmania.event.AuctionEvent;
import net.invisioncraft.plugins.salesmania.util.ItemManager;
import net.invisioncraft.plugins.salesmania.util.MsgUtil;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
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

    public AuctionEventListener(Salesmania plugin) {
        this.plugin = plugin;
        auctionSettings =  plugin.getSettings().getAuctionSettings();
        auctionIgnoreList = plugin.getAuctionIgnoreList();
        economy = plugin.getEconomy();
        localeHandler = plugin.getLocaleHandler();
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
        Player owner = auction.getOwner();
        Locale locale = localeHandler.getLocale(owner);
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
            owner.sendMessage(String.format(locale.getMessage("Auction.tax"),
                    taxAmount));
        }
    }

    private void onAuctionTimerEvent(AuctionEvent auctionEvent) {
        long timeRemaining = auctionEvent.getAuction().getTimeRemaining();
        List<Long> notifyTimes = auctionSettings.getNofityTime();
        if(notifyTimes.contains(timeRemaining)) {
            for(Locale locale : localeHandler.getLocales()) {
                String message =
                        locale.getMessage("Auction.tag") +
                        String.format(locale.getMessage("Auction.timeRemaining"), timeRemaining);
                locale.broadcastMessage(message, auctionIgnoreList);
            }

        }
    }

    private void onAuctionStartEvent(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();

        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            ArrayList<String> infoList = locale.getMessageList("Auction.startInfo");
            infoList = auction.infoReplace(infoList);
            infoList = auction.enchantReplace(infoList,
                    locale.getMessage("Auction.enchant"),
                    locale.getMessage("Auction.enchantInfo"), locale);
            infoList = MsgUtil.addPrefix(infoList, locale.getMessage("Auction.tag"));
            locale.broadcastMessage(infoList, auctionIgnoreList);
        }
    }

    private void onAuctionQueue(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();

        // Take item
        ItemManager.takeItem(auction.getOwner(), auction.getItemStack());

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
            Player player = auction.getLastWinner();
            economy.depositPlayer(player.getName(), auction.getLastBid());
            Locale locale = plugin.getLocaleHandler().getLocale(player);
            player.sendMessage(String.format(
                    locale.getMessage("Auction.Bidding.outBid"), auction.getWinner().getName()));
        }

        // Take new bid
        economy.withdrawPlayer(auction.getWinner().getName(), auction.getBid());

        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            String message = locale.getMessage("Auction.tag") +
            String.format(locale.getMessage("Auction.bidRaised"),
                auction.getBid(), auction.getWinner().getName());
            locale.broadcastMessage(message, auctionIgnoreList);
        }
    }

    public void onAuctionEndEvent(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();
        // NO BIDS
        if(auctionEvent.getAuction().getWinner() == null) {
            // Broadcast
            for(Locale locale : localeHandler.getLocales()) {
                String message =
                        locale.getMessage("Auction.tag") +
                        locale.getMessage("Auction.noBids");
                locale.broadcastMessage(message, auctionIgnoreList);
            }

            // Tax
            if(auctionSettings.taxIfNoBids()) {
                processTax(auctionEvent);
            }

            // Give back item to owner
            giveItem(auction.getOwner(), auction.getItemStack());
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
                locale.broadcastMessage(infoList, auctionIgnoreList);
            }

            // Give money to owner
            economy.depositPlayer(auction.getOwner().getName(), auction.getBid());

            // Tax
            processTax(auctionEvent);

            // Give item to winner
            giveItem(auction.getWinner(), auction.getItemStack());
        }
    }

    public void onAuctionCancelEvent(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();
        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            String message = locale.getMessage("Auction.tag") +
                    locale.getMessage("Auction.canceled");
            locale.broadcastMessage(message, auctionIgnoreList);
        }

        // Give back bid
        if(auction.getWinner() != null) {
            economy.depositPlayer(auction.getWinner().getName(), auction.getBid());
        }

        // Give back item to owner
        giveItem(auction.getOwner(), auction.getItemStack());
    }

    public void onAuctionEnableEvent(AuctionEvent auctionEvent) {
        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            String message = locale.getMessage("Auction.tag") +
                    locale.getMessage("Auction.enabled");
            locale.broadcastMessage(message, auctionIgnoreList);
        }
    }

    public void onAuctionDisableEvent(AuctionEvent auctionEvent) {
        Auction auction = auctionEvent.getAuction();
        // Cancel current auction
        auction.cancel();

        // Broadcast
        for(Locale locale : localeHandler.getLocales()) {
            String message = locale.getMessage("Auction.tag") +
                    locale.getMessage("Auction.disabled");
            locale.broadcastMessage(message, auctionIgnoreList);
        }
    }

    private void giveItem(Player player, ItemStack itemStack) {
        Locale locale = plugin.getLocaleHandler().getLocale(player);
        if(player.isOnline()) {
            HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(itemStack);
            if(remainingItems.isEmpty()) return;
            else {
                plugin.getItemStash().store(player, new ArrayList<ItemStack>(remainingItems.values()));
                player.sendMessage(locale.getMessage("Stash.itemsWaiting"));
            }
        }
        else plugin.getItemStash().store(player, itemStack);
    }
}
