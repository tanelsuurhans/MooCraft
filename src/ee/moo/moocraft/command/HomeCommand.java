package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalPlayer;
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

        LocalPlayer player = plugin.getPlayerManager().getPlayer((Player) commandSender);

        if (args.length == 1) {

            player.setHome();
            player.sendMessage(ChatColor.GREEN + "Home location for this world stored.");

        } else {

            player.teleportHome();
            player.sendMessage(ChatColor.GREEN + "Teleported to home.");

        }

        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return false;
    }
}
