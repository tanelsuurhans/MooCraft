package ee.moo.moocraft;

import ee.moo.moocraft.command.AbstractCommand;
import ee.moo.moocraft.command.CommandHandler;
import ee.moo.moocraft.listeners.PlayerJoinListener;
import ee.moo.moocraft.util.StringUtil;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;

/**
 * User: Tanel Suurhans
 * Date: 2/16/11
 */
public class MooCraft extends JavaPlugin {

    private CommandHandler commandHandler = new CommandHandler();

    public MooCraft(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
    }

    public void onDisable() {
        this.commandHandler.clearCommands();
    }

    public void onEnable() {

        this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, new PlayerJoinListener(this), Event.Priority.Normal, this);

        for (Object obj : ((LinkedHashMap) this.getDescription().getCommands()).keySet()) {

            String classKey  = (String) obj;
            String className = String.format("ee.moo.moocraft.command.%sCommand", StringUtil.capitalize(classKey));

            try {
                Class<? extends AbstractCommand> commandClass = Class.forName(className, false, this.getClassLoader()).asSubclass(AbstractCommand.class);
                Constructor<? extends AbstractCommand> constructor = commandClass.getConstructor(Plugin.class);
                AbstractCommand command = constructor.newInstance(this);

                this.commandHandler.register(classKey, command);

            } catch (ClassNotFoundException e) {
                System.out.println(String.format("Command class not found: %s", e.getMessage()));
            } catch (NoSuchMethodException e) {
                System.out.println(String.format("No matching constructor found: %s", e.getMessage()));
            } catch (Exception e) {
                System.out.println(String.format("Command class loading failed: %s", e.getMessage()));
            }

        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return this.commandHandler.dispatch(command, sender, args);
    }
}
