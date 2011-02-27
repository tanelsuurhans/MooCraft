package ee.moo.moocraft.listener;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.Arrays;

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
    public void onBlockRightClick(BlockRightClickEvent event) {

        if (event.getItemInHand().getType() == FENCE) {

            Block target = event.getBlock().getFace(event.getDirection(), 1);

            if (target.getType() == AIR) {
                target.setType(event.getItemInHand().getType());

                if (event.getItemInHand().getAmount() > 1) {
                    event.getItemInHand().setAmount(event.getItemInHand().getAmount() - 1);
                } else {
                    event.getPlayer().getInventory().remove(event.getItemInHand());
                }

                event.getItemInHand().setAmount(event.getItemInHand().getAmount() - 1);
            }

        }

    }

    @Override
    public void onBlockDamage(BlockDamageEvent event) {

        LocalPlayer player = plugin.getPlayerManager().getPlayer(event.getPlayer());

        if (player.hasToolEquipped() && !player.getTool().isRightClick()) {
            player.getTool().perform(player, event.getBlock());
        }


    }
}
