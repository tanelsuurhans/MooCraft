package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class MemoryCommand extends AbstractCommand {

    public MemoryCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        Long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        Long freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        Long usedMemory = maxMemory - freeMemory;

        commandSender.sendMessage(ChatColor.GREEN + String.format("Memory usage: %s/%s MB", ChatColor.WHITE + usedMemory.toString(), maxMemory));

        return true;
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }
}
