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

package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Auction;
import net.invisioncraft.plugins.salesmania.AuctionQueue;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.worldgroups.WorldGroup;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AuctionQueueSettings extends Configuration {
    Salesmania plugin;

    public AuctionQueueSettings(Salesmania plugin) {
        super(plugin, "auctionQueue.yml");
        this.plugin = plugin;
    }

    public void loadQueue(AuctionQueue queue, WorldGroup worldGroup) {
        if(config.contains("Auctions")) {
            List<Auction> auctionList = new LinkedList<Auction>();
            List<Map<?, ?>> savedAuctions = config.getMapList("Auctions." + worldGroup.getGroupName());
            for(Map<?, ?> dataMap : savedAuctions) {
                ItemStack itemStack = (ItemStack) dataMap.get("itemStack");
                double startBid = (Double) dataMap.get("currentBid");
                OfflinePlayer owner = plugin.getServer().getOfflinePlayer((String)dataMap.get("owner"));

                OfflinePlayer winner;
                if(dataMap.containsKey("winner")) {
                    winner = plugin.getServer().getOfflinePlayer((String)dataMap.get("winner"));
                } else winner = null;
                Auction auction = new Auction(plugin, owner, winner, itemStack, startBid);
                auction.setWorldGroup(worldGroup);
                auctionList.add(auction);
            }
            queue.clear();
            queue.addAll(auctionList);
            if(plugin.getSettings().getAuctionSettings().getEnabled() && !queue.isRunning()) {
                queue.start();
            }
        }
    }

    public void saveAuction(Auction auction, WorldGroup worldGroup) {
        List<Map<?, ?>> auctionList;
        if(!config.contains("Auctions." + worldGroup.getGroupName())) {
            auctionList = new ArrayList<Map<?, ?>>();
        }
        else {
            auctionList = config.getMapList("Auctions." + worldGroup.getGroupName());
        }
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("itemStack", auction.getItemStack());
        dataMap.put("owner", auction.getOwner().getName());
        dataMap.put("currentBid", auction.getBid());
        auctionList.add(dataMap);
        config.set("Auctions." + worldGroup.getGroupName(), auctionList);
        save();
    }

    public void removeAuction(int position, WorldGroup worldGroup) {
        if(config.contains("Auctions")) {
            List<Map<?, ?>> auctionList = config.getMapList("Auctions." + worldGroup.getGroupName());
            auctionList.remove(position);
            config.set("Auctions." + worldGroup.getGroupName(), auctionList);
            save();
        }
    }

    @SuppressWarnings("unchecked")
    public void update(Auction currentAuction, WorldGroup worldGroup) {
        if(config.contains("Auctions")) {
            List<Map<?, ?>> auctionList = config.getMapList("Auctions." + worldGroup.getGroupName());
            Map<String, Object> dataMap = (Map<String, Object>) auctionList.get(0);
            dataMap.put("currentBid", currentAuction.getBid());
            dataMap.put("winner", currentAuction.getWinner().getName());
            auctionList.set(0, dataMap);
            config.set("Auctions." + worldGroup.getGroupName(), auctionList);
            save();
        }
    }

}
