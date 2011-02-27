package ee.moo.moocraft.model.tool;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/27/11
 */
public abstract class Tool {

    private int radius = 1;
    private int distance = 10;

    public Tool() {
    }

    public int getRadius() {
        return radius;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    protected Block getTargetBlock(Player player) {
        return player.getTargetBlock(null, getDistance());
    }

    public abstract String getName();
    public abstract void perform(Player player, Block blockClicked);

    public boolean isRightClick() {
        return true;
    }

}
