package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public class TeleportCommand extends AbstractCommand {

    public TeleportCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length == 0 || args.length > 2) {
            return false;
        }

        // handle self teleporting
        if (args.length == 1) {

            if (commandSender instanceof ConsoleCommandSender) {
                throw new CommandException("You cannot use teleport from the console to teleport yourself.");
            }

            Player target = (Player) commandSender;

            if (args[0].contains(":")) {

                this.teleportPlayer((Player) commandSender, args[0].split(":"), commandSender);

            } else if (args[0].equals("ground")) {

                Integer x = ((Player) commandSender).getLocation().getBlockX();
                Integer z = ((Player) commandSender).getLocation().getBlockZ();
                Integer y = ((Player) commandSender).getWorld().getHighestBlockYAt(x, z) + 1;

                this.teleportPlayer(target, new Location(target.getWorld(), x, y, z), commandSender);

            } else {

                target = plugin.getServer().getPlayer(args[0]);

                if (target == null) {
                    throw new CommandException(String.format("Player %s does not exist.", args[0]));
                }

                this.teleportPlayer((Player) commandSender, target, commandSender);

            }

        // handle player teleporting
        } else if (args.length == 2) {

            Player source = plugin.getServer().getPlayer(args[0]);

            if (source == null) {
                throw new CommandException(String.format("Player %s does not exist.", args[0]));
            }

            if (args[1].contains(":")) {

                this.teleportPlayer(source, args[1].split(":"), commandSender);

            } else {

                Player target = plugin.getServer().getPlayer(args[1]);

                if (target == null) {
                    throw new CommandException(String.format("Player %s does not exist.", args[1]));
                }

                this.teleportPlayer(source, target, commandSender);

            }

        }

        return true;
    }

    private void teleportPlayer(Player source, Player target, CommandSender sender) {

        source.teleport(target);
        sender.sendMessage(ChatColor.GREEN + String.format("Teleported %s to player %s.", source.getDisplayName(), target.getDisplayName()));

    }

    private void teleportPlayer(Player target, String[] coordinates, CommandSender sender) throws CommandException {

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

        this.teleportPlayer(target, new Location(target.getWorld(), x, y, z), sender);
    }

    private void teleportPlayer(Player target, Location location, CommandSender sender) {

        String coordinates = String.format("%s:%s:%s", location.getX(), location.getY(), location.getZ());

        target.teleport(location);
        sender.sendMessage(ChatColor.GREEN + String.format("Teleported %s to location %s", target.getDisplayName(), coordinates));

    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }

}
