package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class DepthCommand extends AbstractCommand {

    public DepthCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        Integer depth = ((Player) commandSender).getLocation().getBlockY() - 63;

        if (depth < 0) {
            commandSender.sendMessage(ChatColor.GREEN + String.format("You are %s blocks below seal level.", ChatColor.WHITE + depth.toString() + ChatColor.GREEN));
        } else if (depth > 0) {
            commandSender.sendMessage(ChatColor.GREEN + String.format("You are %s blocks above seal level.", ChatColor.WHITE + depth.toString() + ChatColor.GREEN));
        } else {
            commandSender.sendMessage(ChatColor.GREEN + "You are at sea level");
        }

        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return false;
    }
}
