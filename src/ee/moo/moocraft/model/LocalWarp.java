package ee.moo.moocraft.model;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/24/11
 */
public class LocalWarp {

    private String name;
    private Location location;
    private Player player;

    public LocalWarp(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public LocalWarp(String name, Location location, Player player) {
        this.name = name;
        this.player = player;
        this.location = location;
    }

    public boolean isPrivate() {
        return !(player == null);
    }

}
