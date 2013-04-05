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
import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

import static net.invisioncraft.plugins.salesmania.commands.auction.AuctionCommandExecutor.*;

public class RegionSettings implements ConfigurationHandler {
    private FileConfiguration config;
    private Settings settings;
    private boolean isEnabled;
    private Salesmania plugin;

    private boolean wgEnabled;
    private WorldGuardPlugin worldGuard;
    private GlobalRegionManager regionManager;

    private HashMap<String, RegionAccess> accessMap;

    private class RegionAccess {
        private ArrayList<AuctionCommand> allowCommands;
        private ArrayList<AuctionCommand> denyCommands;

        public RegionAccess() {
            allowCommands = new ArrayList<AuctionCommand>();
            denyCommands = new ArrayList<AuctionCommand>();
        }

        public void allow(AuctionCommand command) {
            allowCommands.add(command);
        }

        public boolean isAllowed(AuctionCommand command) {
            return allowCommands.contains(command);
        }

        public void deny(AuctionCommand command) {
            denyCommands.add(command);
        }

        public boolean isDenied(AuctionCommand command) {
            return denyCommands.contains(command);
        }
    }

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

    public boolean isAllowed(Player player, String region, AuctionCommand command) {
        if(player.hasPermission("salesmania.auction.region-override")) return true;

        else if(isEnabled) {
            ArrayList<String> allowedCommands = config.getStringList("")


            switch(command) {
                case START:
                    break;

                default:
                    plugin.getLogger().warning("Unhandled command passed to RegionSettings.isAllowed");
            }
        }

        return true;
    }

    @Override
    public void update() {
        config = settings.getConfig();
        if(config.getBoolean("Auction.WorldGuardRegions.enabled") && wgEnabled) {
            isEnabled = true;
        }
        else isEnabled = false;
    }
}
