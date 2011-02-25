package ee.moo.moocraft.model;

import org.bukkit.World;

/**
 * User: Tanel Suurhans
 * Date: 2/24/11
 */
public class LocalWorld {

    private String name;
    private World.Environment environment;

    public LocalWorld(String name, World.Environment environment) {
        this.name = name;
        this.environment = environment;
    }

    public String getName() {
        return this.name;
    }

}
