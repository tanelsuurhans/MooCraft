package ee.moo.moocraft.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * User: Tanel Suurhans
 * Date: 2/17/11
 */
public class TimeCommand extends AbstractCommand {

    private static final long TIME_DAY = 13000;
    private static final long TIME_NIGHT = 0;

    public TimeCommand(Plugin plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        Player sender = (Player) commandSender;
        Long time = sender.getWorld().getTime();
        Integer hours = (int) (time / 1000 + 8) % 24;
        Integer minutes = (int) (time % 1000) * 60 / 1000;

        if (args.length >= 2) {
            return false;
        }

        if (args.length == 0) {

            commandSender.sendMessage(String.format("Time: %02d:%02d", hours, minutes));

        } else if (args.length == 1) {

            String dayTime = args[0];

            if (dayTime.equalsIgnoreCase("day")) {
                sender.getWorld().setTime(TIME_NIGHT);
            } else if (dayTime.equalsIgnoreCase("night")) {
                sender.getWorld().setTime(TIME_DAY);
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isOperatorCommand(String[] args) {

        if (args.length == 1) {
            return true;
        } else {
            return false;
        }

    }

}
