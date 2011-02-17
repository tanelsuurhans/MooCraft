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
public class TeleportCommand extends AbstractCommand {

    public TeleportCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (!commandSender.isOp()) {
            throw new CommandException("You do not have permissions to use this command.");
        }

        if (args.length == 0 || args.length > 2) {
            return false;
        }

        // handle self teleporting
        if (args.length == 1) {

            if (commandSender instanceof ConsoleCommandSender) {
                throw new CommandException("You cannot use teleport from the console to teleport yourself.");
            }

            if (args[0].contains(":")) {

                this.doTeleport((Player) commandSender, args[0].split(":"), commandSender);

            } else {

                Player target = this.plugin.getServer().getPlayer(args[0]);

                if (target == null) {
                    throw new CommandException(String.format("Player %s does not exist.", args[0]));
                }

                this.doTeleport((Player) commandSender, target, commandSender);

            }

        // handle player teleporting
        } else if (args.length == 2) {

            Player source = this.plugin.getServer().getPlayer(args[0]);

            if (source == null) {
                throw new CommandException(String.format("Player %s does not exist.", args[0]));
            }


            if (args[1].contains(":")) {

                this.doTeleport(source, args[1].split(":"), commandSender);

            } else {

                Player target = this.plugin.getServer().getPlayer(args[1]);                

                if (target == null) {
                    throw new CommandException(String.format("Player %s does not exist.", args[1]));
                }

                this.doTeleport(source, target, commandSender);

            }

        }

        return true;
    }

    private void doTeleport(Player source, Player target, CommandSender sender) {

        // teleport player
        source.teleportTo(target);

        // report
        sender.sendMessage(ChatColor.GREEN + String.format("Teleported %s to player %s.", source.getDisplayName(), target.getDisplayName()));
    }

    private void doTeleport(Player source, String[] coordinates, CommandSender sender) throws CommandException {

        String coordinateString = String.format("%s:%s:%s", coordinates[0], coordinates[1], coordinates[2]);

        if (coordinates.length != 3) {
            throw new CommandException(String.format("Coordinates (%s) are malformed.", coordinateString));
        }

        Integer x = null;
        Integer y = null;
        Integer z = null;

        try {

            x = Integer.valueOf(coordinates[0]);
            y = Integer.valueOf(coordinates[1]);
            z = Integer.valueOf(coordinates[2]);

        } catch(NumberFormatException e) {
            throw new CommandException(String.format("Coordinates (%s) are malformed.", coordinateString));
        }

        // teleport player
        source.teleportTo(new Location(source.getWorld(), x ,y, z));

        // report
        sender.sendMessage(ChatColor.GREEN + String.format("Teleported %s to location %s", source.getDisplayName(), coordinateString));
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
