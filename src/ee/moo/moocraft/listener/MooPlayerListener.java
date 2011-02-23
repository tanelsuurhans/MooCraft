package ee.moo.moocraft.listener;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.event.player.*;

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

    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().teleportTo(plugin.getPlayerManager().getHome(event.getPlayer()));
    }
}
