package ee.moo.moocraft.manager;

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
public class DatabaseManager {

    private MooCraft plugin;
    private Connection connection;
    private Map<String, PreparedStatement> sqlCache = new HashMap<String, PreparedStatement>();

    private boolean isLoaded = false;

    public DatabaseManager(MooCraft plugin) {
        this.plugin = plugin;
    }

    public boolean connect() {

        if (connection != null) {
            return true;
        }

        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(getConnectionString());

            plugin.log(Level.INFO, "[MooCraft] Connected to " + getConnectionString());

            return true;
        } catch (ClassNotFoundException e) {
            plugin.log(Level.SEVERE, "[MooCraft] PSQL JDBC Driver cannot be loaded: " + e.getMessage());
        } catch (SQLException e) {
            plugin.log(Level.SEVERE, "[MooCraft] Cannot connect to PostgreSQL database: " + e.getMessage());
        }

        return false;
    }

    public void disconnect() {

        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch(SQLException e) {
            plugin.log(Level.SEVERE, "[MooCraft] Cannot close connection to PostgreSQL database.");
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
            ResultSet result;

            this.connection.setAutoCommit(false);

            result = sqlStatement.executeQuery("SELECT count(*) FROM information_schema.tables WHERE table_name = 'players'");
            result.next();

            if (result.getInt(1) == 0) {

                sqlStatement.execute("CREATE TABLE players (" +
                    "id INTEGER PRIMARY KEY," +
                    "name CHARACTER VARYING(255)," +
                    "seen_at TIMESTAMP WITHOUT TIME ZONE," +
                    "created_at TIMESTAMP WITHOUT TIME ZONE" +
                    ");");

                sqlStatement.execute("CREATE INDEX players_name_index ON players(name);");

            }

            result = sqlStatement.executeQuery("SELECT count(*) FROM information_schema.tables WHERE table_name = 'homes'");
            result.next();

            if (result.getInt(1) == 0) {

                sqlStatement.execute("CREATE TABLE homes (" +
                    "id INTEGER PRIMARY KEY," +
                    "player_id INTEGER NOT NULL," +
                    "world CHARACTER VARYING(255) NOT NULL," +
                    "x DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                    "y DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                    "z DOUBLE PRECISION NOT NULL DEFAULT 0.0" +
                    ")");

                sqlStatement.execute("CREATE UNIQUE INDEX homes_player_world_unique_index ON homes(player_id, world);");

            }

            result = sqlStatement.executeQuery("SELECT count(*) FROM information_schema.tables WHERE table_name = 'warps'");
            result.next();

            if (result.getInt(1) == 0) {

                sqlStatement.execute("CREATE TABLE warps (" +
                    "id INTEGER PRIMARY KEY," +
                    "player_id INTEGER NOT NULL," +
                    "name CHARACTER VARYING(255) NOT NULL," +
                    "world CHARACTER VARYING(255) NOT NULL," +
                    "x DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                    "y DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                    "z DOUBLE PRECISION NOT NULL DEFAULT 0.0," +
                    "private BOOLEAN NOT NULL DEFAULT FALSE" +
                    ")");

                sqlStatement.execute("CREATE UNIQUE INDEX warps_name_world_unique_index ON warps(name, world);");
                sqlStatement.execute("CREATE INDEX warps_name_world_player_index ON warps(player_id, name, world);");

            }

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

    public String getConnectionString() {

        String user = plugin.getConfiguration().getString("database.username");
        String pass = plugin.getConfiguration().getString("database.password");
        String host = plugin.getConfiguration().getString("database.host");
        String port = plugin.getConfiguration().getString("database.port");
        String schema = plugin.getConfiguration().getString("database.database");

        StringBuilder connection = new StringBuilder("jdbc:postgresql://");

        if (!"".equals(user) && !"".equals(pass))
            connection.append(user).append(":").append(pass).append("@");

        connection.append(host);

        if (!"".equals(port))
            connection.append(":").append(port);

        connection.append("/").append(schema);

        return connection.toString();
    }

}
