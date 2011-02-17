package ee.moo.moocraft;

import ee.moo.moocraft.listeners.PlayerJoinListener;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.LinkedHashMap;

/**
 * User: Tanel Suurhans
 * Date: 2/16/11
 */
public class MooCraft extends JavaPlugin {

    private CommandHandler commandHandler;

    public MooCraft(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);

        this.commandHandler = new CommandHandler(this);
    }

    public void onDisable() {
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, new PlayerJoinListener(this.getServer()), Event.Priority.Normal, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            return this.commandHandler.handle(command, args, (Player) sender);
        }

        return false;
    }
}
