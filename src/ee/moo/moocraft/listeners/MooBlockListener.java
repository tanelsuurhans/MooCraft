package ee.moo.moocraft.listeners;

import ee.moo.moocraft.MooCraft;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;

import static org.bukkit.Material.LEAVES;

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

}
