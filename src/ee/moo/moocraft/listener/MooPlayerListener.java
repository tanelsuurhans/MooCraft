package ee.moo.moocraft.listener;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalPlayer;
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
    public void onPlayerJoin(PlayerJoinEvent event) {

        plugin.getPlayerManager().addPlayer(event.getPlayer());

        event.getPlayer().sendMessage(ChatColor.GOLD + "Hello " + event.getPlayer().getDisplayName());
        event.getPlayer().performCommand("playerlist");

    }

    @Override
    public void onPlayerKick(PlayerKickEvent event) {
        plugin.getPlayerManager().removePlayer(event.getPlayer());
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getPlayerManager().removePlayer(event.getPlayer());
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        event.setFormat(ChatColor.WHITE + "[" + dateFormat.format(new Date()) + "]" + ChatColor.WHITE + " <%s> %s");
    }

    @Override
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        plugin.getPlayerManager().getPlayer(event.getPlayer()).teleportHome();
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {

        LocalPlayer player = plugin.getPlayerManager().getPlayer(event.getPlayer());

        if (player.hasToolEquipped() && player.getTool().isRightClick()) {
            player.getTool().perform(player, event.getClickedBlock());
        }

    }

}
