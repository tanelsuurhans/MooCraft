package ee.moo.moocraft.database;

import ee.moo.moocraft.MooCraft;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: Tanel Suurhans
 * Date: 2/20/11
 */
public class Database {

    private MooCraft plugin;
    private java.sql.Connection connection;
    private Map<String, PreparedStatement> sqlCache = new HashMap<String, PreparedStatement>();

    private boolean isLoaded = false;

    public Database(MooCraft plugin) {
        this.plugin = plugin;
    }

    public boolean connect() {

        if (connection != null) {
            return true;
        }

        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + getDatabase());

            return true;
        } catch (ClassNotFoundException e) {
            plugin.log(Level.SEVERE, "[MooCraft] SQLite JDBC Driver cannot be loaded: " + e.getMessage());
        } catch (SQLException e) {
            plugin.log(Level.SEVERE, "[MooCraft] Cannot connect to SQLite database: " + e.getMessage());
        }

        return false;
    }

    public void disconnect() {

        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch(Exception e) {
            // ignore
        }

    }

    public void load() {

        if (isLoaded) {
            return;
        }

        if (connection == null) {
            return;
        }

        try {

            Statement sqlStatement = this.connection.createStatement();

            this.connection.setAutoCommit(false);

            sqlStatement.execute("CREATE TABLE IF NOT EXISTS worlds (" +
                    "name TEXT NOT NULL," +
                    "type TEXT NOT NULL" +
                    ");");

            sqlStatement.execute("CREATE UNIQUE INDEX IF NOT EXISTS worlds_name_type_unique_index ON worlds(name, type);");

            sqlStatement.execute("CREATE TABLE IF NOT EXISTS homes (" +
                "id INTEGER PRIMARY KEY," +
                "player_name TEXT NOT NULL," +
                "world TEXT NOT NULL," +
                "x DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                "y DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                "z DOUBLE PRECISION NOT NULL DEFAULT 0.0" +
                ")");

            sqlStatement.execute("CREATE UNIQUE INDEX IF NOT EXISTS homes_player_world_unique_index ON homes(player_name, world);");

            sqlStatement.execute("CREATE TABLE IF NOT EXISTS warps (" +
                "id INTEGER PRIMARY KEY," +
                "player_name TEXT NOT NULL," +
                "name TEXT NOT NULL," +
                "world TEXT NOT NULL," +
                "x DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                "y DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                "z DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                "private BOOLEAN NOT NULL DEFAULT FALSE" +
                ")");

            sqlStatement.execute("CREATE UNIQUE INDEX IF NOT EXISTS warps_name_world_unique_index ON warps(name, world);");
            sqlStatement.execute("CREATE INDEX IF NOT EXISTS warps_name_world_player_index ON warps(player_name, name, world);");

            sqlStatement.execute("CREATE TABLE IF NOT EXISTS worlds (" +
                    "id INTEGER PRIMARY KEY" +
                    ")");

            connection.commit();
            connection.setAutoCommit(true);

            sqlStatement.close();

            this.isLoaded = true;

        } catch (SQLException e) {
            plugin.log(Level.SEVERE, "Could not load initial schema:" + e.getMessage());
        }

    }

    public PreparedStatement prepare(String query) {

        if (connection == null) {
            return null;
        }

        if (sqlCache.containsKey(query)) {
            return sqlCache.get(query);
        }

        try {
            
            PreparedStatement statement = connection.prepareStatement(query);

            sqlCache.put(query, statement);

            return statement;

        } catch (SQLException e) {
            plugin.log(Level.WARNING, "Failed building prepared statement: " + e.getMessage());
        }

        return null;
    }

    public String getDatabase() {
        return String.format("%s/moocraft.db", plugin.getDataFolder());
    }

    public java.sql.Connection getConnection() {
        return this.connection;
    }

}
