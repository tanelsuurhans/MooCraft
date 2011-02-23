package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.database.Database;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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

    public Location findHome(Player player) {

        Location location = null;

        try {

            PreparedStatement query = database.prepare("SELECT * FROM homes where player_name = ?");

            query.setString(1, player.getDisplayName());

            ResultSet result = query.executeQuery();

            if (result.next()) {

                World world = player.getServer().getWorld(result.getString("world"));

                if (world != null) {
                    location = new Location(world, result.getDouble("x"), result.getDouble("y"), result.getDouble("z"));
                }

            }

            result.close();
            query.close();

        } catch (SQLException e) {
            //
        }

        return location;
    }

    public void saveHome(Player player, Location location) {

        try {

            PreparedStatement query = database.prepare("INSERT OR REPLACE INTO homes(player_name, world, x, y, z) values(?, ?, ?, ?, ?)");

            query.setString(1, player.getDisplayName());
            query.setString(2, player.getWorld().getName());
            query.setDouble(3, location.getX());
            query.setDouble(4, location.getY());
            query.setDouble(5, location.getZ());

            query.executeUpdate();
            query.close();

        } catch(SQLException e) {
            plugin.log(Level.WARNING, String.format("Cannot save home for user %s: " + e.getMessage(), player.getDisplayName()));
        }

    }

    public void saveWorld(World world) {

        try {

            PreparedStatement query = database.prepare("INSERT OR REPLACE INTO worlds(name, type) values(?, ?);");

            query.setString(1, world.getName());
            query.setString(2, world.getEnvironment().toString());

            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, String.format("Cannot save world %s:" + e.getMessage(), world.getName()));
        }

    }

    public void removeWorld(World world) {

        try {

            PreparedStatement query = database.prepare("DELETE FROM worlds WHERE name = ?;");

            query.setString(1, world.getName());
            query.execute();
            query.close();

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
            query.close();

        } catch (SQLException e) {
            plugin.log(Level.WARNING, "Could not query all worlds");
        }

        return worlds;
    }

    public void saveWarp() {

    }

    public void removeWarp() {

    }

}
