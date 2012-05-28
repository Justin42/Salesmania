package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;

import java.util.List;

/**
 * Owner: Justin
 * Date: 5/16/12
 * Time: 7:29 PM
 */
public class Locale extends Configuration {
    private String localeName;
    public Locale(Salesmania plugin, String locale) {
        super(plugin, locale + ".yml");
        localeName = locale;
        plugin.getLogger().info(String.format("Loaded locale messages for %s", locale));
    }

    public String getMessage(String path) {
        if(getConfig().contains(path)) return getConfig().getString(path);
        else return "Locale message not found.";
    }

    public List<String> getMessageList(String path) {
        return getConfig().getStringList(path);
    }

    public String getName() {
        return localeName;
    }
}
