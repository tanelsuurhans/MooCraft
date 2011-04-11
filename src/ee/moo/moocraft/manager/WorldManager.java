package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalWorld;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * User: Tanel Suurhans
 * Date: 2/23/11
 */
public class WorldManager {

    private MooCraft plugin;
    private HashMap<String, LocalWorld> worldList = new HashMap<String, LocalWorld>();

    public WorldManager(MooCraft plugin) {
        this.plugin = plugin;
    }

    public void enable() {

        for (Map.Entry<String, World.Environment> entry : plugin.getPersistenceManager().findWorlds().entrySet()) {

            if (!worldList.containsKey(entry.getKey())) {

                World world = plugin.getServer().createWorld(entry.getKey(), entry.getValue());

                worldList.put(entry.getKey(), new LocalWorld(world.getName(), world.getEnvironment()));

                plugin.log(Level.INFO, String.format("Loaded world %s of type %s", entry.getKey(), entry.getValue()));

            }

        }

    }

    public void disable() {
        worldList.clear();
    }

    public World addWorld(String name, World.Environment type) {

        if (worldList.containsKey(name)) {
            return plugin.getServer().getWorld(name);
        }

        World world = plugin.getServer().createWorld(name, type);

        worldList.put(name, new LocalWorld(world.getName(), world.getEnvironment()));
        plugin.getPersistenceManager().saveWorld(world);
        plugin.getWarpManager().addWorld(world);

        return world;
    }

    public void removeWorld(World world) {

        /** TODO: delete world? */
        if (worldList.containsKey(world.getName())) {

            plugin.getPersistenceManager().removeWorld(world);
            plugin.getPlayerManager().removeWorld(world);
            plugin.getWarpManager().removeWorld(world);
            worldList.remove(world.getName());

        }

    }

    public World getWorld(String name) {
        return plugin.getServer().getWorld(name);
    }

    public List<Player> getPlayers(World world) {

        List<Player> players = new ArrayList<Player>();

        for (LivingEntity entity : world.getLivingEntities()) {

            if (entity instanceof Player)
                players.add((Player) entity);

        }

        return players;
    }

}
