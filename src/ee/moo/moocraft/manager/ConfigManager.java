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
    }

    /** set defaults */
    public void enable() {

        /** other config */
        defaults.put("fire.spread", false);
        defaults.put("fire.burn", false);

        for (Map.Entry<String, Object> obj : defaults.entrySet()) {

            if (this.isNull(obj.getKey())) {
                plugin.getConfiguration().setProperty(obj.getKey(), obj.getValue());
            }

        }

        /** maintenance save basically */
        plugin.getConfiguration().save();
    }

    public void disable() {
        defaults.clear();
    }

    public boolean isNull(String key) {
        return plugin.getConfiguration().getProperty(key) == null;
    }

    public boolean isEnabled(String key) {
        return plugin.getConfiguration().getBoolean(key, true);
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
