package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.model.LocalPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/21/11
 */
public class TravelCommand extends AbstractCommand {

    public TravelCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length == 0) {
            return false;
        }

        World world;

        try {
            Integer index = Integer.valueOf(args[0]);

            world = plugin.getServer().getWorlds().get(index);

            if (world == null) {
                throw new CommandException(String.format("Could not find a world with index %s", index));
            }

        } catch(NumberFormatException e) {

            world = plugin.getServer().getWorld(args[0]);

            if (world == null) {
                throw new CommandException(String.format("Could not find a world with name %s", args[0]));
            }

        }


        LocalPlayer player = plugin.getPlayerManager().getPlayer((Player) commandSender);

        player.teleportHome(world);
        player.sendMessage(ChatColor.GREEN + String.format("You have traveled to %s", ChatColor.WHITE + world.getName()));

        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return false;
    }
}
