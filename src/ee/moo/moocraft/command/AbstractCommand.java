package ee.moo.moocraft.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public abstract class AbstractCommand {

    protected Plugin plugin;

    public AbstractCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public abstract boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException;
    public abstract boolean isGameCommand();
    public abstract boolean isConsoleCommand();

}
