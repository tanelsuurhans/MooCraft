package ee.moo.moocraft.manager;

import ee.moo.moocraft.MooCraft;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class ConfigManager {

    private MooCraft plugin;
    private HashMap<String, Object> defaults = new HashMap<String, Object>();

    public ConfigManager(MooCraft plugin) {
        this.plugin = plugin;
        this.initialize();
    }

    /** set defaults */
    private void initialize() {

        /** database config */
        defaults.put("database.host", "localhost");
        defaults.put("database.port", "");
        defaults.put("database.username", "");
        defaults.put("database.password", "");
        defaults.put("database.database", "minecraft");

        /** other config */
        defaults.put("fire.spread", false);
        defaults.put("fire.burn", false);

        Iterator<Map.Entry<String, Object>> iterator = defaults.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<String, Object> entry = iterator.next();

            if (this.isNull(entry.getKey())) {
                plugin.getConfiguration().setProperty(entry.getKey(), entry.getValue());
            }

        }

        /** maintenance save basically */
        plugin.getConfiguration().save();
    }

    public boolean isNull(String key) {
        return plugin.getConfiguration().getProperty(key) == null;
    }

    public boolean isEnabled(String key) {
        return plugin.getConfiguration().getBoolean(key, true);
    }

    public boolean isDisabled(String key) {
        return plugin.getConfiguration().getBoolean(key, false);
    }

    public String booleanString(String key) {
        return isEnabled(key) ? "enabled" : "disabled";
    }

    public void reload() {
        plugin.getConfiguration().load();
    }

    public boolean save() {
        return plugin.getConfiguration().save();
    }

    public Object get(String key) {
        return plugin.getConfiguration().getProperty(key);
    }

    public void set(String key, Object value) {

        try {
            plugin.getConfiguration().setProperty(key, value);
        } catch(Exception e) {
            plugin.getServer().getLogger().log(Level.WARNING, e.getMessage());
        }

    }

}
