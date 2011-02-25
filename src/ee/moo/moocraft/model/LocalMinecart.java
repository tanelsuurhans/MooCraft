package ee.moo.moocraft.model;

import net.minecraft.server.Entity;
import org.bukkit.craftbukkit.entity.CraftMinecart;
import org.bukkit.entity.Minecart;

/**
 * User: Tanel Suurhans
 * Date: 2/24/11
 */
public class LocalMinecart {

    private Minecart minecart;

    public LocalMinecart(Minecart minecart) {
        this.minecart = minecart;
    }

    public Boolean isMoving() {
        return getVelocityX() != 0D || getVelocityY() != 0D || getVelocityZ() != 0D;
    }

    public Double getVelocityX() {
        return minecart.getVelocity().getX();
    }

    public Double getVelocityY() {
        return minecart.getVelocity().getY();
    }

    public Double getVelocityZ() {
        return minecart.getVelocity().getZ();
    }

    public Entity getHandle() {
        return ((CraftMinecart) minecart).getHandle();
    }

}
