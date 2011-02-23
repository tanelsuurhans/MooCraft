package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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

        ArrayList<String> players = new ArrayList<String>();

        for (Player player : plugin.getServer().getOnlinePlayers()) {

            if (plugin.getPlayerManager().hasGodMode(player)) {
                players.add(player.getDisplayName());
            }

        }

        if (players.isEmpty()) {
            sender.sendMessage(ChatColor.GREEN + "No players with god mode online.");
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

        String message = ChatColor.GREEN + String.format("God mode for %s is ", ChatColor.WHITE + target.getDisplayName() + ChatColor.GREEN);

        if (plugin.getPlayerManager().hasGodMode(target)) {
            sender.sendMessage(String.format(message + "%s", ChatColor.WHITE + "on"));
        } else {
            sender.sendMessage(String.format(message + "%s", ChatColor.WHITE + "off"));
        }

    }

    private void setPlayer(CommandSender sender, String[] args) throws CommandException {

        if (args.length != 2) {
            throw new CommandException("Invalid value for setting god mode");
        }

        boolean isGod;

        if (args[1].equals("enable") || args[1].equals("on") || args[1].equals("true")) {
            isGod = true;
        } else if (args[1].equals("disable") || args[1].equals("off") || args[1].equals("false")) {
            isGod = false;
        } else {
            throw new CommandException("Invalid command argument");
        }

        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null) {
            throw new CommandException(String.format("Cannot find player named %s", args[0]));
        }

        plugin.getPlayerManager().setGodMode(target, isGod);

        /** send the messages */
        this.getPlayer(sender, args);
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }

}