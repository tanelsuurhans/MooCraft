package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public class FireCommand extends AbstractCommand {

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

            String subCommand = args[0].toUpperCase();

            if (subCommand.equalsIgnoreCase("burn")) {

                this.burnEnabled(commandSender);

            } else if(subCommand.equalsIgnoreCase("spread")) {

                this.spreadEnabled(commandSender);

            } else if (subCommand.equalsIgnoreCase("enable")) {

                this.changeBurn(commandSender, true);
                this.changeSpread(commandSender, true);

            } else if (subCommand.equalsIgnoreCase("disable")) {

                this.changeBurn(commandSender, false);
                this.changeSpread(commandSender, false);

            } else {
                return false;
            }


        } else if(args.length == 2) {

            String subCommand = args[0].toUpperCase();
            String value = args[1].toLowerCase();

            if (!value.equals("enable") && !value.equals("disable")) {
                return false;
            }

            boolean state = value.equals("enable");

            if (subCommand.equalsIgnoreCase("burn")) {

                this.changeBurn(commandSender, state);

            } else if(subCommand.equalsIgnoreCase("spread")) {

                this.changeSpread(commandSender, state);

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
