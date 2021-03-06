package ee.moo.moocraft.model;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;

/**
 * User: Tanel Suurhans
 * Date: 2/27/11
 */
public abstract class BasePlayer implements Player {

    protected Player player;

    public boolean isOnline() {
        return player.isOnline();
    }

    public String getDisplayName() {
        return player.getDisplayName();
    }

    public void setDisplayName(String name) {
        player.setDisplayName(name);
    }

    public void setCompassTarget(Location loc) {
        player.setCompassTarget(loc);
    }

    public Location getCompassTarget() {
        return player.getCompassTarget();
    }

    public void saveData() {
        player.saveData();
    }

    public void loadData() {
        player.loadData();
    }

    public int getMaximumNoDamageTicks() {
        return player.getMaximumNoDamageTicks();
    }

    public void setMaximumNoDamageTicks(int ticks) {
        player.setMaximumNoDamageTicks(ticks);
    }

    public int getLastDamage() {
        return player.getLastDamage();
    }

    public void setLastDamage(int damage) {
        player.setLastDamage(damage);
    }

    public int getNoDamageTicks() {
        return player.getNoDamageTicks();
    }

    public void setNoDamageTicks(int ticks) {
        player.setNoDamageTicks(ticks);
    }

    public boolean teleport(Location location) {
        return player.teleport(location);
    }

    public boolean teleport(Entity destination) {
        return player.teleport(destination);
    }

    public List<Entity> getNearbyEntities(double x, double y, double z) {
        return player.getNearbyEntities(x, y, z);
    }

    public boolean isDead() {
        return player.isDead();
    }

    public Entity getPassenger() {
        return player.getPassenger();
    }

    public boolean setPassenger(Entity passenger) {
        return player.setPassenger(passenger);
    }

    public boolean isEmpty() {
        return player.isEmpty();
    }

    public boolean eject() {
        return player.eject();
    }

    public float getFallDistance() {
        return player.getFallDistance();
    }

    public void setFallDistance(float distance) {
        player.setFallDistance(distance);
    }

    public InetSocketAddress getAddress() {
        return player.getAddress();
    }

    public void kickPlayer(String message) {
        player.kickPlayer(message);
    }

    public void chat(String msg) {
        player.chat(msg);
    }

    public boolean performCommand(String command) {
        return player.performCommand(command);
    }

    public boolean isSneaking() {
        return player.isSneaking();
    }

    public void setSneaking(boolean sneak) {
        player.setSneaking(sneak);
    }

    public void updateInventory() {
        player.updateInventory();
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    public boolean isOp() {
        return player.isOp();
    }

    public String getName() {
        return player.getName();
    }

    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    public ItemStack getItemInHand() {
        return player.getItemInHand();
    }

    public void setItemInHand(ItemStack item) {
        player.setItemInHand(item);
    }

    public boolean isSleeping() {
        return player.isSleeping();
    }

    public int getSleepTicks() {
        return player.getSleepTicks();
    }

    public int getHealth() {
        return player.getHealth();
    }

    public void setHealth(int health) {
        player.setHealth(health);
    }

    public double getEyeHeight() {
        return player.getEyeHeight();
    }

    public double getEyeHeight(boolean ignoreSneaking) {
        return player.getEyeHeight(ignoreSneaking);
    }

    public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
        return player.getLineOfSight(transparent, maxDistance);
    }

    public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
        return player.getTargetBlock(transparent, maxDistance);
    }

    public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
        return player.getLastTwoTargetBlocks(transparent, maxDistance);
    }

    public Egg throwEgg() {
        return player.throwEgg();
    }

    public Snowball throwSnowball() {
        return player.throwSnowball();
    }

    public Arrow shootArrow() {
        return player.shootArrow();
    }

    public boolean isInsideVehicle() {
        return player.isInsideVehicle();
    }

    public boolean leaveVehicle() {
        return player.leaveVehicle();
    }

    public Vehicle getVehicle() {
        return player.getVehicle();
    }

    public int getRemainingAir() {
        return player.getRemainingAir();
    }

    public void setRemainingAir(int ticks) {
        player.setRemainingAir(ticks);
    }

    public int getMaximumAir() {
        return player.getMaximumAir();
    }

    public void setMaximumAir(int ticks) {
        player.setMaximumAir(ticks);
    }

    public Location getLocation() {
        return player.getLocation();
    }

    public World getWorld() {
        return player.getWorld();
    }

    public void teleportTo(Location location) {
        player.teleportTo(location);
    }

    public void teleportTo(Entity destination) {
        player.teleportTo(destination);
    }

    public int getEntityId() {
        return player.getEntityId();
    }

    public int getFireTicks() {
        return player.getFireTicks();
    }

    public int getMaxFireTicks() {
        return player.getMaxFireTicks();
    }

    public void setFireTicks(int ticks) {
        player.setFireTicks(ticks);
    }

    public void remove() {
        player.remove();
    }

    public Server getServer() {
        return player.getServer();
    }

    public void damage(int amount) {
        player.damage(amount);
    }

    public void damage(int amount, Entity source) {
        player.damage(amount, source);
    }

    public void setVelocity(Vector velocity) {
        player.setVelocity(velocity);
    }

    public Vector getVelocity() {
        return player.getVelocity();
    }

    public void sendRawMessage(String message) {
        player.sendRawMessage(message);
    }

    public Location getEyeLocation() {
        return player.getEyeLocation();
    }
}
