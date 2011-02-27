package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class GodCommand extends AbstractCommand {

    public GodCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length == 0) {
            return false;
        }

        if (args.length == 2) {

            this.setPlayer(commandSender, args);

        } else {

            if (args[0].equals("list")) {
                this.listPlayers(commandSender);
            } else {
                this.getPlayer(commandSender, args);
            }

        }

        return true;
    }

    private void listPlayers(CommandSender sender) {

        List<String> players = new ArrayList<String>();

        for (Player player : plugin.getServer().getOnlinePlayers()) {

            if (plugin.getPlayerManager().getPlayer(player).isGod()) {
                players.add(player.getDisplayName());
            }

        }

        if (players.isEmpty()) {
            sender.sendMessage(ChatColor.GREEN + "No players with god mode found.");
        } else {
            sender.sendMessage(ChatColor.GREEN + "Players with god mode: " + ChatColor.GOLD + players.size());
            sender.sendMessage(ChatColor.GRAY + StringUtil.join(players.toArray(), ", "));
        }
    }

    private void getPlayer(CommandSender sender, String[] args) throws CommandException {

        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null) {
            throw new CommandException(String.format("Cannot find player named %s.", args[0]));
        }

        StringBuilder builder = new StringBuilder();

        builder.append(ChatColor.GREEN);
        builder.append("God mode for ");
        builder.append(ChatColor.WHITE);
        builder.append(target.getDisplayName());
        builder.append(ChatColor.GREEN);
        builder.append(": ");

        if (plugin.getPlayerManager().getPlayer(target).isGod()) {
            builder.append(ChatColor.GOLD + "ON");
        } else {
            builder.append(ChatColor.RED + "OFF");
        }

        sender.sendMessage(builder.toString());
    }

    private void setPlayer(CommandSender sender, String[] args) throws CommandException {

        if (args.length != 2) {
            throw new CommandException("Invalid value for setting god mode");
        }

        boolean isGod;

        if (args[1].matches("enable|on|true")) {
            isGod = true;
        } else if (args[1].matches("disable|off|false")) {
            isGod = false;
        } else {
            throw new CommandException("Invalid command argument");
        }

        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null) {
            throw new CommandException(String.format("Cannot find player named %s", args[0]));
        }

        plugin.getPlayerManager().getPlayer(target).setGod(isGod);

        StringBuilder builder = new StringBuilder();

        builder.append(ChatColor.GREEN);
        builder.append("God mode for");
        builder.append(ChatColor.WHITE);
        builder.append(target.getDisplayName());
        builder.append(ChatColor.GREEN);
        builder.append(" is now ");

        if (isGod) {
            builder.append(ChatColor.GOLD);
            builder.append("ON");
        } else {
            builder.append(ChatColor.RED);
            builder.append("OFF");
        }

        sender.sendMessage(builder.toString());


    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }

}