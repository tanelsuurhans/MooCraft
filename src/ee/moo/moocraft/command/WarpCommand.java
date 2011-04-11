package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalWarp;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Tanel Suurhans
 * Date: 2/23/11
 */
public class WarpCommand extends AbstractCommand {

    private enum Arguments {
        CREATE, REMOVE, PRIVATE
    }

    public WarpCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length == 0) {
            return false;
        }

        Player player = (Player) commandSender;
        String subCommand = args[0].toUpperCase();

        if (args.length == 2) {

            if (subCommand.equalsIgnoreCase("create")) {

                this.createWarp(player, args,  false);

            } else if (subCommand.equalsIgnoreCase("private")) {

                this.createWarp(player, args, true);

            } else if (subCommand.equalsIgnoreCase("remove")) {

                this.removeWarp(player, args);

            } else {
                return false;
            }

        } else {

            if (subCommand.equalsIgnoreCase("list")) {

                List<LocalWarp> warps = new ArrayList<LocalWarp>();

                for (LocalWarp warp : plugin.getWarpManager().getWarps(player.getWorld())) {

                    if (!warp.isPrivate() || warp.ownedBy(player))
                        warps.add(warp);
                }

                commandSender.sendMessage(ChatColor.GREEN + String.format("Total warps in this world: %s", ChatColor.WHITE + String.valueOf(warps.size())));

                for (LocalWarp warp : warps) {
                    commandSender.sendMessage(warp.getName());
                }

            } else {

                LocalWarp warp = plugin.getWarpManager().getWarp(args[0], player.getWorld());

                if (warp == null) {
                    throw new CommandException(String.format("Warp %s was not found.", args[0]));
                }

                if (warp.isPrivate() && !warp.ownedBy(player)) {
                    throw new CommandException(String.format("Warp %s is a private warp.", warp.getName()));
                }

                player.teleport(warp.getLocation());

            }

        }

        return true;
    }

    private void createWarp(Player sender, String[] args, boolean isPrivate) throws CommandException {

        LocalWarp warp = plugin.getWarpManager().getWarp(args[1], sender.getWorld());

        if (warp != null) {
            throw new CommandException(String.format("Warp named %s already exists.", args[1]));
        }

        if (isPrivate) {

            warp = plugin.getWarpManager().addWarp(args[1], sender.getLocation(), sender);
            sender.sendMessage(ChatColor.GREEN + String.format("Created a private warp named %s.", ChatColor.WHITE + warp.getName()));

        } else {
            warp = plugin.getWarpManager().addWarp(args[1], sender.getLocation());
            sender.sendMessage(ChatColor.GREEN + String.format("Created a public warp named %s.", ChatColor.WHITE + warp.getName()));
        }

    }

    private void removeWarp(Player sender, String[] args) throws CommandException {

        LocalWarp warp = plugin.getWarpManager().getWarp(args[1], sender.getWorld());

        if (warp == null) {
            throw new CommandException(String.format("Cannot find warp named %s in this world.", args[1]));
        }

        if (warp.isPrivate() && !warp.ownedBy(sender)) {
            throw new CommandException(String.format("Warp %s is a private warp.", warp.getName()));
        }

        plugin.getWarpManager().removeWarp(warp);

        sender.sendMessage(ChatColor.GREEN + String.format("Warp %s removed.", warp.getName()));
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return false;
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return args.length >= 2;
    }
}
