package ee.moo.moocraft.command;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * User: Tanel Suurhans
 * Date: 2/18/11
 */
public class ConfigCommand extends AbstractCommand {

    public ConfigCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (!commandSender.isOp()) {
            throw new CommandException("You do not have permissions to use this command.");
        }

        if (args.length > 3) {
            return false;
        }

        // load
        // save
        // get key
        // set key value
        // add key value

        return true;
    }

    @Override
    public boolean isGameCommand() {
        return true;
    }

    @Override
    public boolean isConsoleCommand() {
        return true;
    }
}
