package ee.moo.moocraft;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.smartcardio.CommandAPDU;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public class CommandHandler {

    private static enum Commands {
        GIVE,
        TIME,
        TELEPORT
    }

    private static final long TIME_DAY = 13000;
    private static final long TIME_NIGHT = 0;

    private JavaPlugin plugin;

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean handle(Command command, String[] args, Player sender) {

        switch (Commands.valueOf(command.getName().toUpperCase())) {
            case GIVE:
                return this.handleGive(sender, args);
            case TIME:
                return this.handleTime(sender, args);
            case TELEPORT:
                return this.handleTeleport(sender, args);
        }

        return true;
    }

    private boolean handleGive(Player sender, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage("You are not authorized to use this command.");
            return true;
        }

        if (args.length == 0 || args.length > 3) {
            return false;
        }

        Material material = Material.matchMaterial(args[0]);
        Integer amount = 1;
        Player receiver = sender;

        if (material == null) {
            sender.sendMessage(ChatColor.RED + "Unknown item with ID: " + args[0] + ".");
            return true;
        }

        if (args.length >= 2) {

            try {
                amount = Integer.parseInt(args[1]);
            } catch(NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + args[1] + " is not a valid number.");
                return true;
            }

            amount = Math.min(amount, 64);
        }

        if (args.length == 3) {

            receiver = this.plugin.getServer().getPlayer(args[2]);

            if (receiver == null) {
                sender.sendMessage(ChatColor.RED + args[2] + " is not a valid player name.");
                return true;
            }
            
        }

        if (receiver.getInventory().firstEmpty() < 0) {
            receiver.getWorld().dropItem(receiver.getLocation(), new ItemStack(material, amount));
        } else {
            receiver.getInventory().addItem(new ItemStack(material, amount));
        }

        sender.sendMessage(ChatColor.GREEN + "Given " + receiver.getDisplayName() + " " + amount + " " + material.toString());

        return true;
    }

    private boolean handleTime(Player sender, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage("You are not authorized to use this command.");
            return true;
        }

        Long time       = sender.getWorld().getTime();
        Integer hours   = (int) (time / 1000 + 8) % 24;
        Integer minutes = (int) (time % 1000) * 60 / 1000;

        if (args.length == 0) {

            sender.sendMessage(String.format("Time: %02d:%02d", hours, minutes));

        } else if (args.length == 1) {

            String dayTime = args[0];

            if (dayTime.equalsIgnoreCase("day")) {
                sender.getWorld().setTime(TIME_NIGHT);
            } else if (dayTime.equalsIgnoreCase("night")) {
                sender.getWorld().setTime(TIME_DAY);
            } else {
                return false;
            }

            return true;

        }

        return false;
    }

    private boolean handleTeleport(Player sender, String[] args) {

        if (!sender.isOp()) {
            sender.sendMessage("You are not authorized to use this command.");
            return true;
        }

        return true;
    }

}
