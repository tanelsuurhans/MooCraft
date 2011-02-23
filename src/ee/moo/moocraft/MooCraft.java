package ee.moo.moocraft;

import ee.moo.moocraft.command.AbstractCommand;
import ee.moo.moocraft.command.CommandException;
import ee.moo.moocraft.command.CommandHandler;
import ee.moo.moocraft.manager.ConfigManager;
import ee.moo.moocraft.listener.MooBlockListener;
import ee.moo.moocraft.listener.MooEntityListener;
import ee.moo.moocraft.listener.MooPlayerListener;
import ee.moo.moocraft.manager.PlayerManager;
import ee.moo.moocraft.manager.DatabaseManager;
import ee.moo.moocraft.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.logging.Level;

/**
 * User: Tanel Suurhans
 * Date: 2/16/11
 */
public class MooCraft extends JavaPlugin {

    private final CommandHandler commandHandler = new CommandHandler();

    private ConfigManager configManager;
    private PlayerManager playerManager;
    private DatabaseManager databaseManager;

    public MooCraft() {
        super();
    }

    public void onDisable() {
        commandHandler.clearCommands();
        databaseManager.disconnect();

       log(Level.INFO, String.format("Version %s is disabled.", getDescription().getVersion()));
    }

    public void onEnable() {

        this.configManager = new ConfigManager(this);
        this.playerManager = new PlayerManager(this);
        this.databaseManager = new DatabaseManager(this);

        Listener playerListener = new MooPlayerListener(this);
        Listener blockListener = new MooBlockListener(this);
        Listener entityListener = new MooEntityListener(this);

        databaseManager.connect();
        databaseManager.load();

        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Low, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Low, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_KICK, playerListener, Event.Priority.Low, this);
        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, playerListener, Event.Priority.Lowest, this);

        getServer().getPluginManager().registerEvent(Event.Type.BLOCK_IGNITE, blockListener, Event.Priority.High, this);
        getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BURN, blockListener, Event.Priority.High, this);

        getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Normal, this);
        getServer().getPluginManager().registerEvent(Event.Type.ENTITY_COMBUST, entityListener, Event.Priority.High, this);
        getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Event.Priority.Normal, this);

        /** automatically load commands */
        for (Object obj : ((LinkedHashMap) getDescription().getCommands()).keySet()) {

            String classKey  = (String) obj;
            String className = String.format("ee.moo.moocraft.command.%sCommand", StringUtil.capitalize(classKey));

            try {
                Class<? extends AbstractCommand> commandClass = Class.forName(className, false, getClassLoader()).asSubclass(AbstractCommand.class);
                Constructor<? extends AbstractCommand> constructor = commandClass.getConstructor(MooCraft.class);
                AbstractCommand command = constructor.newInstance(this);

                commandHandler.register(classKey, command);

            } catch (ClassNotFoundException e) {
                log(Level.SEVERE, String.format("Command class not found: %s", e.getMessage()));
            } catch (NoSuchMethodException e) {
                log(Level.SEVERE, String.format("No matching constructor found: %s", e.getMessage()));
            } catch (Exception e) {
                log(Level.SEVERE, String.format("Command class loading failed: %s", e.getMessage()));
            }

        }

        getServer().getLogger().log(Level.INFO, String.format("[MooCraft] Version %s is enabled.", getDescription().getVersion()));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try{
            return commandHandler.dispatch(command, sender, args);
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    public ConfigManager getConfigManager() {
        return this.configManager;
    }

    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }

    public void log(Level level, String output) {
        getServer().getLogger().log(level, String.format("[MooCraft] %s", output));
    }

}
