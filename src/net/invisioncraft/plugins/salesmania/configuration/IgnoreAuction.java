package net.invisioncraft.plugins.salesmania.configuration;

import net.invisioncraft.plugins.salesmania.Salesmania;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Owner: Byte 2 O Software LLC
 * Date: 5/29/12
 * Time: 2:19 PM
 */
public class IgnoreAuction extends Configuration {
    public IgnoreAuction(Salesmania plugin) {
        super(plugin, "auctionIgnore.yml");
    }

    public boolean isIgnored(CommandSender sender) {
        List<String> ignoreList = getConfig().getStringList("Ignore");
        if(ignoreList.contains(sender.getName())) return true;
        else return false;
    }

    public void setIgnore(CommandSender sender, boolean ignored) {
        List<String> ignoreList = getConfig().getStringList("Ignore");
        ignoreList.remove(sender.getName());
        getConfig().set("Ignore", ignoreList);
    }

    public boolean toggleIgnore(CommandSender sender) {
        boolean hasIgnored;
        List<String> ignoreList = getConfig().getStringList("Ignore");
        if(ignoreList.contains(sender.getName())) {
            ignoreList.remove(sender.getName());
            hasIgnored = false;
        }
        else {
            ignoreList.add(sender.getName());
            hasIgnored = true;
        }
        getConfig().set("Ignore", ignoreList);
        return hasIgnored;
    }
}
