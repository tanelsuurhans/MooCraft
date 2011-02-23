package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;

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

            if (!playerList.containsKey(player.getEntityId())) {

                HashMap<Property, Object> properties = new HashMap<Property, Object>();
                Location home = plugin.getPersistenceManager().findHome(player);

                if (home != null)
                    properties.put(Property.HOME, home);

                playerList.put(player.getEntityId(), properties);

            }

        }

    }

    public void disable() {
        playerList.clear();
    }

    public void addPlayer(Player entity) {

        if (!playerList.containsKey(entity.getEntityId())) {
            playerList.put(entity.getEntityId(), new HashMap<Property, Object>());
        }

    }

    public void removePlayer(Entity entity) {
        playerList.remove(entity.getEntityId());
    }

    public void setHome(Player player){
        playerList.get(player.getEntityId()).put(Property.HOME, player.getLocation());
        plugin.getPersistenceManager().saveHome(player, player.getLocation());
    }

    public Location getHome(Player player) {

        HashMap properties = playerList.get(player.getEntityId());

        if (!properties.containsKey(Property.GOD)) {
            return player.getWorld().getSpawnLocation();
        }

        return (Location) properties.get(Property.HOME);
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
    
}
