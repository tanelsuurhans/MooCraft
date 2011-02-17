package ee.moo.moocraft.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * User: Tanel Suurhans
 * Date: 2/16/11
 */
public class PlayerJoinListener extends PlayerListener {

    private final Server server;

    public PlayerJoinListener(Server server) {
        this.server = server;
    }

    @Override
    public void onPlayerJoin(PlayerEvent event) {

        event.getPlayer().sendMessage(ChatColor.GOLD + "O Hai there " + event.getPlayer().getDisplayName());
        event.getPlayer().sendMessage(ChatColor.GOLD + "Online players: " + this.server.getOnlinePlayers().length);

        if (this.server.getOnlinePlayers().length > 0) {

            for(Player player : this.server.getOnlinePlayers()) {
                event.getPlayer().sendMessage(ChatColor.GRAY + player.getDisplayName());
            }

        }

    }
}
