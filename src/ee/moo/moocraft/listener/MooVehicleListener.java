package ee.moo.moocraft.listener;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalMinecart;
import net.minecraft.server.EntityBoat;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityMinecart;
import org.bukkit.Material;
import org.bukkit.craftbukkit.entity.CraftBoat;
import org.bukkit.craftbukkit.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.entity.CraftMinecart;
import org.bukkit.entity.*;
import org.bukkit.event.vehicle.*;
import org.bukkit.inventory.ItemStack;

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

                    try {
					    EntityLiving el = ((CraftLivingEntity) entity).getHandle();

                        // die and drop loot
                        // remove
						el.a(cart.getHandle());
                        el.C();
                    } catch (Exception e) {
                        entity.setHealth(0);
                    }

                }

            } else if (event.getEntity() instanceof Arrow) {
                event.setCancelled(true);
                event.setCollisionCancelled(true);
            }

        }

    }

    @Override
    public void onVehicleBlockCollision(VehicleBlockCollisionEvent event) {
        System.out.println(event.getBlock());
        System.out.println(event.getVehicle());
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

            CraftMinecart craftCart = (CraftMinecart) event.getVehicle();
            EntityMinecart entityCart = (EntityMinecart) craftCart.getHandle();

            if (entityCart.a * 10 > 40) {

                /** TODO: bukkit support */
                if (entityCart.d == CraftMinecart.Type.PoweredMinecart.getId()) {
                    event.getAttacker().getWorld().dropItemNaturally(craftCart.getLocation(), new ItemStack(Material.POWERED_MINECART, 1));
                } else if (entityCart.d == CraftMinecart.Type.StorageMinecart.getId()) {
                    event.getAttacker().getWorld().dropItemNaturally(craftCart.getLocation(), new ItemStack(Material.STORAGE_MINECART, 1));
                } else {
                    event.getAttacker().getWorld().dropItemNaturally(craftCart.getLocation(), new ItemStack(Material.MINECART, 1));
                }

                event.getVehicle().remove();
                event.setCancelled(true);
            }

        }

    }

}
