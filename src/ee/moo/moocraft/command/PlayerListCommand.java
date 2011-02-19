package ee.moo.moocraft.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public class PlayerlistCommand extends AbstractCommand {

    public PlayerlistCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        commandSender.sendMessage(ChatColor.GOLD + "Players online: " + this.plugin.getServer().getOnlinePlayers().length);

        if (this.plugin.getServer().getOnlinePlayers().length > 0) {

            for(Player player : this.plugin.getServer().getOnlinePlayers()) {
                commandSender.sendMessage(ChatColor.GRAY + player.getDisplayName());
            }

        }

        return true;
    }

}
