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

package net.invisioncraft.plugins.salesmania.worldguard;

import net.invisioncraft.plugins.salesmania.commands.auction.AuctionCommandExecutor;

import java.util.ArrayList;

import static net.invisioncraft.plugins.salesmania.commands.auction.AuctionCommandExecutor.*;

public class RegionAccess {
    private ArrayList<AuctionCommand> denyCommands;
    private boolean itemsToStash;

    public RegionAccess() {
        denyCommands = new ArrayList<>();
        itemsToStash = false;
    }

    public void allow(AuctionCommand command) {
        if(denyCommands.contains(command)) denyCommands.remove(command);
    }

    // Todo this is a bit messy...
    public boolean isAllowed(AuctionCommand command) {
        if(denyCommands.contains(command)) return false;
        else if(denyCommands.contains(AuctionCommand.ALL)) return false;
        // Account for aliases
        else switch(command) {
                case START:
                case S:
                    return !denyCommands.contains(AuctionCommand.START);
                case BID:
                case B:
                    return !denyCommands.contains(AuctionCommand.BID);
                case LIST:
                case L:
                case QUEUE:
                case Q:
                    return !denyCommands.contains(AuctionCommand.LIST);
                default: return true;
        }
    }

    public void deny(AuctionCommand command) {
        denyCommands.add(command);
    }

    public boolean isDenied(AuctionCommand command) {
        return !isAllowed(command);
    }

    public ArrayList<AuctionCommandExecutor.AuctionCommand> getDenied() {
        return denyCommands;
    }

    public boolean itemsToStash() {
        return itemsToStash;
    }

    public void setItemsToStash(boolean itemsToStash) {
        this.itemsToStash = itemsToStash;
    }
}

