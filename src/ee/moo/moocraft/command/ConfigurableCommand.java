package ee.moo.moocraft.command;

import ee.moo.moocraft.configuration.ConfigurablePlugin;

/**
 * User: Tanel Suurhans
 * Date: 2/19/11
 */
public abstract class ConfigurableCommand extends AbstractCommand {

    private ConfigurablePlugin plugin;

    public ConfigurableCommand(ConfigurablePlugin plugin) {
        super(plugin);
    }

}
