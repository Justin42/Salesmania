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

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.GlobalRegionManager;
import com.sun.javaws.exceptions.InvalidArgumentException;
import net.invisioncraft.plugins.salesmania.Salesmania;
import net.invisioncraft.plugins.salesmania.worldguard.RegionAccess;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.invisioncraft.plugins.salesmania.commands.auction.AuctionCommandExecutor.AuctionCommand;

public class RegionSettings implements ConfigurationHandler {
    private FileConfiguration config;
    private Settings settings;
    private boolean isEnabled;
    private Salesmania plugin;

    private boolean wgEnabled;
    private WorldGuardPlugin worldGuard;
    private GlobalRegionManager regionManager;

    private HashMap<String, RegionAccess> accessMap;
    private static final String DEFAULT_MAP_KEY = "default";

    public RegionSettings(Settings settings) {
        this.settings = settings;
        plugin = settings.getPlugin();
        accessMap = new HashMap<String, RegionAccess>();

        worldGuard = (WorldGuardPlugin) plugin.getServer().getPluginManager().getPlugin("WorldGuard");
        if(worldGuard != null && worldGuard.isEnabled()) {
            wgEnabled = true;
            regionManager = worldGuard.getGlobalRegionManager();
        }

        update();
    }

    public boolean isAllowed(Player player, AuctionCommand command) {
        if(player.hasPermission("salesmania.auction.region-override")) return true;

        else if(isEnabled) {
        }

        return true;
    }

    public RegionAccess getRegionAccess(String region) {
        if(accessMap.containsKey(region)) return accessMap.get(region);
        else return accessMap.get(DEFAULT_MAP_KEY);
    }

    @SuppressWarnings("unchecked")
    public void parseRegions() {
        List defaultAllow = new ArrayList<AuctionCommand>();
        List defaultDeny = new ArrayList<AuctionCommand>();
        boolean defaultToStash = false;

        // Parse defaults
        try {
            defaultAllow = parseCommandList((List<String>)config.get("Auction.WorldGuardRegions.defaultAllow"));
            defaultDeny = parseCommandList((List<String>)config.get("Auction.WorldGuardRegions.defaultAllow"));
        } catch (ClassCastException | IllegalArgumentException ex) {
            plugin.getLogger().warning("Configuration for world guard region default allow/deny commands seems invalid.");
            if (ex instanceof  IllegalArgumentException) {
                plugin.getLogger().warning("Bad command '" + ((IllegalArgumentException) ex).getMessage() +  "' in world guard region default allow or deny list");
            }
        }


        // Parse regions
        for(Map<?,?> map : config.getMapList("Auction.WorldGuardRegions.regions")) {
            RegionAccess regionAccess = new RegionAccess();
            try {
                regionAccess.getAllowed().addAll(parseCommandList((List<String>)map.get("allow")));
                regionAccess.getDenied().addAll(parseCommandList((List<String>)map.get("deny")));
            } catch (ClassCastException | IllegalArgumentException ex) {
                plugin.getLogger().warning("Configuration for world guard region '" + map.get("regionName") + "' seems invalid.");
                if(ex instanceof IllegalArgumentException) {
                    plugin.getLogger().warning("Bad command in allow or deny list '" + ((IllegalArgumentException) ex).getMessage() + "'");
                }
            }
        }
    }

    // TODO
    private ArrayList<AuctionCommand> parseCommandList(List<String> cmdlist) throws IllegalArgumentException {
        for (String cmdString : cmdlist) {
            AuctionCommand cmd;
            try {

            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException(cmdString);
            }
        }
        return null;
    }



    @Override
    public void update() {
        config = settings.getConfig();
        if(config.getBoolean("Auction.WorldGuardRegions.enabled") && wgEnabled) {
            isEnabled = true;
        }
        else isEnabled = false;
        if(isEnabled) parseRegions();
    }
}
