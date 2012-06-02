package net.invisioncraft.plugins.salesmania;

import net.invisioncraft.plugins.salesmania.commands.auction.AuctionCommandExecutor;
import net.invisioncraft.plugins.salesmania.commands.salesmania.SalesmaniaCommandExecutor;
import net.invisioncraft.plugins.salesmania.configuration.*;
import net.invisioncraft.plugins.salesmania.listeners.AuctionEventListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.logging.Logger;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 5/16/12
 */

public class Salesmania extends JavaPlugin {
    private Logger consoleLogger;
    private Economy economy;
    private Settings settings;
    private IgnoreAuction ignoreAuction;
    private Auction currentAuction;
    private LocaleHandler localeHandler;
    private HashSet<Configuration> configSet;

    private boolean usingVault = false;

    @Override
    public void onEnable() {
        configSet = new HashSet<Configuration>();
        settings = new Settings(this);
        consoleLogger = this.getLogger();
        localeHandler = new LocaleHandler(this);
        ignoreAuction = new IgnoreAuction(this);

        getCommand("auction").setExecutor(new AuctionCommandExecutor(this));
        getCommand("salesmania").setExecutor(new SalesmaniaCommandExecutor(this));
        getServer().getPluginManager().registerEvents(new AuctionEventListener(), this);

        // Vault
        if(getServer().getPluginManager().getPlugin("Vault") != null && getServer().getPluginManager().getPlugin("Vault").isEnabled()) {
            RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
            if(economyProvider != null) economy = economyProvider.getProvider();
            usingVault = true;
            consoleLogger.info("Found Vault.");
        }

        consoleLogger.info("Salesmania Activated");
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public Settings getSettings() {
        return settings;
    }

    public Auction getAuction() {
        if(currentAuction == null) {
            currentAuction = new Auction(this);
        }
        return currentAuction;
    }

    public LocaleHandler getLocaleHandler() {
        return localeHandler;
    }

    public void registerConfig(Configuration config) {
        configSet.add(config);
    }

    public void reloadConfig(CommandSender sender) {
        Locale locale = localeHandler.getLocale(sender);
        for(Configuration config : configSet) {
            consoleLogger.info(String.format(
                    locale.getMessage("Misc.reloadConfig"), config.getFilename()));
            sender.sendMessage(String.format(
                    locale.getMessage("Misc.reloadConfig"),
                    config.getFilename()));
            config.reload();
        }
    }

    @Override
    public void saveConfig() {
        Locale locale = localeHandler.getLocale(getServer().getConsoleSender());
        for(Configuration config : configSet) {
            config.save();
            consoleLogger.info(String.format(
                    locale.getMessage("Misc.saveConfig"), config.getFilename()));
        }
    }

    public boolean usingVault() {
        return usingVault;
    }

    public IgnoreAuction getIgnoreAuction() {
        return ignoreAuction;
    }
}
