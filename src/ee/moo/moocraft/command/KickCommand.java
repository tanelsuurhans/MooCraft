package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class KickCommand extends AbstractCommand {

    public KickCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length == 0) {
            return false;
        }

        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null) {
            commandSender.sendMessage(ChatColor.RED + String.format("Cannot find player named %s", ChatColor.WHITE + args[0]));
        } else {

            String kickMessage = "Kicked from server.";
            String[] tokens = Arrays.copyOfRange(args, 1, args.length);

            if (args.length > 2) {
                kickMessage += " Reason: " + StringUtil.join(tokens);
            }

            target.kickPlayer(kickMessage);
        }

        return true;
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }
}
