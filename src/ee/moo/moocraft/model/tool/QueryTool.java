package ee.moo.moocraft.model.tool;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * User: Tanel Suurhans
 * Date: 2/27/11
 */
public class QueryTool extends Tool {

    @Override
    public void perform(Player player, Block blockClicked) {

        Block block = player.getTargetBlock(null, 10);
        StringBuilder builder = new StringBuilder("");

        builder.append(ChatColor.GOLD);
        builder.append(String.format("[%s, %s, %s] ", block.getX(), block.getY(), block.getZ()));
        builder.append(ChatColor.WHITE);
        builder.append(block.getType());
        builder.append(String.format(" (%s) ", block.getTypeId()));
        builder.append(ChatColor.DARK_GREEN);
        builder.append(String.format("[%s]", block.getData()));

        player.sendMessage(builder.toString());

    }

    public String getName() {
        return "Query tool";
    }

}
