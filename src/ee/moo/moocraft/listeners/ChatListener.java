package ee.moo.moocraft.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.Plugin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Tanel Suurhans
 * Date: 2/18/11
 */
public class ChatListener extends PlayerListener {

    private Plugin plugin;
    private DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public ChatListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPlayerChat(PlayerChatEvent event) {
        event.setFormat(ChatColor.DARK_GRAY + "[" + dateFormat.format(new Date()) + "]" + ChatColor.WHITE + " <%s> %s");
    }
}
