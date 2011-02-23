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

    private enum Arguments {
        LIST, CREATE, DELETE
    }

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

            switch (Arguments.valueOf(args[0].toUpperCase())) {
                case LIST:
                    this.listWorlds(commandSender);
                    break;
                case CREATE:
                    this.createWorld(commandSender, args);
                    break;
                case DELETE:
                    this.deleteWorld(commandSender, args);
                    break;
                default:
                    return false;
            }

        }

        return true;
    }

    private void listWorlds(CommandSender sender) {

        sender.sendMessage(ChatColor.GREEN + "Total worlds: " + ChatColor.WHITE + plugin.getServer().getWorlds().size());

        for (int i = 0; i < plugin.getServer().getWorlds().size(); i++) {

            StringBuffer buffer = new StringBuffer();
            World world = plugin.getServer().getWorlds().get(i);

            buffer.append(ChatColor.GREEN + "[");
            buffer.append(ChatColor.WHITE).append(i);
            buffer.append(ChatColor.GREEN + "]");
            buffer.append(ChatColor.GREEN + ": ");
            buffer.append(ChatColor.WHITE).append(world.getName());
            buffer.append(ChatColor.GREEN + " (");
            buffer.append(ChatColor.WHITE).append(world.getLivingEntities().size());
            buffer.append(ChatColor.GREEN + " online)");

            sender.sendMessage(buffer.toString());

        }

    }

    private void createWorld(CommandSender sender, String[] args) throws CommandException {

        if (args.length < 2) {
            throw new CommandException("Invalid number of arguments.");
        }

        World world = plugin.getServer().getWorld(args[1]);

        if (world != null) {
            throw new CommandException(String.format("World with the name %s already exists", world.getName()));
        }

        World.Environment env = World.Environment.NORMAL;

        if (args.length == 3 && args[2].equalsIgnoreCase("nether")) {
            env = World.Environment.NETHER;
        }

        world = plugin.getServer().createWorld(args[1], env);

        String worldName = ChatColor.WHITE + world.getName() + ChatColor.WHITE;
        String worldType = ChatColor.WHITE + world.getEnvironment().toString() + ChatColor.WHITE;

        sender.sendMessage(ChatColor.GREEN + String.format("Created world %s (%s)", worldName, worldType));
    }

    private void deleteWorld(CommandSender sender, String[] args) throws CommandException {

        if (args.length < 2) {
            throw new CommandException("Invalid number of arguments.");
        }

        World world = plugin.getServer().getWorld(args[1]);

        if (world == null) {
            throw new CommandException(String.format("World named %s does not exist.", args[1]));
        }

        String worldName = ChatColor.WHITE + world.getName() + ChatColor.GREEN;
        String worldType = ChatColor.WHITE + world.getEnvironment().toString() + ChatColor.GREEN;

        sender.sendMessage(ChatColor.GREEN + String.format("Deleted world %s (%s)", worldName, worldType));
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }

}
