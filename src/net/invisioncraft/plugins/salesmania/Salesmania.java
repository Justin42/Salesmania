package net.invisioncraft.plugins.salesmania;

import net.invisioncraft.plugins.salesmania.commands.auction.AuctionCommandExecutor;
import net.invisioncraft.plugins.salesmania.configuration.Configuration;
import net.invisioncraft.plugins.salesmania.configuration.Locale;
import net.invisioncraft.plugins.salesmania.configuration.LocaleHandler;
import net.invisioncraft.plugins.salesmania.configuration.Settings;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.logging.Logger;

public class Salesmania extends JavaPlugin {
    private Logger consoleLogger;
    private Economy economy;
    private Settings settings;
    private Auction currentAuction;
    private LocaleHandler localeHandler;
    private HashSet<Configuration> configSet;

    @Override
    public void onEnable() {
        configSet = new HashSet<Configuration>();
        settings = new Settings(this);
        consoleLogger = this.getLogger();
        localeHandler = new LocaleHandler(this);

        getCommand("auction").setExecutor(new AuctionCommandExecutor(this));

        consoleLogger.info("Salesmania Activated");
    }

    @Override
    public void onDisable() {

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
            sender.sendMessage(String.format(
                    locale.getMessage("Etc.reloadConfig"),
                    config.getFilename()));
            config.reload();
        }
    }
}
