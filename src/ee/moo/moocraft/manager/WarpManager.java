package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalWarp;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * User: Tanel Suurhans
 * Date: 2/24/11
 */
public class WarpManager {

    private MooCraft plugin;
    private HashMap<String, LocalWarp> warpList = new HashMap<String, LocalWarp>();

    public WarpManager(MooCraft plugin) {
        this.plugin = plugin;
    }

    public void disable() {
        warpList.clear();
    }

    public void enable() {
        // query all warps
    }

    public void addWarp(String name, Location location) {

        if (!warpList.containsKey(name)) {
            warpList.put(name, new LocalWarp(name, location));
        }

    }

    public void addWarp(String name, Location location, Player player) {

        if (!warpList.containsKey(name)) {
            warpList.put(name, new LocalWarp(name, location, player));
        }

    }

    public void removeWarp(String name) {

        if (warpList.containsKey(name)) {
            warpList.remove(name);
        }

    }

    public LocalWarp getWarp(String name) {
        return warpList.get(name);
    }

}
