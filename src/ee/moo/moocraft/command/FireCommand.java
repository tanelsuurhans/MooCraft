package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class FireCommand extends AbstractCommand {

    private enum Arguments {
        BURN, SPREAD, ENABLE, DISABLE
    }

    public FireCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (args.length > 2) {
            return false;
        }

        if (args.length == 0) {

            this.burnEnabled(commandSender);
            this.spreadEnabled(commandSender);

        } else if(args.length == 1) {

            switch(Arguments.valueOf(args[0].toUpperCase())) {

                case BURN:
                    this.burnEnabled(commandSender);
                    break;
                case SPREAD:
                    this.spreadEnabled(commandSender);
                    break;
                case ENABLE:
                    this.changeBurn(commandSender, true);
                    this.changeSpread(commandSender, true);
                    break;
                case DISABLE:
                    this.changeBurn(commandSender, false);
                    this.changeSpread(commandSender, false);
                    break;
                default:
                    return false;

            }


        } else if(args.length == 2) {

            String value = args[1].toLowerCase();

            if (!value.equals("enable") && !value.equals("disable")) {
                return false;
            }

            boolean state = value.equals("enable");

            switch(Arguments.valueOf(args[0].toUpperCase())) {

                case BURN:
                    this.changeBurn(commandSender, state);
                    break;
                case SPREAD:
                    this.changeSpread(commandSender, state);
                    break;
                default:
                    return false;

            }

        }

        return true;
    }

    private void burnEnabled(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + String.format("Fire burning is %s", ChatColor.WHITE + plugin.getConfigManager().booleanString("fire.burn")));
    }

    private void spreadEnabled(CommandSender sender) {
        sender.sendMessage(ChatColor.GREEN + String.format("Fire spreading is %s", ChatColor.WHITE + plugin.getConfigManager().booleanString("fire.spread")));
    }

    private void changeBurn(CommandSender sender, boolean state) {
        plugin.getConfigManager().set("fire.burn", state);
        sender.sendMessage(ChatColor.GREEN + String.format("Fire burning is now %s", ChatColor.WHITE + plugin.getConfigManager().booleanString("fire.burn")));
    }

    private void changeSpread(CommandSender sender, boolean state) {
        plugin.getConfigManager().set("fire.spread", state);
        sender.sendMessage(ChatColor.GREEN + String.format("Fire spreading is now %s", ChatColor.WHITE + plugin.getConfigManager().booleanString("fire.spread")));
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }

}
