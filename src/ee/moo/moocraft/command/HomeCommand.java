package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/20/11
 */
public class HomeCommand  extends AbstractCommand {

    public HomeCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        Player player = (Player) commandSender;

        if (args.length == 1) {

            plugin.getPlayerManager().setHome(player);
            player.sendMessage(ChatColor.GREEN + "Home location for this world stored.");

        } else {

            player.teleportTo(plugin.getPlayerManager().getHome(player));
            player.sendMessage(ChatColor.GREEN + "Teleported to home.");

        }

        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return false;
    }
}
