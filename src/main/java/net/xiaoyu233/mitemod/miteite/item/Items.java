package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.fml.api.item.*;
import net.xiaoyu233.fml.reload.event.ItemRegistryEvent;
import net.xiaoyu233.fml.reload.event.RecipeRegistryEvent;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.Constant;

@SuppressWarnings("ConstantConditions")
public class Items extends Item{
    public static final Item BLAZE_COAL_POWDER = new ItemBlazeCoalPowder(Constant.getNextItemID());
    public static final Item DIAMOND_CHUNK = new Item(Constant.getNextItemID(), Material.diamond,"diamond_chunk").setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.diamond) /(float)4);
    public static final Item OBSIDIAN_STICK = new Item(Constant.getNextItemID(),Material.obsidian,"obsidian_stick");
    public static final ItemAxe VIBRANIUM_AXE = new AxeItem(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemBattleAxe VIBRANIUM_BATTLE_AXE = new BattleAxeItem(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_BOOTS = new ItemBoots(Constant.getNextItemID(),Materials.vibranium,false);
    public static final ItemBow VIBRANIUM_BOW = new ItemBow(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_CHESTPLATE = new ItemCuirass(Constant.getNextItemID(), Materials.vibranium, false);
    public static final ItemDagger VIBRANIUM_DAGGER = new DaggerItem(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_HELMET = new ItemHelmet(Constant.getNextItemID(),Materials.vibranium,false);
    public static final Item VIBRANIUM_INGOT = new ItemIngot(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_LEGGINGS = new ItemLeggings(Constant.getNextItemID(),Materials.vibranium,false);
    public static final ItemNugget VIBRANIUM_NUGGET = new NuggetItem(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemPickaxe VIBRANIUM_PICKAXE = new PickaxeItem(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemShovel VIBRANIUM_SHOVEL = new ShovelItem(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemSword VIBRANIUM_SWORD = new SwordItem(Constant.getNextItemID(), Materials.vibranium);
    public static final ItemWarHammer VIBRANIUM_WAR_HAMMER = new WarHammerItem(Constant.getNextItemID(),Materials.vibranium);
    public static final ItemEnhanceStone IRON_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.iron).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.iron) * 2f);
    public static final ItemEnhanceStone MITHRIL_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.mithril).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.mithril) * 2f);
    public static final ItemEnhanceStone ADAMANTIUM_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.adamantium).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.diamond) * 2f);
    public static final ItemEnhanceStone UNIVERSAL_ENHANCE_STONE = (ItemEnhanceStone) new ItemEnhanceStone(ItemEnhanceStone.Types.universal).setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.ender_pearl) * 2f);

    public static void registerItems(ItemRegistryEvent event) {
        event.register("obsidian_stick", OBSIDIAN_STICK, CreativeTabs.tabMaterials);
        event.register("vibranium", VIBRANIUM_INGOT, CreativeTabs.tabMaterials);
        event.register("vibranium_nugget", VIBRANIUM_NUGGET, CreativeTabs.tabMaterials);
        event.register("vibranium_helmet", VIBRANIUM_HELMET);
        event.register("vibranium_chestplate", VIBRANIUM_CHESTPLATE);
        event.register("vibranium_leggings", VIBRANIUM_LEGGINGS);
        event.register("vibranium_boots", VIBRANIUM_BOOTS);
        event.register("vibranium_sword", VIBRANIUM_SWORD);
        event.register("vibranium_shovel", VIBRANIUM_SHOVEL);
        event.register("vibranium_pickaxe", VIBRANIUM_PICKAXE);
        event.register("vibranium_axe", VIBRANIUM_AXE);
        event.register("vibranium_dagger", VIBRANIUM_DAGGER);
        event.register("vibranium_war_hammer", VIBRANIUM_WAR_HAMMER);
        event.register("vibranium_battle_axe", VIBRANIUM_BATTLE_AXE);
        event.register("diamond_chunk", DIAMOND_CHUNK, CreativeTabs.tabMaterials);
        event.register("blaze_coal_powder", BLAZE_COAL_POWDER, CreativeTabs.tabMaterials);
        event.register("bows/vibranium/", VIBRANIUM_BOW).setUnlocalizedName("vibranium_bow");
        event.register("enhance_stone/iron",IRON_ENHANCE_STONE , CreativeTabs.tabMaterials);
        event.register("enhance_stone/mithril",MITHRIL_ENHANCE_STONE , CreativeTabs.tabMaterials);
        event.register("enhance_stone/adamantium",ADAMANTIUM_ENHANCE_STONE , CreativeTabs.tabMaterials);
        event.register("enhance_stone/universal",UNIVERSAL_ENHANCE_STONE , CreativeTabs.tabMaterials);
        Constant.initItemArray();
    }

    public static void registerRecipes(RecipeRegistryEvent register) {
        register.registerShapedRecipe(new ItemStack(OBSIDIAN_STICK), true, "#", "#", '#', Block.obsidian);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_INGOT),
                false,
                "NIN",
                "IDI",
                "NIN",
                'N',
                Item.mithrilNugget,
                'I',
                Item.ingotAdamantium,
                'D',
                Item.diamond);
        register.registerShapelessRecipe(new ItemStack(VIBRANIUM_NUGGET, 9), true, VIBRANIUM_INGOT);
        register.registerShapelessRecipe(new ItemStack(VIBRANIUM_INGOT),
                true,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET,
                VIBRANIUM_NUGGET);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_HELMET),
                true,
                "#A#",
                "# #",
                '#', VIBRANIUM_INGOT,
                'A', Item.helmetAdamantium).extendsNBT();
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_CHESTPLATE),
                true,
                "# #",
                "#A#",
                "###",
                '#', VIBRANIUM_INGOT,
                'A', Item.plateAdamantium).extendsNBT();
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_LEGGINGS),
                true,
                "#A#",
                "# #",
                "# #",
                '#', VIBRANIUM_INGOT,
                'A', Item.legsAdamantium).extendsNBT();
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_BOOTS),
                true,
                "A #",
                "# #",
                '#', VIBRANIUM_INGOT,
                'A', Item.bootsAdamantium).extendsNBT();
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_PICKAXE),
                true,
                "###",
                " A ",
                " S ",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', pickaxeAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_SHOVEL),
                true,
                "#",
                "A",
                "S",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', shovelAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_SWORD),
                true,
                " # ",
                " # ",
                " A ",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', swordAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_AXE),
                true,
                "##",
                "A#",
                "S ",
                '#', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', axeAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_DAGGER),
                true,
                "I",
                "A",
                'I', VIBRANIUM_INGOT,
                'A', daggerAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_WAR_HAMMER),
                true,
                "III",
                "IAI",
                " S ",
                'I', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', warHammerAncientMetal);
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_BATTLE_AXE),
                true,
                "I I",
                "IAI",
                " S ",
                'I', VIBRANIUM_INGOT,
                'S', OBSIDIAN_STICK,
                'A', battleAxeAncientMetal);
        register.registerShapelessRecipe(new ItemStack(VIBRANIUM_INGOT, 9), true, Blocks.blockVibranium);
        register.registerShapelessRecipe(new ItemStack(Item.diamond),
                false,
                DIAMOND_CHUNK,
                DIAMOND_CHUNK,
                DIAMOND_CHUNK,
                DIAMOND_CHUNK);
        register.registerShapedRecipe(new ItemStack(BLAZE_COAL_POWDER),
                true,
                " B ",
                "BCB",
                " B ",
                'B', Item.blazePowder,
                'C', Item.coal);
        FurnaceRecipes.smelting().addSmelting(Block.coalBlock.blockID, new ItemStack(DIAMOND_CHUNK));
        register.registerShapedRecipe(new ItemStack(VIBRANIUM_BOW),
                true,
                "NSL",
                "AVL",
                "NSL",
                'S', OBSIDIAN_STICK,
                'V', VIBRANIUM_INGOT,
                'L', Item.silk,
                'N', VIBRANIUM_NUGGET,
                'A', bowAncientMetal);
        register.registerShapedRecipe(new ItemStack(Item.emerald),
                true,
                "SS",
                "SS",
                'S', Item.shardEmerald);
        register.registerShapelessRecipe(new ItemStack(Item.shardEmerald, 4), true, Item.emerald);
        register.registerShapedRecipe(new ItemStack(IRON_ENHANCE_STONE),
                true,
                " C ",
                "SIS",
                " C ",
                'I', Item.ingotIron,
                'C', Item.ingotCopper,
                'S', Item.ingotSilver);
        register.registerShapedRecipe(new ItemStack(MITHRIL_ENHANCE_STONE),
                true,
                " I ",
                "GMG",
                " I ",
                'I', Item.ingotIron,
                'G', Item.ingotGold,
                'M', Item.ingotMithril);
        register.registerShapedRecipe(new ItemStack(ADAMANTIUM_ENHANCE_STONE),
                true,
                " D ",
                "MAM",
                " R ",
                'R', Block.blockRedstone,
                'D', Item.diamond,
                'M', Item.ingotMithril,
                'A', Item.ingotAdamantium);
        register.registerShapedRecipe(new ItemStack(UNIVERSAL_ENHANCE_STONE),
                true,
                "gDg",
                "bTb",
                "BEB",
                'g', Item.glowstone,
                'D', Item.bottleOfDisenchanting,
                'b', Item.blazePowder,
                'T', Item.ghastTear,
                'B', Item.book,
                'E', Item.enderPearl);
    }
}
