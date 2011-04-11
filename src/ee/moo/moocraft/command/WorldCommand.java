package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/21/11
 */
public class WorldCommand extends AbstractCommand {

    public WorldCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length == 0) {

            if (commandSender instanceof ConsoleCommandSender) {
                throw new CommandException("You cannot check your current world from the command.");
            }

            commandSender.sendMessage(ChatColor.GREEN + String.format("Your current world is %s", ChatColor.WHITE + ((Player) commandSender).getWorld().getName()));

        } else {

            String subCommand = args[0].toUpperCase();

            if (subCommand.equalsIgnoreCase("list")) {

                this.listWorlds(commandSender);

            } else if (subCommand.equalsIgnoreCase("create")) {

                this.createWorld(commandSender, args);

            } else if(subCommand.equalsIgnoreCase("delete")) {

                this.deleteWorld(commandSender, args);

            } else {
                return false;
            }

        }

        return true;
    }

    private void listWorlds(CommandSender sender) {

        sender.sendMessage(ChatColor.GREEN + "Total worlds: " + ChatColor.WHITE + plugin.getServer().getWorlds().size());

        for (World world : plugin.getServer().getWorlds()) {

            StringBuffer buffer = new StringBuffer();

            buffer.append(ChatColor.WHITE).append(world.getName());
            buffer.append(ChatColor.GREEN + " (");
            buffer.append(ChatColor.WHITE).append(plugin.getWorldManager().getPlayers(world).size());
            buffer.append(ChatColor.GREEN + " online)");

            sender.sendMessage(buffer.toString());

        }

    }

    private void createWorld(CommandSender sender, String[] args) throws CommandException {

        if (args.length < 2) {
            throw new CommandException("Invalid number of arguments.");
        }

        World world = plugin.getWorldManager().getWorld(args[1]);

        if (world != null) {
            throw new CommandException(String.format("World with the name %s already exists", world.getName()));
        }

        World.Environment env = World.Environment.NORMAL;

        if (args.length == 3 && args[2].equalsIgnoreCase("nether")) {
            env = World.Environment.NETHER;
        }

        sender.sendMessage(ChatColor.BLUE + "Staring world generation...");

        world = plugin.getWorldManager().addWorld(args[1], env);

        String worldName = ChatColor.WHITE + world.getName() + ChatColor.WHITE;
        String worldType = ChatColor.WHITE + world.getEnvironment().toString() + ChatColor.WHITE;

        sender.sendMessage(ChatColor.GREEN + String.format("Created world %s (%s)", worldName, worldType));
    }

    private void deleteWorld(CommandSender sender, String[] args) throws CommandException {

        if (args.length < 2) {
            throw new CommandException("Invalid number of arguments.");
        }

        World world = plugin.getWorldManager().getWorld(args[1]);

        if (world == null) {
            throw new CommandException(String.format("World named %s does not exist.", args[1]));
        }

        plugin.getWorldManager().removeWorld(world);

        String worldName = ChatColor.WHITE + world.getName() + ChatColor.GREEN;
        String worldType = ChatColor.WHITE + world.getEnvironment().toString() + ChatColor.GREEN;

        sender.sendMessage(ChatColor.GREEN + String.format("Deleted world %s (%s)", worldName, worldType));
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }

}
