package ee.moo.moocraft.model;

import ee.moo.moocraft.manager.PersistenceManager;
import ee.moo.moocraft.model.tool.Tool;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Tanel Suurhans
 * Date: 2/27/11
 */
public class LocalPlayer extends BasePlayer {

    private PersistenceManager db;

    private Boolean godMode = false;

    private Map<String, Location> homes = new HashMap<String, Location>();
    private Map<Integer, Tool> tools = new HashMap<Integer, Tool>();

    public LocalPlayer(Player player, PersistenceManager db) {
        this.db = db;
        this.player = player;
    }

    public void setHome() {
        Location location = getLocation();

        db.saveHome(player, location);
        homes.put(location.getWorld().getName(), location);
    }

    public Location getHome() {
        return getHome(player.getWorld());
    }

    public Location getHome(World world) {

        Location home = homes.get(world.getName());

        if (home == null) {
            home = db.findHome(player, world);

            if (home != null)
                homes.put(world.getName(), home);
        }

        if (home == null)
            home = world.getSpawnLocation();

        return home;

    }

    public void removeHome(World world) {

        if (homes.containsKey(world.getName())) {
            homes.remove(world.getName());
            db.removeHome(world);
        }

    }

    public Tool getTool() {
        return tools.get(getItemInHand().getTypeId());
    }

    public Tool getTool(Class clazz) {
        
        Tool tool = tools.get(getItemInHand().getTypeId());

        if (tool == null)
            return null;

        if (tool.getClass().equals(clazz))
            return tool;

        return null;
    }

    public void removeTool() {
        tools.remove(getItemInHand().getTypeId());
    }

    public void setTool(Tool tool) {
        tools.put(getItemInHand().getTypeId(), tool);
    }

    public boolean hasToolEquipped() {
        return !(tools.get(getItemInHand().getTypeId()) == null);
    }

    public boolean isGod() {
        return godMode;
    }

    public void setGod(boolean godMode) {
        this.godMode = godMode;
    }

    public void teleportHome(World world) {
        teleportTo(getHome(world));
    }

    public void teleportHome() {
        teleportTo(getHome());
    }

}
