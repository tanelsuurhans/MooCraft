package ee.moo.moocraft.listeners;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class MooPlayerListener extends PlayerListener {

    private MooCraft plugin;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public MooPlayerListener(MooCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPlayerJoin(PlayerEvent event) {

        plugin.getPlayerManager().addPlayer(event.getPlayer());

        event.getPlayer().sendMessage(ChatColor.GOLD + "Hello " + event.getPlayer().getDisplayName());
        event.getPlayer().performCommand("playerlist");

    }

    @Override
    public void onPlayerKick(PlayerKickEvent event) {
        plugin.getPlayerManager().removePlayer(event.getPlayer());
    }

    @Override
    public void onPlayerQuit(PlayerEvent event) {
        plugin.getPlayerManager().removePlayer(event.getPlayer());
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        event.setFormat(ChatColor.DARK_GRAY + "[" + dateFormat.format(new Date()) + "]" + ChatColor.WHITE + " <%s> %s");
    }
}