package ee.moo.moocraft.configuration;

import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class ConfigurationManager {

    private Plugin plugin;
    private HashMap<String, Object> defaults = new HashMap<String, Object>();

    public ConfigurationManager(Plugin plugin) {
        this.plugin = plugin;
        this.initialize();
    }

    /** set defaults */
    private void initialize() {

        this.defaults.put("fire.spread", false);
        this.defaults.put("fire.burn", false);

        Iterator<Map.Entry<String, Object>> iterator = defaults.entrySet().iterator();

        while (iterator.hasNext()) {

            Map.Entry<String, Object> entry = iterator.next();

            if (this.isNull(entry.getKey())) {
                this.plugin.getConfiguration().setProperty(entry.getKey(), entry.getValue());
            }

        }

        /** maintenance save basically */
        this.plugin.getConfiguration().save();
    }

    public boolean isNull(String key) {
        return this.plugin.getConfiguration().getProperty(key) == null;
    }

    public boolean isEnabled(String key) {
        return this.plugin.getConfiguration().getBoolean(key, true);
    }

    public boolean isDisabled(String key) {
        return this.plugin.getConfiguration().getBoolean(key, false);
    }

    public String booleanString(String key) {
        return this.isEnabled(key) ? "enabled" : "disabled";
    }

    public void reload() {
        this.plugin.getConfiguration().load();
    }

    public boolean save() {
        return this.plugin.getConfiguration().save();
    }

    public Object get(String key) {
        return this.plugin.getConfiguration().getProperty(key);
    }

    public void set(String key, Object value) {
        try {
            this.plugin.getConfiguration().setProperty(key, value);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
