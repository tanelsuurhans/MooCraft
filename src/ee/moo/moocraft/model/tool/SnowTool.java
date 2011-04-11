package ee.moo.moocraft.model.tool;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/27/11
 */
public class SnowTool extends Tool {

    @Override
    public void perform(Player player, Block blockClicked) {

        int removed = 0;

        Block block = getTargetBlock(player);
        Location location = block.getLocation();

        int minX = location.getBlockX() - (getRadius() - 1);
        int minZ = location.getBlockZ() - (getRadius() - 1);

        int maxX = location.getBlockX() + (getRadius() - 1);
        int maxZ = location.getBlockZ() + (getRadius() - 1);


        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {

                for (int y = 127; y >= 1; y--) {

                    Block obj = location.getWorld().getBlockAt(x, y, z);

                    switch(obj.getType()) {
                        case SNOW:
                            obj.setType(Material.AIR);
                            removed++;
                            break;
                        case ICE:
                            obj.setType(Material.STATIONARY_WATER);
                            removed++;
                            break;
                    }
                }
            }
        }

        player.sendMessage(ChatColor.GREEN + String.format("Removed %s blocks.", removed));
    }

    public String getName() {
        return "Snow tool";
    }

}
