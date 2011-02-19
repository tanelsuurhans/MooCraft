package ee.moo.moocraft.configuration;

import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.io.File;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public abstract class ConfigurablePlugin extends JavaPlugin {

    private ConfigurationManager configManager = new ConfigurationManager(this);

    public ConfigurablePlugin(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
    }

    public ConfigurationManager getConfigManager() {
        return this.configManager;
    }

}
