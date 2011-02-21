package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public class PlayerlistCommand extends AbstractCommand {

    public PlayerlistCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        ArrayList<String> players = new ArrayList<String>();

        if (plugin.getServer().getOnlinePlayers().length > 0) {

            for(Player player : plugin.getServer().getOnlinePlayers()) {
                players.add(player.getDisplayName());
            }

        }

        commandSender.sendMessage(ChatColor.GREEN + "Players online: " + ChatColor.GOLD + plugin.getServer().getOnlinePlayers().length);
        commandSender.sendMessage(ChatColor.GRAY + StringUtil.join(players.toArray(), ", "));

        return true;
    }

}
