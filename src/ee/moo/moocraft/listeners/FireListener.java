package ee.moo.moocraft.listeners;

import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Material.*;

/**
 * User: Tanel Suurhans
 * Date: 2/18/11
 */
public class FireListener extends BlockListener {

    private Plugin plugin;

    public FireListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onBlockBurn(BlockBurnEvent event) {

        if (event.getBlock().getType() == LEAVES) {
            event.setCancelled(true);
        }
        
    }

    @Override
    public void onBlockIgnite(BlockIgniteEvent event) {

        if (event.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
            event.setCancelled(true);
        }

    }
}
