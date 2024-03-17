package net.xiaoyu233.mitemod.miteite.block;

import net.minecraft.*;
import net.xiaoyu233.fml.api.block.AnvilBlock;
import net.xiaoyu233.fml.api.block.StrongBoxBlock;
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent;
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent;
import net.xiaoyu233.fml.util.IdUtil;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableLevel;

import static net.xiaoyu233.mitemod.miteite.item.Items.VIBRANIUM_INGOT;

public class Blocks extends Block{
    public static final Block blockForgingTable = new BlockForgingTable(getNextBlockID()).setHardness(8.0F).setResistance(0.875f).setStepSound(Block.soundStoneFootstep);
    public static final BlockAnvil anvilVibranium = new AnvilBlock(getNextBlockID(), Materials.vibranium);
    public static final BlockOreStorage blockVibranium = new BlockOreStorage(getNextBlockID(),Materials.vibranium);
    public static final Block furnaceVibraniumBurning = new BlockFurnaceVibranium(getNextBlockID(), true).setHardness(8.0F).setResistance(0.875f).setStepSound(Block.soundStoneFootstep);
    public static final Block furnaceVibraniumIdle = new BlockFurnaceVibranium(getNextBlockID(), false).setCreativeTab(CreativeTabs.tabDecorations).setHardness(8.0F).setResistance(0.875f).setStepSound(Block.soundStoneFootstep);
    public static final Block netherAdamantiumOre = new BlockNetherAdamantiumOre(getNextBlockID()).setCreativeTab(CreativeTabs.tabBlock).setHardness(4.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreNetherAdamantium");
    public static final Block chestVibranium = new StrongBoxBlock(getNextBlockID(), Materials.vibranium).setStepSound(soundMetalFootstep);
    protected Blocks(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    public static int getNextBlockID() {
        return IdUtil.getNextBlockID();
    }

    public static void registerItemBlocks(ItemRegistryEvent registryEvent){
        anvilVibranium.stepSound = Block.soundAnvilFootstep;
        registryEvent.registerAnvil(anvilVibranium,"anvil_vibranium", IdUtil.getNextItemID());
        registryEvent.registerItemBlock(blockVibranium,"block_vibranium", IdUtil.getNextItemID());
        registryEvent.registerItemBlock(furnaceVibraniumIdle,"furnace_vibranium_idle", IdUtil.getNextItemID());
        registryEvent.registerItemBlock(furnaceVibraniumBurning,"furnace_vibranium_burning", IdUtil.getNextItemID());
        registryEvent.registerItemBlock(blockForgingTable,"block_forging_table", IdUtil.getNextItemID());
        registryEvent.registerItemBlock(netherAdamantiumOre,"nether_adamantium_ore", IdUtil.getNextItemID());
        registryEvent.registerItemBlock(chestVibranium,"vibranium_chest", IdUtil.getNextItemID());
    }

    public static void registerRecipes(RecipeRegistryEvent register) {
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
        FurnaceRecipes.smelting().addSmelting(Blocks.netherAdamantiumOre.blockID, new ItemStack(Item.ingotAdamantium));
    }

    private static void registerForgingTableUpgradeRecipes(RecipeRegistryEvent register, ForgingTableLevel originalLevel, Item ingot){
        register.registerShapedRecipe(new ItemStack(Blocks.blockForgingTable,1,originalLevel.getLevel() + 1),true,
                "III",
                " T ",
                'I', ingot,
                'T', new ItemStack(Blocks.blockForgingTable,1,originalLevel.getLevel()));
    }
}
