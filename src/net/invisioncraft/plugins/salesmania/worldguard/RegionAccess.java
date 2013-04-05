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

    public boolean isAllowed(AuctionCommand command) {
        return !denyCommands.contains(command);
    }

    public void deny(AuctionCommand command) {
        denyCommands.add(command);
    }

    public boolean isDenied(AuctionCommand command) {
        return denyCommands.contains(command);
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

