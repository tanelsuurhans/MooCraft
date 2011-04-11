package ee.moo.moocraft.listener;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalMinecart;
import net.minecraft.server.EntityBoat;
import net.minecraft.server.EntityLiving;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftBoat;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * User: Tanel Suurhans
 * Date: 2/24/11
 */
public class MooVehicleListener extends VehicleListener {

    private MooCraft plugin;

    public MooVehicleListener(MooCraft plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent event) {

        if (event.getVehicle() instanceof Minecart) {

            if (event.getEntity() instanceof LivingEntity) {

                if (event.getEntity() instanceof Player) {
                    return;
                }

                LivingEntity entity = (LivingEntity) event.getEntity();
                LocalMinecart cart = new LocalMinecart((Minecart) event.getVehicle());

                if (cart.isMoving()) {

                    entity.damage(entity.getHealth(), cart);
                    entity.remove();

                }

            } else if (event.getEntity() instanceof Arrow) {
                event.setCancelled(true);
                event.setCollisionCancelled(true);
            }

        }

    }

    @Override
    public void onVehicleDamage(VehicleDamageEvent event) {

        if (event.getVehicle() instanceof Boat) {

            CraftBoat craftBoat = (CraftBoat) event.getVehicle();
            EntityBoat entityBoat = (EntityBoat) craftBoat.getHandle();

            /** TODO: bukkit support */
            if (entityBoat.a * 10 > 40) {
                event.getAttacker().getWorld().dropItemNaturally(craftBoat.getLocation(), new ItemStack(Material.BOAT, 1));
                event.getVehicle().remove();
                event.setCancelled(true);
            }

        } else if (event.getVehicle() instanceof Minecart) {

            LocalMinecart minecart = plugin.getMinecartManager().getMinecart(event.getVehicle());

            if (minecart.getDamage() > 40) {

                if (minecart.isPowered()) {
                    event.getAttacker().getWorld().dropItemNaturally(minecart.getLocation(), new ItemStack(Material.POWERED_MINECART, 1));
                } else if (minecart.isStorage()) {
                    event.getAttacker().getWorld().dropItemNaturally(minecart.getLocation(), new ItemStack(Material.STORAGE_MINECART, 1));
                } else {                    
                    event.getAttacker().getWorld().dropItemNaturally(minecart.getLocation(), new ItemStack(Material.MINECART, 1));
                }

                plugin.getMinecartManager().removeMinecart(minecart);

                event.getVehicle().remove();
                event.setCancelled(true);
            }

        }

    }

    @Override
    public void onVehicleMove(VehicleMoveEvent event) {

        if (event.getVehicle() instanceof Minecart) {

            LocalMinecart minecart = plugin.getMinecartManager().getMinecart(event.getVehicle());

            Block currentBlock = minecart.getLocation().getBlock();
            Block previousBlock = minecart.getPreviousLocation().getBlock();

            if (currentBlock.getType() == Material.WOOD_PLATE || currentBlock.getType() == Material.STONE_PLATE) {
                minecart.setDerailedVelocityMod(new Vector(1, 1, 1));
            } else if (previousBlock.getType() == Material.WOOD_PLATE || previousBlock.getType() == Material.STONE_PLATE) {
                minecart.setDerailedVelocityMod(new Vector(0.5D, 0.5D, 0.5D));
            }

            minecart.update();

        }

    }

    @Override
    public void onVehicleUpdate(VehicleUpdateEvent event) {

        if (event.getVehicle() instanceof Minecart) {

            LocalMinecart minecart = plugin.getMinecartManager().getMinecart(event.getVehicle());
            Block block = minecart.getBlockBelow(2);

            if (block.getType() == Material.IRON_BLOCK) {

                if (minecart.isMoving()) {
                    minecart.setSpeed(0.1D);
                } else if (!minecart.isEmpty()) {
                    minecart.setVelocity(minecart.getPassenger().getLocation().getDirection());
                    minecart.setSpeed(0.1D);
                }

            } else if (block.getType() == Material.GOLD_BLOCK) {

                if (minecart.isMoving()) {
                    minecart.doubleSpeed();
                }

            } else if (block.getType() == Material.WOOL) {

            }

        }

    }
}
