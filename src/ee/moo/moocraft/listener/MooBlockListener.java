package ee.moo.moocraft.listener;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.block.*;

import static org.bukkit.Material.*;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class MooBlockListener extends BlockListener {

    private MooCraft plugin;

    public MooBlockListener(MooCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onBlockBurn(BlockBurnEvent event) {

        if (!plugin.getConfigManager().isEnabled("fire.burn")) {

            if (event.getBlock().getType() == LEAVES) {
                event.setCancelled(true);
            }

        }

    }

    @Override
    public void onBlockIgnite(BlockIgniteEvent event) {

        if (!plugin.getConfigManager().isEnabled("fire.spread")) {

            if (event.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
                event.setCancelled(true);
            }

        }

    }

    @Override
    public void onBlockCanBuild(BlockCanBuildEvent event) {

        System.out.println(event.getMaterial());
        System.out.println(event.getBlock().getType());

        if (event.getMaterial() == Material.FENCE && event.getBlock().getType() == AIR) {
            event.setBuildable(true);
        }

    }

    @Override
    public void onBlockDamage(BlockDamageEvent event) {

        LocalPlayer player = plugin.getPlayerManager().getPlayer(event.getPlayer());

        if (player.hasToolEquipped() && !player.getTool().isRightClick()) {
            player.getTool().perform(player, event.getBlock());
        }

    }

    @Override
    public void onBlockRedstoneChange(BlockRedstoneEvent event) {

    }
    
}
