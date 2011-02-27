package ee.moo.moocraft.listener;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Monster;
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

            LocalPlayer player = plugin.getPlayerManager().getPlayer((Player) event.getEntity());

            if (player.isGod())
                event.setCancelled(true);

        }

    }

    @Override
    public void onEntityCombust(EntityCombustEvent event) {

        if (event.getEntity() instanceof Player) {

            LocalPlayer player = plugin.getPlayerManager().getPlayer((Player) event.getEntity());

            if (player.isGod())
                event.setCancelled(true);

        }

    }

    @Override
    public void onCreatureSpawn(CreatureSpawnEvent event) {

        if (event.getEntity() instanceof Monster) {

            Block block = event.getLocation().getBlock();
            Material type = block.getType();

            if (type == Material.LEAVES || type == Material.LOG) {
                event.setCancelled(true);
            }

        }

    }
}
