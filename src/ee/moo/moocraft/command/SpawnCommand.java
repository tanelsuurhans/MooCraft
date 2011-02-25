package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import ee.moo.moocraft.util.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/24/11
 */
public class SpawnCommand extends AbstractCommand {

    public SpawnCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length == 0) {
            return false;
        }

        Player player = (Player) commandSender;
        Location target = player.getTargetBlock(null, 100).getLocation();
        CreatureType type = CreatureType.fromName(StringUtil.capitalize(args[0]));

        if (type == null) {
            throw new CommandException(String.format("Cannot find creature of type %s", args[0]));
        }

        target.setX(target.getBlockX());
        target.setY(target.getBlockY());
        target.setZ(target.getBlockZ());

        player.getWorld().spawnCreature(target, type);
        player.sendMessage(ChatColor.GREEN + String.format("Spawned creature %s", ChatColor.WHITE + type.getName()));

        return true;
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return false;
    }
}
