package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class HealCommand extends AbstractCommand {

    public HealCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender sender, String command, String[] args) throws CommandException {

        if (args.length == 1) {

            Player target = plugin.getServer().getPlayer(args[0]);

            if (target == null) {
                throw new CommandException(String.format("Cannot find player named %s", args[0]));
            }

            target.setHealth(20);

            sender.sendMessage(ChatColor.GREEN + String.format("%s has been healed.", target.getDisplayName()));

            if (sender instanceof Player) {
                target.sendMessage(ChatColor.GOLD + String.format("%s healed you.", ((Player) sender).getDisplayName()));
            } else {
                target.sendMessage(ChatColor.GOLD + "[CONSOLE] You have been healed");
            }

        } else {

            if (sender instanceof ConsoleCommandSender) {
                throw new CommandException("You cannot heal yourself from the console.");
            }

            ((Player) sender).setHealth(20);

            sender.sendMessage(ChatColor.GOLD + "You have been healed.");
        }

        return true;
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return args.length == 1;
    }

}
