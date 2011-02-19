package ee.moo.moocraft.command;

import ee.moo.moocraft.configuration.ConfigurablePlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

/**
 * User: Tanel Suurhans
 * Date: 2/18/11
 */
public class ConfigCommand extends AbstractCommand {

    public ConfigCommand(ConfigurablePlugin plugin) {
        super(plugin);
    }

    private enum Arguments {
        LOAD,
        SAVE,
        GET,
        SET,
        ADD
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] args) throws CommandException {

        if (!commandSender.isOp()) {
            throw new CommandException("You do not have permissions to use this command.");
        }

        if (args.length > 3 || args.length < 1) {
            return false;
        }

        switch(Arguments.valueOf(args[0].toUpperCase())) {
            case LOAD:
                this.loadConfig(commandSender);
                break;
            case SAVE:
                this.saveConfig(commandSender);
                break;
            case GET:
                this.getConfig(commandSender, args);
                break;
            case SET:
                this.setConfig(commandSender, args);
                break;
            case ADD:
                this.addConfig(commandSender, args);
                break;
            default:
                return false;
        }

        return true;
    }

    private void loadConfig(CommandSender sender) {
        this.plugin.getConfiguration().load();
        sender.sendMessage(ChatColor.GREEN + "Configuration loaded.");
    }

    private void saveConfig(CommandSender sender) {
        if (this.plugin.getConfiguration().save()) {
            sender.sendMessage(ChatColor.GREEN + "Configuration saved.");
        } else {
            sender.sendMessage(ChatColor.RED + "Configuration save failed.");
        }
    }

    private void getConfig(CommandSender sender, String[] args) {

        Object value = this.plugin.getConfiguration().getProperty(args[1]);

        if (value == null) {
            sender.sendMessage(ChatColor.RED + String.format("Property %s could not be found.", ChatColor.WHITE + args[1] + ChatColor.RED));
        } else {
            sender.sendMessage(ChatColor.GREEN + String.format("%s: %s", args[1], value));
        }
        
    }

    private void setConfig(CommandSender sender, String[] args) throws CommandException {

        if(args.length != 3) {
            throw new CommandException("Invalid number of arguments");
        }

        this.plugin.getConfiguration().setProperty(args[1], args[2]);

        sender.sendMessage(ChatColor.GREEN + String.format("Set %s value to %s", ChatColor.WHITE + args[1] + ChatColor.GREEN, ChatColor.WHITE + args[2] + ChatColor.GREEN));

    }

    private void addConfig(CommandSender sender, String[] args) {

    }

    @Override
    public boolean isGameCommand() {
        return true;
    }

    @Override
    public boolean isConsoleCommand() {
        return true;
    }
}
