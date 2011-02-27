package ee.moo.moocraft.model.tool;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/27/11
 */
public class ScanTool extends Tool {

    private boolean showDetails = false;

    @Override
    public void perform(Player player, Block blockClicked) {

        Block block = getTargetBlock(player);
        Location location = block.getLocation();

        if (block.getType() == Material.AIR) {
            player.sendMessage(ChatColor.GREEN + "Target a solid block for scanning.");
            return;
        }

        int minX = location.getBlockX() - (getRadius() - 1);
        int minZ = location.getBlockZ() - (getRadius() - 1);

        int maxX = location.getBlockX() + (getRadius() - 1);
        int maxZ = location.getBlockZ() + (getRadius() - 1);

        int scanned = 0;

        StringBuilder builder = new StringBuilder("");

        builder.append(ChatColor.GREEN);
        builder.append("Scanning blocks @ ");
        builder.append(ChatColor.WHITE);
        builder.append(String.format("[%s %s %s]", location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        builder.append(ChatColor.GREEN);
        builder.append(" in ");
        builder.append(ChatColor.WHITE);
        builder.append(getRadius());
        builder.append(ChatColor.GREEN);
        builder.append(" block radius");

        player.sendMessage(builder.toString());

        int ironOre = 0;
        int coalOre = 0;
        int goldOre = 0;
        int diamOre = 0;
        int lazuOre = 0;

        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = block.getLocation().getBlockY(); y >= 0; y--) {

                    switch(location.getWorld().getBlockTypeIdAt(x, y, z)) {

                        case 14: // gold ore
                            if (showDetails)
                                player.sendMessage(ChatColor.GOLD + String.format("Gold @ [%s:%s:%s]", x, y, z));

                            goldOre++;
                            break;
                        case 15: // iron ore
                            if (showDetails)
                                player.sendMessage(ChatColor.DARK_PURPLE + String.format("Iron @ [%s:%s:%s]", x, y, z));

                            ironOre++;
                            break;
                        case 16: // coal ore
                            if (showDetails)
                                player.sendMessage(ChatColor.DARK_GRAY + String.format("Coal @ [%s:%s:%s]", x, y, z));

                            coalOre++;
                            break;
                        case 21: // lapis ore
                            if (showDetails)
                                player.sendMessage(ChatColor.BLUE + String.format("Lapis @ [%s:%s:%s]", x, y, z));

                            lazuOre++;
                            break;
                        case 56: // diamond ore
                            if (showDetails)
                                player.sendMessage(ChatColor.AQUA + String.format("Diamond @ [%s:%s:%s]", x, y, z));

                            diamOre++;
                            break;

                    }

                    scanned++;
                }
            }
        }

        builder = new StringBuilder("");
        builder.append(ChatColor.GREEN);
        builder.append("Found: ");
        builder.append(ChatColor.DARK_GRAY);
        builder.append(String.format("%s coal, ", coalOre));
        builder.append(ChatColor.DARK_PURPLE);
        builder.append(String.format("%s iron, ", ironOre));
        builder.append(ChatColor.GOLD);
        builder.append(String.format("%s gold, ", goldOre));
        builder.append(ChatColor.AQUA);
        builder.append(String.format("%s diamond, ", diamOre));
        builder.append(ChatColor.BLUE);
        builder.append(String.format("%s lapis.", lazuOre));

        player.sendMessage(builder.toString());
        player.sendMessage(ChatColor.GREEN + String.format("Scanned %s blocks.", scanned));

    }

    public boolean toggleDetails() {
        showDetails = !showDetails;
        return showDetails;
    }

    @Override
    public String getName() {
        return "Scan tool";
    }

}
