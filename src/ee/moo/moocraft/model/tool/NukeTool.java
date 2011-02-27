package ee.moo.moocraft.model.tool;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * User: Tanel Suurhans
 * Date: 2/27/11
 */
public class NukeTool extends Tool {

    @Override
    public void perform(Player player, Block blockClicked) {
        
        Random random = new Random();

        if (blockClicked == null)
            return;

        switch(blockClicked.getType()) {

            case STONE:
            case COBBLESTONE:
                nukeBlock(blockClicked, Material.COBBLESTONE);
                break;

            case DIRT:
            case GRASS:
            case SOIL:
                nukeBlock(blockClicked, Material.DIRT);
                break;

            case GRAVEL:
                nukeBlock(blockClicked, Material.GRAVEL);

                if (random.nextDouble() >= 0.9) {
                    nukeBlock(blockClicked, Material.GRAVEL);
                }

                break;
            
            case DIAMOND_ORE:
                nukeBlock(blockClicked, Material.DIAMOND);
                break;
            
            case REDSTONE_ORE:
            case GLOWING_REDSTONE_ORE:
                nukeBlock(blockClicked, Material.REDSTONE, (random.nextInt(2) + 4));
                break;

            case LOG:
                nukeBlock(blockClicked, Material.LOG, 1, blockClicked.getData());
                break;

            case LEAVES:
                nukeBlock(blockClicked, Material.AIR, 0);

                if (random.nextDouble() >= 0.95)
                    nukeBlock(blockClicked, Material.SAPLING);

                break;

            case LAPIS_ORE:
                nukeBlock(blockClicked, Material.INK_SACK, (random.nextInt(5) + 4), (byte) 4);
                break;

            case WOOL:
                nukeBlock(blockClicked, Material.WOOL, 1, blockClicked.getData());
                break;

            case DOUBLE_STEP:
                nukeBlock(blockClicked, Material.STEP, 2, blockClicked.getData());
                break;

            case STEP:
                nukeBlock(blockClicked, Material.STEP, 1, blockClicked.getData());
                break;

            case REDSTONE_WIRE:
                nukeBlock(blockClicked, Material.REDSTONE);
                break;
            
            case CLAY:
                nukeBlock(blockClicked, Material.CLAY_BALL, 4);
                break;
            
            case SIGN_POST:
            case WALL_SIGN:
                nukeBlock(blockClicked, Material.SIGN);
                break;

            case SNOW:
            case ICE:
                nukeBlock(blockClicked, Material.AIR);
                break;
            
            case SAND:
            case SANDSTONE:
            case COAL_ORE:
            case GOLD_ORE:
            case IRON_ORE:
            case WOOD:
            case BOOKSHELF:
            case WOOD_STAIRS:
            case COBBLESTONE_STAIRS:
            case FURNACE:
                nukeBlock(blockClicked, blockClicked.getType());
                break;
        }
        
    }

    private void nukeBlock(Block block, Material drop) {
        nukeBlock(block, drop, 1, (byte) 0);
    }

    private void nukeBlock(Block block, Material drop, int amount) {
        nukeBlock(block, drop, amount, (byte) 0);
    }

    private void nukeBlock(Block block, Material drop, int amount, byte data) {

        block.setType(Material.AIR);

        ItemStack item = new ItemStack(drop, 1, (short) 0, data);

        for (int i = 1; i <= amount; i++) {
            block.getWorld().dropItemNaturally(block.getLocation(), item);
        }

    }

    @Override
    public String getName() {
        return "Nuke tool";
    }

    @Override
    public boolean isRightClick() {
        return false;
    }
}
