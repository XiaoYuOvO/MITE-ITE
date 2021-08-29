package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableLevel;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.RecipeRegister;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static net.xiaoyu233.mitemod.miteite.item.Items.VIBRANIUM_INGOT;

public class Blocks extends Block{
    public static final Block blockForgingTable = new BlockForgingTable(getNextBlockID())
        .setBlockHardness(8.0F).setExplosionResistance(0.875f).setStepSound_(Block.soundStoneFootstep)
        ;


    public static final BlockAnvil anvilVibranium = ReflectHelper.createInstance(BlockAnvil.class, new Class[]{int.class, Material.class},
            getNextBlockID(), Materials.vibranium);
    public static final BlockOreBlock blockVibranium = new BlockOreBlock(getNextBlockID(),Materials.vibranium);
    public static final Block furnaceVibraniumBurning =
            new BlockFurnaceVibranium(getNextBlockID(), true)
                    .setBlockHardness(8.0F)
                    .setExplosionResistance(0.875f).setStepSound_(Block.soundStoneFootstep);
    public static final Block furnaceVibraniumIdle =
            new BlockFurnaceVibranium(getNextBlockID(),false).setCreativeTab(CreativeModeTab.tabDecorations)
                    .setBlockHardness(8.0F).setExplosionResistance(0.875f).setStepSound_(Block.soundStoneFootstep);
    public static final Block netherAdamantiumOre = new BlockNetherAdamantiumOre(getNextBlockID()).setCreativeTab(CreativeModeTab.tabBlock).setBlockHardness(4.0F).setStepSound_(soundStoneFootstep).setUnlocalizedName("oreNetherAdamantium");
    public static final Block chestVibranium = (ReflectHelper.createInstance(BlockStrongbox.class,new Class[] {int.class,Material.class},getNextBlockID(), Materials.vibranium)).setStepSound_(soundMetalFootstep);
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

    protected Blocks(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
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
        registerItemBlock(netherAdamantiumOre,"nether_adamantium_ore");
        registerItemBlock(chestVibranium,"vibranium_chest");
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
        register.registerShapedRecipe(new ItemStack(Blocks.blockForgingTable,1,0),true,
                "WIT",
                " A ",
                "OOO",
                'W',Block.planks,
                'A',Block.anvil,
                'I', Items.ingotIron,
                'T',new ItemStack(Block.workbench,1,7),
                'O',Blocks.obsidian);
        register.registerShapedRecipe(new ItemStack(Blocks.chestVibranium), true,
                "III",
                "I I",
                "III",
                'I', VIBRANIUM_INGOT);
        registerForgingTableUpgradeRecipes(register,ForgingTableLevel.IRON,Item.ingotMithril);
        registerForgingTableUpgradeRecipes(register,ForgingTableLevel.MITHRIL,Item.ingotAdamantium);
        registerForgingTableUpgradeRecipes(register,ForgingTableLevel.ADAMANTIUM, VIBRANIUM_INGOT);
        RecipesFurnace.smelting().addSmelting(Blocks.netherAdamantiumOre.blockID, new ItemStack(Item.ingotAdamantium));
    }

    private static void registerForgingTableUpgradeRecipes(RecipeRegister register, ForgingTableLevel originalLevel, Item ingot){
        register.registerShapedRecipe(new ItemStack(Blocks.blockForgingTable,1,originalLevel.getLevel() + 1),true,
                "III",
                " T ",
                'I', ingot,
                'T', new ItemStack(Blocks.blockForgingTable,1,originalLevel.getLevel()));
    }
}
