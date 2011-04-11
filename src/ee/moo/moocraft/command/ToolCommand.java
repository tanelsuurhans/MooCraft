package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalPlayer;
import ee.moo.moocraft.model.tool.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/27/11
 */
public class ToolCommand extends AbstractCommand {

    private static int MAX_RADIUS = 100;

    public ToolCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        LocalPlayer player = plugin.getPlayerManager().getPlayer((Player) commandSender);

        if (args.length < 1) {
            return false;
        }

        String subCommand = args[0].toUpperCase();

        if (subCommand.equalsIgnoreCase("query")) {

            this.handleQueryTool(player);

        } else if(subCommand.equalsIgnoreCase("water")) {

            this.handleWaterTool(player);

        } else if (subCommand.equalsIgnoreCase("fire")) {

            this.handleFireTool(player);

        } else if (subCommand.equalsIgnoreCase("snow")) {

            this.handleSnowTool(player);

        } else if (subCommand.equalsIgnoreCase("scan")) {

            this.handleScan(player, args);

        } else if (subCommand.equalsIgnoreCase("radius")) {

            this.handleRadius(player,  args);

        } else if (subCommand.equalsIgnoreCase("check")) {

            this.handleCheck(player);

        } else if (subCommand.equalsIgnoreCase("nuke")) {

            this.handleNuke(player);

        } else {
            return false;
        }

        return true;
    }

    private void handleQueryTool(LocalPlayer player) throws CommandException {

        String itemName = player.getItemInHand().getType().toString();
        Tool tool = player.getTool(QueryTool.class);

        if (player.getItemInHand().getTypeId() < 255) {
            throw new CommandException("Cannot use blocks as tools.");
        }

        if (tool == null) {
            player.setTool(new QueryTool());
            player.sendMessage(ChatColor.GREEN + String.format("Query tool bound to %s", ChatColor.GOLD + itemName));
        } else {
            player.removeTool();
            player.sendMessage(ChatColor.GREEN + String.format("Query tool unbound from %s", ChatColor.GOLD + itemName));
        }

    }

    private void handleWaterTool(LocalPlayer player) throws CommandException {

        String itemName = player.getItemInHand().getType().toString();
        Tool tool = player.getTool(WaterTool.class);

        if (player.getItemInHand().getTypeId() < 255) {
            throw new CommandException("Cannot use blocks as tools.");
        }

        if (tool == null) {
            player.setTool(new WaterTool());
            player.sendMessage(ChatColor.GREEN + String.format("Water tool bound to %s", ChatColor.GOLD + itemName));
        } else {
            player.removeTool();
            player.sendMessage(ChatColor.GREEN + String.format("Water tool unbound from %s", ChatColor.GOLD + itemName));
        }

    }

    private void handleFireTool(LocalPlayer player) throws CommandException {

    }

    private void handleSnowTool(LocalPlayer player) throws CommandException {

        String itemName = player.getItemInHand().getType().toString();
        Tool tool = player.getTool(SnowTool.class);

        if (player.getItemInHand().getTypeId() < 255) {
            throw new CommandException("Cannot use blocks as tools.");
        }

        if (tool == null) {
            player.setTool(new SnowTool());
            player.sendMessage(ChatColor.GREEN + String.format("Snow tool bound to %s", ChatColor.GOLD + itemName));
        } else {
            player.removeTool();
            player.sendMessage(ChatColor.GREEN + String.format("Snow tool unbound from %s", ChatColor.GOLD + itemName));
        }

    }

    private void handleNuke(LocalPlayer player) throws CommandException {

        String itemName = player.getItemInHand().getType().toString();
        Tool tool = player.getTool(NukeTool.class);

        if (player.getItemInHand().getTypeId() < 255) {
            throw new CommandException("Cannot use blocks as tools.");
        }

        if (tool == null) {
            player.setTool(new NukeTool());
            player.sendMessage(ChatColor.GREEN + String.format("Nuke tool bound to %s", ChatColor.GOLD + itemName));
        } else {
            player.removeTool();
            player.sendMessage(ChatColor.GREEN + String.format("Nuke tool unbound from %s", ChatColor.GOLD + itemName));
        }

    }

    private void handleScan(LocalPlayer player, String[] args) throws CommandException {

        String itemName = player.getItemInHand().getType().toString();
        ScanTool tool = (ScanTool) player.getTool(ScanTool.class);

        if (player.getItemInHand().getTypeId() < 255) {
            throw new CommandException("Cannot use blocks as tools.");
        }

        if (tool == null) {

            tool = new ScanTool();

            if (args.length == 2 && args[1].equalsIgnoreCase("details")) {
                tool.toggleDetails();
            }

            player.setTool(tool);
            player.sendMessage(ChatColor.GREEN + String.format("Scan tool bound to %s", ChatColor.GOLD + itemName));

        } else {

            if (args.length == 2 && args[1].equalsIgnoreCase("details")) {

                if(tool.toggleDetails()) {
                    player.sendMessage(ChatColor.GREEN + "Scan details are now " + ChatColor.YELLOW + "ON");
                } else {
                    player.sendMessage(ChatColor.GREEN + "Scan details are now " + ChatColor.DARK_RED + "OFF");
                }

            } else {
                player.removeTool();
                player.sendMessage(ChatColor.GREEN + String.format("Scan tool unbound from %s", ChatColor.GOLD + itemName));
            }
        }

    }

    private void handleRadius(LocalPlayer player, String[] args) throws CommandException {

        Tool tool = player.getTool();

        if (tool == null) {
            player.sendMessage(ChatColor.GREEN + "You need to bind a tool first to set/get it's radius.");
            return;
        }

        if (args.length == 1) {

            StringBuilder builder = new StringBuilder("");

            builder.append(ChatColor.GREEN);
            builder.append("Radius for ");
            builder.append(ChatColor.GOLD);
            builder.append(tool.getName());
            builder.append(ChatColor.GREEN);
            builder.append(" is ");
            builder.append(ChatColor.WHITE);
            builder.append(tool.getRadius());

            player.sendMessage(builder.toString());

        } else {

            int radius;

            try {
                radius = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                throw new CommandException("Invalid number for radius.");
            }

            if (radius > MAX_RADIUS) {
                throw new CommandException("Maximum value for radius is 100");
            }

            tool.setRadius(radius);

            StringBuilder builder = new StringBuilder();

            builder.append(ChatColor.GREEN);
            builder.append("Radius for ");
            builder.append(ChatColor.GOLD);
            builder.append(tool.getName());
            builder.append(ChatColor.GREEN);
            builder.append(" is set to ");
            builder.append(ChatColor.WHITE);
            builder.append(radius);

            player.sendMessage(builder.toString());

        }

    }

    private void handleCheck(LocalPlayer player) {

        Tool tool = player.getTool();
        String itemName = player.getItemInHand().getType().toString();

        if (tool == null) {
            player.sendMessage(ChatColor.GOLD + itemName + ChatColor.GREEN + " is not bound to any tool.");
        } else {
            player.sendMessage(ChatColor.GOLD + itemName + ChatColor.GREEN + " is bound to " + ChatColor.WHITE + tool.getName());
        }

    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return true;
    }
}
