package ee.moo.moocraft.command;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public class LocationCommand extends AbstractCommand {

    public LocationCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length > 1) {
            return false;
        }

        if (args.length == 1) {

            Player target = this.plugin.getServer().getPlayer(args[0]);

            if (target == null) {
                throw new CommandException(String.format("Player named %s does not exist", args[0]));
            }

            Location location = target.getLocation();

            Integer x = location.getBlockX();
            Integer y = location.getBlockY();
            Integer z = location.getBlockZ();

            commandSender.sendMessage(ChatColor.GREEN + String.format("Your location is: %s:%s:%s", x, y, z));

        } else {

            if (commandSender instanceof ConsoleCommandSender) {
                throw new CommandException("You cannot check your location from the console.");
            }

            Location location = ((Player) commandSender).getLocation();

            Integer x = location.getBlockX();
            Integer y = location.getBlockY();
            Integer z = location.getBlockZ();

            commandSender.sendMessage(ChatColor.GREEN + String.format("Your location is: %s:%s:%s", x, y, z));
        }

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
