package ee.moo.moocraft.listeners;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.player.MooPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.*;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class MooEntityListener extends EntityListener {

    private MooCraft plugin;

    public MooEntityListener(MooCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event) {

        if (event.getEntity() instanceof Player) {
            plugin.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + String.format("%s came to a miserable end.", ((Player) event.getEntity()).getDisplayName()));
        }

    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {

        if (event.getEntity() instanceof Player) {

            MooPlayer player = plugin.getPlayerManager().getPlayer(event.getEntity());

            if (player.isGodMode()) {
                event.setCancelled(true);
            }

        }

    }

    @Override
    public void onEntityCombust(EntityCombustEvent event) {

        if (event.getEntity() instanceof Player) {

            MooPlayer player = plugin.getPlayerManager().getPlayer(event.getEntity());

            if (player.isGodMode()) {
                event.setCancelled(true);
            }

        }

    }
    
}
