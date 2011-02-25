package ee.moo.moocraft.model;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/24/11
 */
public class LocalWarp {

    private String name;
    private Player player;
    private Location location;

    public LocalWarp(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public LocalWarp(String name, Location location, Player player) {
        this.name = name;
        this.player = player;
        this.location = location;
    }

    public String getName() {
        return this.name;
    }

    public Location getLocation() {
        return this.location;
    }
    
    public String getWorldName() {
        return this.location.getWorld().getName();
    }

    public boolean ownedBy(Player player) {
        return player.getDisplayName().equals(this.player.getDisplayName());
    }

    public boolean isPrivate() {
        return !(player == null);
    }

}
