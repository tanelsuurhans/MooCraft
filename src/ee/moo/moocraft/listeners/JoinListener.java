package ee.moo.moocraft.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.Plugin;

/**
 * User: Tanel Suurhans
 * Date: 2/16/11
 */
public class JoinListener extends PlayerListener {

    private Plugin plugin;

    public JoinListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPlayerJoin(PlayerEvent event) {

        event.getPlayer().sendMessage(ChatColor.GOLD + "Hello " + event.getPlayer().getDisplayName());
        event.getPlayer().performCommand("playerlist");

    }
    
}
