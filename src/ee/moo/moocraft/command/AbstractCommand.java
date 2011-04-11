package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.command.CommandSender;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public abstract class AbstractCommand {

    protected MooCraft plugin;

    public AbstractCommand(MooCraft plugin) {
        this.plugin = plugin;
    }

    public abstract boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException;

    public boolean isOperatorCommand(String[] args) {
        return false;
    }

    public boolean isGameCommand(String[] args) {
        return true;
    }

    public boolean isConsoleCommand(String[] args) {
        return true;
    }

}
