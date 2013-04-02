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

package net.invisioncraft.plugins.salesmania.event.auction;

import net.invisioncraft.plugins.salesmania.Auction;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class AuctionBidEvent extends AuctionEvent {

    public AuctionBidEvent(Auction auction) {
        super(auction);
    }

    public Player getBidder() {
        return auction.getWinner().getPlayer();
    }

    public double getBid() {
        return auction.getBid();
    }

    public OfflinePlayer getLastBidder() {
        return auction.getLastWinner();
    }

    public double getLastBid() {
        return auction.getLastBid();
    }
}
