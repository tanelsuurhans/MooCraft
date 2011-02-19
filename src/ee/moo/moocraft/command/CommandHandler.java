package ee.moo.moocraft.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public class CommandHandler {

    private Map<String, AbstractCommand> commands = new HashMap<String, AbstractCommand>();

    public CommandHandler() {
    }

    public void register(String key, AbstractCommand command) {
        this.commands.put(key, command);
    }

    public boolean dispatch(org.bukkit.command.Command command, CommandSender sender, String[] args) throws CommandException {

        AbstractCommand target = this.commands.get(command.getName());

        if (target == null) {
            return false;
        }

        if (sender instanceof Player && !target.isGameCommand()) {
            return true;
        }

        if (sender instanceof ConsoleCommandSender && !target.isConsoleCommand()) {
            return true;
        }

        if (target.isOperatorCommand(args) && !sender.isOp()) {
            throw new CommandException("You are not authorized to use this command");
        }

        return target.execute(sender, command.getName(), args);
    }

    public void clearCommands() {
        this.commands.clear();
    }

}
