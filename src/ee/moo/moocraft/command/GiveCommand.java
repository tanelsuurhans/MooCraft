package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public class GiveCommand extends AbstractCommand {

    public GiveCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length < 2 || args.length > 3) {
            return false;
        }

        Material material = Material.matchMaterial(args[1]);
        Player target = plugin.getServer().getPlayer(args[0]);
        Integer amount = 1;

        if (target == null) {
            throw new CommandException(String.format("%s is not a valid player name.", args[0]));
        }

        if (material == null) {
            throw new CommandException(String.format("Unknown item with ID %s.", args[1]));
        }

        if (args.length == 3) {

            try {
                amount = Integer.parseInt(args[2]);
            } catch(NumberFormatException e) {
                throw new CommandException(String.format("%s is not a valid number.", args[2]));
            }

            amount = Math.min(amount, 64);
        }

        if (target.getInventory().firstEmpty() < 0) {
            target.getWorld().dropItem(target.getLocation(), new ItemStack(material, amount));
        } else {
            target.getInventory().addItem(new ItemStack(material, amount));
        }

        commandSender.sendMessage(ChatColor.GREEN + "Given " + target.getDisplayName() + " " + amount + " " + material.toString());

        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return false;
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }
}
