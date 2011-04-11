package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalWarp;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Tanel Suurhans
 * Date: 2/24/11
 */
public class WarpManager {

    private MooCraft plugin;
    private ConcurrentHashMap<String, List<LocalWarp>> warpList = new ConcurrentHashMap<String, List<LocalWarp>>();

    public WarpManager(MooCraft plugin) {
        this.plugin = plugin;
    }

    public void disable() {
        warpList.clear();
    }

    public void enable() {

        for (World world : plugin.getServer().getWorlds()) {

            List<LocalWarp> warps = new ArrayList<LocalWarp>();
            
            for (Map<String, Object> properties : plugin.getPersistenceManager().findWarps(world)) {

                String name = (String) properties.get("name");

                Double x = (Double) properties.get("x");
                Double y = (Double) properties.get("y");
                Double z = (Double) properties.get("z");

                Location location = new Location(world, x, y, z);

                warps.add(new LocalWarp(name, location));
            }

            warpList.put(world.getName(), warps);
        }

    }

    public LocalWarp addWarp(String name, Location location) {

        LocalWarp warp;

        if ((warp = getWarp(name, location.getWorld())) != null) {
            return warp;
        }

        warp = new LocalWarp(name, location);

        warpList.get(location.getWorld().getName()).add(warp);
        plugin.getPersistenceManager().saveWarp(warp);

        return warp;
    }

    public LocalWarp addWarp(String name, Location location,Player player) {

        LocalWarp warp;

        if ((warp = getWarp(name, location.getWorld())) != null) {
            return warp;
        }

        warp = new LocalWarp(name, location, player);

        warpList.get(location.getWorld().getName()).add(warp);
        plugin.getPersistenceManager().saveWarp(warp, player);

        return warp;
    }

    public void removeWarp(LocalWarp warp) {
        if (warp != null) {
            plugin.getPersistenceManager().removeWarp(warp);
            warpList.get(warp.getWorldName()).remove(warp);
        }

    }

    public LocalWarp getWarp(String name, World world) {

        if (!warpList.containsKey(world.getName())) {
            return null;
        }

        for (LocalWarp entry : warpList.get(world.getName())) {

            if (entry.getName().equals(name))
                return entry;

        }

        return null;
    }

    public List<LocalWarp> getWarps(World world) {
        return warpList.get(world.getName());
    }

    public void addWorld(World world) {
        if (!warpList.containsKey(world.getName())) {
            warpList.put(world.getName(), new ArrayList<LocalWarp>());
        }
    }

    public void removeWorld(World world) {
        if (warpList.containsKey(world.getName())) {
            warpList.remove(world.getName());
        }
    }

}
