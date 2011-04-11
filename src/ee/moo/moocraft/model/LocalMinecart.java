package ee.moo.moocraft.model;

import net.minecraft.server.Entity;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.entity.CraftMinecart;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.PoweredMinecart;
import org.bukkit.entity.StorageMinecart;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * User: Tanel Suurhans
 * Date: 2/24/11
 */
public class LocalMinecart implements Minecart {

    private Minecart minecart;

    private Vector previousMotion;
    private Vector previousLocation;

    private static double MAXIMUM_MOMENTUM = 1E150D;

    public LocalMinecart(Minecart minecart) {
        this.minecart = minecart;
        this.update();
    }

    public void update() {
        this.previousLocation = minecart.getLocation().toVector().clone();
        this.previousMotion = minecart.getVelocity().clone();
    }

    public Location getPreviousLocation() {
        return this.previousLocation.toLocation(minecart.getWorld());
    }

    public Vector getPreviousMotion() {
        return this.previousMotion;
    }

    public Block getBlockBelow(int depth) {
        return this.minecart.getWorld().getBlockAt(minecart.getLocation().getBlockX(), minecart.getLocation().getBlockY() - depth, minecart.getLocation().getBlockZ());
    }

    public void setSpeed(double speed) {

        double velocityX = getVelocityX() > 0 ? speed : getVelocityX() == 0 ? 0 : -speed;
        double velocityY = getVelocityY() > 0 ? speed : getVelocityY() == 0 ? 0 : -speed;
        double velocityZ = getVelocityZ() > 0 ? speed : getVelocityZ() == 0 ? 0 : -speed;

        setVelocity(new Vector(velocityX, velocityY, velocityZ));

    }

    public void doubleSpeed( ) {

        if (MAXIMUM_MOMENTUM / 2 > Math.abs(getVelocityX())) {
            setVelocityX(getVelocityX() * 2);
        }

        if (MAXIMUM_MOMENTUM / 2 > Math.abs(getVelocityY())) {
            setVelocityY(getVelocityY() * 2);
        }

        if (MAXIMUM_MOMENTUM / 2 > Math.abs(getVelocityZ())) {
            setVelocityZ(getVelocityZ() * 2);
        }

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

    public void setVelocityX(double velocityX) {
        setVelocity(new Vector(velocityX, getVelocityY(), getVelocityZ()));
    }

    public void setVelocityY(double velocityY) {
        setVelocity(new Vector(getVelocityX(), velocityY, getVelocityZ()));
    }

    public void setVelocityZ(double velocityZ) {
        setVelocity(new Vector(getVelocityX(), getVelocityY(), velocityZ));
    }

    public Entity getHandle() {
        return ((CraftMinecart) minecart).getHandle();
    }

    public boolean isPowered() {
        return (minecart instanceof PoweredMinecart);
    }

    public boolean isStorage() {
        return (minecart instanceof StorageMinecart);
    }

    public boolean isRegular() {
        return !isPowered() && !isStorage();
    }

    public void setDamage(int damage) {
        minecart.setDamage(damage);
    }

    public int getDamage() {
        return minecart.getDamage();
    }

    public double getMaxSpeed() {
        return minecart.getMaxSpeed();
    }

    public void setMaxSpeed(double speed) {
        minecart.setMaxSpeed(speed);
    }

    public boolean isSlowWhenEmpty() {
        return minecart.isSlowWhenEmpty();
    }

    public void setSlowWhenEmpty(boolean slow) {
        minecart.setSlowWhenEmpty(slow);
    }

    public Vector getFlyingVelocityMod() {
        return minecart.getFlyingVelocityMod();
    }

    public void setFlyingVelocityMod(Vector flying) {
        minecart.setFlyingVelocityMod(flying);
    }

    public Vector getDerailedVelocityMod() {
        return minecart.getDerailedVelocityMod();
    }

    public void setDerailedVelocityMod(Vector derailed) {
        minecart.setDerailedVelocityMod(derailed);
    }

    public Vector getVelocity() {
        return minecart.getVelocity();
    }

    public World getWorld() {
        return minecart.getWorld();
    }

    public boolean teleport(Location location) {
        return minecart.teleport(location);
    }

    public boolean teleport(org.bukkit.entity.Entity destination) {
        return minecart.teleport(destination);
    }

    public void teleportTo(Location location) {
        minecart.teleportTo(location);
    }

    public void teleportTo(org.bukkit.entity.Entity destination) {
        minecart.teleportTo(destination);
    }

    public List<org.bukkit.entity.Entity> getNearbyEntities(double x, double y, double z) {
        return minecart.getNearbyEntities(x, y, z);
    }

    public int getEntityId() {
        return minecart.getEntityId();
    }

    public int getFireTicks() {
        return minecart.getFireTicks();
    }

    public int getMaxFireTicks() {
        return minecart.getMaxFireTicks();
    }

    public void setFireTicks(int ticks) {
        minecart.setFireTicks(ticks);
    }

    public void remove() {
        minecart.remove();
    }

    public boolean isDead() {
        return minecart.isDead();
    }

    public Server getServer() {
        return minecart.getServer();
    }

    public Location getLocation() {
        return minecart.getLocation();
    }

    public void setVelocity(Vector vel) {
        minecart.setVelocity(vel);
    }

    public org.bukkit.entity.Entity getPassenger() {
        return minecart.getPassenger();
    }

    public boolean setPassenger(org.bukkit.entity.Entity passenger) {
        return minecart.setPassenger(passenger);
    }

    public boolean isEmpty() {
        return minecart.isEmpty();
    }

    public boolean eject() {
        return minecart.eject();
    }

    public float getFallDistance() {
        return minecart.getFallDistance();
    }

    public void setFallDistance(float distance) {
        minecart.setFallDistance(distance);
    }

}
