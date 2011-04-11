package ee.moo.moocraft.command;

import ee.moo.moocraft.MooCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * User: Tanel Suurhans
 * Date: 2/18/11
 */
public class ConfigCommand extends AbstractCommand {

    public ConfigCommand(MooCraft plugin) {
        super(plugin);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {


        if (args.length > 3 || args.length < 1) {
            return false;
        }

        String subCommand = args[0].toUpperCase();

        if (subCommand.equalsIgnoreCase("load")) {

            this.loadConfig(commandSender);

        } else if(subCommand.equalsIgnoreCase("save")) {

            this.saveConfig(commandSender);

        } else if(subCommand.equalsIgnoreCase("get")) {

            this.getConfig(commandSender, args);

        } else {
            return false;
        }

        return true;
    }

    private void loadConfig(CommandSender sender) {

        plugin.getConfigManager().reload();
        sender.sendMessage(ChatColor.GREEN + "Configuration loaded.");

    }

    private void saveConfig(CommandSender sender) {

        if (plugin.getConfigManager().save()) {
            sender.sendMessage(ChatColor.GREEN + "Configuration saved.");
        } else {
            sender.sendMessage(ChatColor.RED + "Configuration save failed.");
        }

    }

    private void getConfig(CommandSender sender, String[] args) throws CommandException {

        if (args.length < 2) {
            throw new CommandException("Configuration key missing.");
        }

        Object value = plugin.getConfigManager().get(args[1]);

        if (value == null) {
            sender.sendMessage(ChatColor.RED + String.format("Property %s could not be found.", ChatColor.WHITE + args[1] + ChatColor.RED));
        } else {
            sender.sendMessage(ChatColor.GREEN + String.format("%s: %s", args[1], value));
        }
        
    }

    @Override
    public boolean isOperatorCommand(String[] args) {
        return true;
    }
}
