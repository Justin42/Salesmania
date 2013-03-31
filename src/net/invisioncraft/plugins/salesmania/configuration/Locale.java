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

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Locale extends Configuration {
    private String localeName;
    private ArrayList<Player> playerCache;
    protected Locale(Salesmania plugin, String locale) {
        super(plugin, locale + ".yml");
        localeName = locale;
        playerCache = new ArrayList<Player>();
    }

    public String getMessage(String path) {
        if(getConfig().contains(path)) return getConfig().getString(path).
                replace("&", String.valueOf(ChatColor.COLOR_CHAR));
        else return "Locale message not found.";
    }

    public ArrayList<String> getMessageList(String path) {
        ArrayList<String> messageList = new ArrayList<String>();
        for(String message : getConfig().getStringList(path)) {
            messageList.add(message.replace("&", String.valueOf(ChatColor.COLOR_CHAR)));
        }
        return messageList;
    }

    public String getName() {
        return localeName;
    }

    public void addPlayer(Player player) {
        playerCache.add(player);
    }

    public void removePlayer(Player player) {
        playerCache.remove(player);
    }

    public ArrayList<Player> getPlayers() {
        return playerCache;
    }
}
