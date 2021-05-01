package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.RecipeRegister;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static net.xiaoyu233.mitemod.miteite.item.Items.VIBRANIUM_INGOT;

public class Blocks {
    public static final Block blockForgingTable = new BlockForgingTable(getNextBlockID())
        .setBlockHardness(8.0F).setExplosionResistance(0.875f).setStepSound_(Block.soundStoneFootstep)
        ;


    public static final BlockAnvil anvilVibranium = ReflectHelper.createInstance(BlockAnvil.class, new Class[]{int.class, Material.class},
            getNextBlockID(), Materials.vibranium);
    public static final BlockOreBlock blockVibranium = new BlockOreBlock(getNextBlockID(),Materials.vibranium);
    public static final Block furnaceVibraniumBurning =
            new BlockFurnaceVibranium(getNextBlockID(), true)
                    .setBlockHardness(8.0F)
                    .setExplosionResistance(0.875f).setStepSound_(Block.soundStoneFootstep)
        ;
    public static final Block furnaceVibraniumIdle =
            new BlockFurnaceVibranium(getNextBlockID(),false).setCreativeTab(CreativeModeTab.tabDecorations)
                    .setBlockHardness(8.0F).setExplosionResistance(0.875f).setStepSound_(Block.soundStoneFootstep);

    static {
        try {
            Field field = Block.class.getDeclaredField("is_normal_cube_lookup");
            field.setAccessible(true);
            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null,new boolean[4096]);
            boolean[] is_normal_block = (boolean[]) field.get(null);
            for (Block block : Block.blocksList) {
                if (block !=null) {
                    is_normal_block[block.blockID] = block.is_normal_cube;
                }
            }
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    private static void registerAnvil(BlockAnvil block,String resourceLocation){
        block.setUnlocalizedName(resourceLocation);
        block.setResourceLocation(resourceLocation);
        Item item = new ItemAnvil(block).setUnlocalizedName(resourceLocation);
        Item.itemsList[Constant.getNextItemID()] = item;
        item.setMaxStackSize(block.getItemStackLimit());
    }
    public static int getNextBlockID() {
        return Constant.nextBlockID++;
    }

    private static void registerBlock(Block block,String resourceLocation){
        block.setUnlocalizedName(resourceLocation);
        block.setResourceLocation(resourceLocation);
    }

    public static void registerBlocks(){

        registerAnvil(anvilVibranium,"anvil_vibranium");
        registerItemBlock(blockVibranium,"block_vibranium");
        anvilVibranium.stepSound = Block.soundAnvilFootstep;
        registerItemBlock(furnaceVibraniumIdle,"furnace_vibranium_idle");
        registerItemBlock(furnaceVibraniumBurning,"furnace_vibranium_burning");
        registerItemBlock(blockForgingTable,"block_forging_table");
    }

    private static void registerItemBlock(Block block,String resourceLocation){
        block.setUnlocalizedName(resourceLocation);
        block.setResourceLocation(resourceLocation);
        Item item = new ItemBlock(block).setUnlocalizedName(resourceLocation);
        Item.itemsList[Constant.getNextItemID()] = item;
        item.setMaxStackSize(block.getItemStackLimit());
    }

    public static void registerRecipes(RecipeRegister register) {
        register.registerShapedRecipe(new ItemStack(anvilVibranium),true,
                "AVA",
                        " I ",
                        "IaI",
                        'A', Item.ingotAdamantium,
                        'V', blockVibranium,
                        'I', VIBRANIUM_INGOT,
                        'a', Block.anvilAncientMetal
        );
        register.registerShapelessRecipe(new ItemStack(blockVibranium),true,
                        VIBRANIUM_INGOT, VIBRANIUM_INGOT, VIBRANIUM_INGOT,
                        VIBRANIUM_INGOT, VIBRANIUM_INGOT, VIBRANIUM_INGOT,
                        VIBRANIUM_INGOT, VIBRANIUM_INGOT, VIBRANIUM_INGOT
        );
        register.registerShapedRecipe(new ItemStack(Blocks.furnaceVibraniumIdle),true,
                "VOA",
                        "DND",
                        "AOV",
                        'V',VIBRANIUM_INGOT,
                        'O',Block.obsidian,
                        'D',Item.diamond,
                        'A',Item.ingotAdamantium,
                        'N', Block.furnaceNetherrackIdle
        );
        register.registerShapedRecipe(new ItemStack(Blocks.blockForgingTable),true,
                "MAM",
                        "HVI",
                        "wWw",
                        'M',Block.blockMithril,
                        'A',Block.blockAdamantium,
                        'H',Items.warHammerMithril,
                        'V', VIBRANIUM_INGOT,
                        'I',Item.axeIron,
                        'W',Block.blockAncientMetal,
                        'w',Items.ingotAncientMetal);
    }
}
