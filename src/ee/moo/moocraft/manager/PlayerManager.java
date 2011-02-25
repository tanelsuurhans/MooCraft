package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class PlayerManager {

    private enum Property {
        GOD, HOME
    }

    private MooCraft plugin;
    private static HashMap<Integer, HashMap<Property, Object>> playerList = new HashMap<Integer, HashMap<Property, Object>>();

    public PlayerManager(MooCraft plugin) {
        this.plugin = plugin;
    }

    public void enable() {

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            this.addPlayer(player);
        }

    }

    public void disable() {
        playerList.clear();
    }

    public void addPlayer(Player entity) {

        if (!playerList.containsKey(entity.getEntityId())) {

            HashMap<Property, Object> properties = new HashMap<Property, Object>();
            HashMap<String, Location> homes = new HashMap<String, Location>();

            for (World world : plugin.getServer().getWorlds()) {

                Location home = plugin.getPersistenceManager().findHome(entity, world);

                if (home != null)
                    homes.put(world.getName(), home);

            }

            properties.put(Property.HOME, homes);
            playerList.put(entity.getEntityId(), properties);
        }

    }

    public void removePlayer(Entity entity) {
        playerList.remove(entity.getEntityId());
    }

    public void setHome(Player player){

        Map<Property, Object> props = playerList.get(player.getEntityId());
        Map<String, Location> homes = (Map) props.get(Property.HOME);

        homes.put(player.getWorld().getName(), player.getLocation());
        plugin.getPersistenceManager().saveHome(player, player.getLocation());

    }

    public Location getHome(Player player) {
        return getHome(player, player.getWorld());
    }

    public Location getHome(Player player, World world) {

        Map<Property, Object> properties = playerList.get(player.getEntityId());

        if (!properties.containsKey(Property.HOME)) {
            return world.getSpawnLocation();
        }

        Map<String, Location> homes = (Map) properties.get(Property.HOME);

        if (!homes.containsKey(world.getName())) {
            return world.getSpawnLocation();
        }

        return homes.get(world.getName());
    }

    public boolean hasGodMode(Player player) {

        HashMap properties = playerList.get(player.getEntityId());

        if (!properties.containsKey(Property.GOD)) {
            return false;
        }

        return (Boolean) properties.get(Property.GOD);
    }

    public void setGodMode(Player player, boolean mode) {
        playerList.get(player.getEntityId()).put(Property.GOD, mode);
    }

    public void removeWorld(World world) {
        
        for (Map props : playerList.values()) {

            Map<String, Location> homes = (Map) props.get(Property.HOME);

            if (homes.containsKey(world.getName())) {
                Location home = homes.remove(world.getName());
                plugin.getPersistenceManager().removeHome(home);
            }

        }

    }
    
}
