package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.liar.item.CraftingManagerHelper;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Blocks {
    static {
        try {
            Field field = Block.class.getDeclaredField("is_normal_cube_lookup");
            field.setAccessible(true);
            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null,new boolean[4096]);
            boolean[] is_normal_block = (boolean[]) field.get(null);
            for (Block block : Block.s) {
                if (block !=null) {
                    is_normal_block[block.cF] = block.is_normal_cube;
                }
            }
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }


    public static final BlockAnvil anvilVibranium = ReflectHelper.createInstance(BlockAnvil.class, new Class[]{int.class, Material.class},
            getNextBlockID(), Materials.vibranium);
    public static final BlockOreBlock blockVibranium = new BlockOreBlock(getNextBlockID(),Materials.vibranium);
    public static final Block furnaceVibraniumIdle = new BlockFurnaceVibranium(getNextBlockID(),false).a(CreativeModeTab.c).setBlockHardness(8.0F).setExplosionResistance(0.875f).setStepSound(Block.k);
    public static final Block furnaceVibraniumBurning = new BlockFurnaceVibranium(getNextBlockID(), true).setBlockHardness(8.0F).setExplosionResistance(0.875f).setStepSound(Block.k);
    public static void registerBlocks(){

        registerBlock(anvilVibranium,"anvil_vibranium");
        registerItemBlock(blockVibranium,"block_vibranium");
        anvilVibranium.cS = Block.r;
        Item.g[Item.getNextItemID()] = new ItemAnvil(anvilVibranium).b("anvil_vibranium");
        registerItemBlock(furnaceVibraniumIdle,"furnace_vibranium_idle");
        registerItemBlock(furnaceVibraniumBurning,"furnace_vibranium_burning");
    }
    public static int getNextBlockID() {
        return Constant.nextBlockID++;
    }

    private static void registerItemBlock(Block block,String resourceLocation){
        block.c(resourceLocation);
        block.setResourceLocation(resourceLocation);
        Item item = new ItemBlock(block).b(resourceLocation);
        Item.g[Item.getNextItemID()] = item;
        item.d(block.getItemStackLimit());
    }
    private static void registerBlock(Block block,String resourceLocation){
        block.c(resourceLocation);
        block.setResourceLocation(resourceLocation);

    }

    public static void registerRecipes() {
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(anvilVibranium),true,
                "AVA",
                        " I ",
                        "ISI",
                        'A', Block.blockAdamantium,
                        'V', blockVibranium,
                        'I', Items.VIBRANIUM_INGOT,
                        'S', Item.bU
        );
        CraftingManagerHelper.registerShapelessRecipe(new ItemStack(blockVibranium),true,
                        Items.VIBRANIUM_INGOT,Items.VIBRANIUM_INGOT,Items.VIBRANIUM_INGOT,
                        Items.VIBRANIUM_INGOT,Items.VIBRANIUM_INGOT,Items.VIBRANIUM_INGOT,
                        Items.VIBRANIUM_INGOT,Items.VIBRANIUM_INGOT,Items.VIBRANIUM_INGOT
        );
    }
}
