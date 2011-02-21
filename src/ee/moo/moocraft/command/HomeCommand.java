package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.command.CommandSender;

/**
 * User: Tanel Suurhans
 * Date: 2/20/11
 */
public class HomeCommand  extends AbstractCommand {

    public HomeCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {
        return true;
    }

    @Override
    public boolean isConsoleCommand(String[] args) {
        return false;
    }
}
