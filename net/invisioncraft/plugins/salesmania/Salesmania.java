package net.invisioncraft.plugins.salesmania;

import net.invisioncraft.plugins.salesmania.configuration.Settings;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Salesmania extends JavaPlugin {
    Logger consoleLogger;
    Economy economy;
    Settings settings;

    @Override
    public void onEnable() {
        settings = new Settings(this);

        consoleLogger = this.getLogger();
        consoleLogger.info("Salesmania Activated");
    }

    @Override
    public void onDisable() {

    }

    public Settings getSettings() {
        return settings;
    }
}
