package ee.moo.moocraft.model.tool;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Stack;

/**
 * User: Tanel Suurhans
 * Date: 2/27/11
 */
public class WaterTool extends Tool {

    @Override
    public void perform(Player player, Block blockClicked) {

        int fixed = 0;

        Block block = getTargetBlock(player);
        Location location = block.getLocation();

        int mWater = Material.WATER.getId();
        int sWater = Material.STATIONARY_WATER.getId();

        int px = location.getBlockX();
        int py = location.getBlockY();
        int pz = location.getBlockZ();

        Vector origin = new Vector(location.getX(), location.getY(), location.getZ());

        Stack<BlockVector> queue = new Stack<BlockVector>();
        HashSet<BlockVector> visited = new HashSet<BlockVector>();

        for (int x = px - 1; x <= px + 1; x++) {
            for (int z = pz - 1; z <= pz + 1; z++) {
                for (int y = py - 1; y <= py + 1; y++) {

                    Block obj = location.getWorld().getBlockAt(x, y, z);

                    if ((obj.getTypeId() == mWater || obj.getTypeId() == sWater)) {
                        queue.add(new BlockVector(x, y, z));
                    }

                }
            }
        }

        while (!queue.empty()) {

            BlockVector vector = queue.pop();
            Integer type = location.getWorld().getBlockTypeIdAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());

            if (type != mWater && type != sWater && type != 0)
                continue;

            if (visited.contains(vector))
                continue;

            Block obj = location.getWorld().getBlockAt(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());

            if (obj.getData() > 0) {
                obj.setType(Material.STATIONARY_WATER);
                obj.setData((byte) 0);

                fixed++;
            }

            visited.add(vector);

            if (vector.distance(origin) > getRadius())
                continue;

            queue.push(vector.add(new Vector(1, 0, 0)).toBlockVector());
            queue.push(vector.add(new Vector(-1, 0, 0)).toBlockVector());
            queue.push(vector.add(new Vector(0, 0, 1)).toBlockVector());
            queue.push(vector.add(new Vector(0, 0, -1)).toBlockVector());

        }

        StringBuilder builder = new StringBuilder();

        builder.append(ChatColor.GOLD);
        builder.append(String.format("[%s] ", getName()));
        builder.append(ChatColor.GREEN);
        builder.append(" Fixed ");
        builder.append(fixed);
        builder.append(ChatColor.GREEN);
        builder.append(" blocks of water");

        player.sendMessage(builder.toString());

    }

    public String getName() {
        return "Water tool";
    }

}
