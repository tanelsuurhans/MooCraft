package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/23/11
 */
public class WarpCommand extends AbstractCommand {

    private enum Arguments {
        CREATE, REMOVE
    }

    public WarpCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length == 0) {
            return false;
        }

        if (args.length == 2) {

            switch (Arguments.valueOf(args[1].toUpperCase())) {

                case CREATE:
                    this.createWarp((Player) commandSender, args);
                    break;
                case REMOVE:
                    this.removeWarp((Player) commandSender, args);
                    break;
                default:
                    return false;

            }


        } else {

            if (args[0].equals("list")) {

            } else {
                // TODO: teleport
            }

        }

        return true;
    }

    private void createWarp(Player sender, String[] args) {
        plugin.getWarpManager().addWarp();
    }

    private void removeWarp(Player sender, String[] args) {
        plugin.getWarpManager().removeWarp();
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
