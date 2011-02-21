package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class CompassCommand extends AbstractCommand {

    public CompassCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        Integer bearing = (int) Math.abs((((Player) commandSender).getLocation().getYaw() - 90) % 360);
        String direction = "";

        if (bearing < 23) {
            direction = "North";
        } else if (bearing < 68) {
            direction = "NorthEast";
        } else if (bearing < 113) {
            direction = "East";
        } else if (bearing < 158) {
            direction = "SouthEast";
        } else if (bearing < 203) {
            direction = "South";
        } else if (bearing < 248) {
            direction = "SouthWest";
        } else if (bearing < 293) {
            direction = "West";
        } else if (bearing < 338) {
            direction = "North";
        }

        commandSender.sendMessage(ChatColor.GREEN + String.format("Your bearing is %s (%s degrees)", ChatColor.WHITE + direction, ChatColor.WHITE + bearing.toString()));

        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return false;
    }
}
