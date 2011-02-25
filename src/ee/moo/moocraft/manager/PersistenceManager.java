package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.database.Database;
import ee.moo.moocraft.model.LocalWarp;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

/**
 * User: Tanel Suurhans
 * Date: 2/23/11
 */
public class PersistenceManager {

    private MooCraft plugin;
    private Database database;

    public PersistenceManager(MooCraft plugin) {
        this.plugin = plugin;
        this.database = new Database(plugin);
    }

    public void enable() {
        database.connect();
        database.load();
    }

    public void disable() {
        database.disconnect();
    }

    public Location findHome(Player player, World world) {

        Location location = null;

        try {

            PreparedStatement query = database.prepare("SELECT * FROM homes where player_name = ? AND world = ?");

            query.setString(1, player.getDisplayName());
            query.setString(2, world.getName());

            ResultSet result = query.executeQuery();

            if (result.next()) {
                location = new Location(world, result.getDouble("x"), result.getDouble("y"), result.getDouble("z"));
            }

            result.close();

        } catch (SQLException e) {
            //
        }

        return location;
    }

    public void saveHome(Player player, Location location) {

        try {

            PreparedStatement query = database.prepare("INSERT OR REPLACE INTO homes(player_name, world, x, y, z) values(?, ?, ?, ?, ?);");

            query.setString(1, player.getDisplayName());
            query.setString(2, player.getWorld().getName());
            query.setDouble(3, location.getX());
            query.setDouble(4, location.getY());
            query.setDouble(5, location.getZ());

            query.executeUpdate();

        } catch(SQLException e) {
            plugin.log(Level.WARNING, String.format("Cannot save home for user %s: " + e.getMessage(), player.getDisplayName()));
        }

    }

    public void removeHome(Location location) {

        try {

            PreparedStatement query = database.prepare("DELETE FROM homes WHERE world = ?;");

            query.setString(1, location.getWorld().getName());
            query.executeUpdate();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, "Cannot delete home location: " + e.getMessage());
        }

    }

    public void saveWorld(World world) {

        try {

            PreparedStatement query = database.prepare("INSERT OR REPLACE INTO worlds(name, type) values(?, ?);");

            query.setString(1, world.getName());
            query.setString(2, world.getEnvironment().toString());

            query.executeUpdate();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, String.format("Cannot save world %s:" + e.getMessage(), world.getName()));
        }

    }

    public void removeWorld(World world) {

        try {

            PreparedStatement query = database.prepare("DELETE FROM worlds WHERE name = ?;");

            query.setString(1, world.getName());
            query.executeUpdate();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, String.format("Could not delete world %s", world.getName()));
        }

    }

    public HashMap<String, World.Environment> findWorlds() {

        HashMap<String, World.Environment> worlds = new HashMap<String, World.Environment>();

        try {

            PreparedStatement query = database.prepare("SELECT * FROM worlds");
            ResultSet result = query.executeQuery();

            while(result.next()) {
                worlds.put(result.getString("name"), World.Environment.valueOf(result.getString("type")));
            }

            result.close();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, "Could not query all worlds");
        }

        return worlds;
    }

    public void saveWarp(LocalWarp warp) {

        try {

            PreparedStatement query = database.prepare("INSERT OR REPLACE INTO warps(name, world, x, y, z) values(?, ?, ?, ?, ?);");

            query.setString(1, warp.getName());
            query.setString(2, warp.getWorldName());
            query.setDouble(3, warp.getLocation().getX());
            query.setDouble(4, warp.getLocation().getY());
            query.setDouble(5, warp.getLocation().getZ());

            query.executeUpdate();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, String.format("Cannot save warp %s: " + e.getMessage(), warp.getName()));
        }

    }

    public void saveWarp(LocalWarp warp, Player player) {

        try {

            PreparedStatement query = database.prepare("INSERT OR REPLACE INTO warps(name, world, x, y, z, player_name) values(?, ?, ?, ?, ?, ?);");

            query.setString(1, warp.getName());
            query.setString(2, warp.getWorldName());
            query.setDouble(3, warp.getLocation().getX());
            query.setDouble(4, warp.getLocation().getY());
            query.setDouble(5, warp.getLocation().getZ());
            query.setString(6, player.getDisplayName());

            query.executeUpdate();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, String.format("Cannot save warp %s: " + e.getMessage(), warp.getName()));
        }

    }

    public void removeWarp(LocalWarp warp) {

        try {

            PreparedStatement query = database.prepare("DELETE FROM warps WHERE name = ? AND world = ?");

            query.setString(1, warp.getName());
            query.setString(2, warp.getWorldName());

            query.executeUpdate();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, String.format("Cannot delete warp %s: " + e.getMessage(), warp.getName()));
        }

    }

    public List<Map<String, Object>> findWarps(World world) {

        List<Map<String, Object>> warps = new ArrayList<Map<String, Object>>();

        try {

            PreparedStatement query = database.prepare("SELECT * FROM warps WHERE world = ?");

            query.setString(1, world.getName());

            ResultSet result = query.executeQuery();

            while (result.next()) {

                Map<String, Object> properties = new HashMap<String, Object>();

                properties.put("name", result.getString("name"));
                properties.put("x", result.getDouble("x"));
                properties.put("y", result.getDouble("y"));
                properties.put("z", result.getDouble("z"));

                warps.add(properties);                
            }

            result.close();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, String.format("Cannot find warps for world %s: " + e.getMessage(), world.getName()));
        }

        return warps;
    }

}
