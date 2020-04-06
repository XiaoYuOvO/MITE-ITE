package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.trans.item.CraftingManagerHelper;
import net.xiaoyu233.mitemod.miteite.trans.item.ItemTrans;
import net.xiaoyu233.mitemod.miteite.util.Constant;

import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.createInstance;

@SuppressWarnings("ConstantConditions")
public class Items extends Item{
    public static final Item OBSIDIAN_STICK = createInstance(Item.class,new Class[]{int.class,Material.class,String.class},ItemTrans.getNextItemID(),Material.obsidian,"obsidian_stick");
    public static final Item VIBRANIUM_INGOT = createInstance(ItemIngot.class,new Class[]{int.class,Material.class},ItemTrans.getNextItemID(),Materials.vibranium);
    public static final ItemNugget VIBRANIUM_NUGGET = createInstance(ItemNugget.class, new Class[]{int.class,Material.class},ItemTrans.getNextItemID(),Materials.vibranium);
    public static final ItemArmor VIBRANIUM_HELMET = new ItemHelmet(ItemTrans.getNextItemID(),Materials.vibranium,false);
    public static final ItemArmor VIBRANIUM_CHESTPLATE = new ItemCuirass(ItemTrans.getNextItemID(), Materials.vibranium, false);
    public static final ItemArmor VIBRANIUM_LEGGINGS = new ItemLeggings(ItemTrans.getNextItemID(),Materials.vibranium,false);
    public static final ItemArmor VIBRANIUM_BOOTS = new ItemBoots(ItemTrans.getNextItemID(),Materials.vibranium,false);
    public static final ItemSword VIBRANIUM_SWORD = createInstance(ItemSword.class,new Class[]{int.class,Material.class},ItemTrans.getNextItemID(), Materials.vibranium);
    public static final ItemShovel VIBRANIUM_SHOVEL = createInstance(ItemShovel.class,new Class[]{int.class,Material.class},ItemTrans.getNextItemID(),Materials.vibranium);
    public static final ItemPickaxe VIBRANIUM_PICKAXE = createInstance(ItemPickaxe.class,new Class[]{int.class,Material.class},ItemTrans.getNextItemID(),Materials.vibranium);
    public static final ItemAxe VIBRANIUM_AXE = createInstance(ItemAxe.class,new Class[]{int.class,Material.class},ItemTrans.getNextItemID(),Materials.vibranium);
    public static final ItemDagger VIBRANIUM_DAGGER = createInstance(ItemDagger.class,new Class[]{int.class,Material.class},ItemTrans.getNextItemID(),Materials.vibranium);
    public static final ItemWarHammer VIBRANIUM_WAR_HAMMER = createInstance(ItemWarHammer.class,new Class[]{int.class,Material.class},ItemTrans.getNextItemID(),Materials.vibranium);
    public static final ItemBattleAxe VIBRANIUM_BATTLE_AXE = createInstance(ItemBattleAxe.class,new Class[]{int.class,Material.class},ItemTrans.getNextItemID(),Materials.vibranium);
    public static final Item DIAMOND_CHUNK = createInstance(Item.class,new Class[]{int.class,Material.class,String.class},ItemTrans.getNextItemID(),Material.diamond,"diamond_chunk").setCraftingDifficultyAsComponent(ItemRock.getCraftingDifficultyAsComponent(Material.diamond) /(float)4);
    public static final Item BLAZE_COAL = new ItemBlazeCoal(ItemTrans.getNextItemID());
    public static final ItemBow VIBRANIUM_BOW = new ItemBow(ItemTrans.getNextItemID(),Materials.vibranium);
    public static void registerItems() {
        register("obsidian_stick",OBSIDIAN_STICK,CreativeModeTab.l);
        register("vibranium", VIBRANIUM_INGOT,CreativeModeTab.l);
        register("vibranium_nugget",VIBRANIUM_NUGGET,CreativeModeTab.l);
        register("vibranium_helmet",VIBRANIUM_HELMET);
        register("vibranium_chestplate",VIBRANIUM_CHESTPLATE);
        register("vibranium_leggings",VIBRANIUM_LEGGINGS);
        register("vibranium_boots",VIBRANIUM_BOOTS);
        register("vibranium_sword",VIBRANIUM_SWORD);
        register("vibranium_shovel",VIBRANIUM_SHOVEL);
        register("vibranium_pickaxe",VIBRANIUM_PICKAXE);
        register("vibranium_axe",VIBRANIUM_AXE);
        register("vibranium_dagger",VIBRANIUM_DAGGER);
        register("vibranium_war_hammer",VIBRANIUM_WAR_HAMMER);
        register("vibranium_battle_axe",VIBRANIUM_BATTLE_AXE);
        register("diamond_chunk",DIAMOND_CHUNK,CreativeModeTab.l);
        register("blaze_coal",BLAZE_COAL,CreativeModeTab.l);
        register("bows/vibranium/",VIBRANIUM_BOW).b("vibranium_bow");
        Constant.initItemArray();
    }
    public static void registerRecipes(){
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(OBSIDIAN_STICK),true,
                "#",
                "#",
                '#',Block.au
        );
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_INGOT),false,
                 "NIN",
                         "IDI",
                         "NIN",
                         'N',Item.mithrilNugget,
                         'I',Item.ingotAdamantium,
                         'D',Item.p
        );
        CraftingManagerHelper.registerShapelessRecipe(new ItemStack(VIBRANIUM_NUGGET,9),true,Items.VIBRANIUM_INGOT);
        CraftingManagerHelper.registerShapelessRecipe(new ItemStack(VIBRANIUM_INGOT),true,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET,Items.VIBRANIUM_NUGGET);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_HELMET),true,
                "###",
                        "# #",
                        '#',VIBRANIUM_INGOT);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_CHESTPLATE),true,
                "# #",
                        "###",
                        "###",
                        '#',VIBRANIUM_INGOT);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_LEGGINGS),true,
                "###",
                        "# #",
                        "# #",
                        '#',VIBRANIUM_INGOT);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_BOOTS),true,
                "# #",
                        "# #",
                        '#',VIBRANIUM_INGOT);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_PICKAXE),true,
                "###",
                        " S ",
                        " S ",
                        '#',VIBRANIUM_INGOT,
                        'S',OBSIDIAN_STICK);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_SHOVEL),true,
                "#",
                        "S",
                        "S",
                        '#',VIBRANIUM_INGOT,
                        'S',OBSIDIAN_STICK);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_SWORD),true,
                " # ",
                        " # ",
                        " S ",
                        '#',VIBRANIUM_INGOT,
                        'S',OBSIDIAN_STICK);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_AXE),true,
                "##",
                        "S#",
                        "S ",
                        '#',VIBRANIUM_INGOT,
                        'S',OBSIDIAN_STICK);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_DAGGER),true,
                "I",
                        "S",
                'I',Items.VIBRANIUM_INGOT,
                'S',OBSIDIAN_STICK
                );
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_WAR_HAMMER),true,
                "III",
                        "ISI",
                        " S ",
                'I',Items.VIBRANIUM_INGOT,
                'S',OBSIDIAN_STICK
        );
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(VIBRANIUM_BATTLE_AXE),true,
                "I I",
                        "ISI",
                        " S ",
                'I',Items.VIBRANIUM_INGOT,
                'S',OBSIDIAN_STICK
                );
        CraftingManagerHelper.registerShapelessRecipe(new ItemStack(VIBRANIUM_INGOT,9),true, Blocks.blockVibranium);
        CraftingManagerHelper.registerShapelessRecipe(new ItemStack(Item.p),false, Items.DIAMOND_CHUNK,Items.DIAMOND_CHUNK,Items.DIAMOND_CHUNK,Items.DIAMOND_CHUNK);
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(BLAZE_COAL),true,
                " B ",
                        "BCB",
                        " B ",
                'B',Item.by,
                'C',Item.o
                );
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(Blocks.furnaceVibraniumIdle),true,
                "VOV",
                        "DND",
                        "VOV",
                'V',VIBRANIUM_INGOT,
                'O',Block.au,
                'D',Item.p,
                'N', Block.furnaceNetherrackIdle
                );
        RecipesFurnace.a().addSmelting(Block.cE.cF, new ItemStack(Items.DIAMOND_CHUNK));
        CraftingManagerHelper.registerShapedRecipe(new ItemStack(Items.VIBRANIUM_BOW),true,
                "NSL",
                        "SVL",
                        "NSL",
                'S',Items.OBSIDIAN_STICK,
                'V',Items.VIBRANIUM_INGOT,
                'L',Item.M,
                'N',Items.VIBRANIUM_NUGGET
                );
    }
    private static Item register(String resourceLocation,Item item,CreativeModeTab tab){
        item.setResourceLocation(item.getResourceLocationPrefix() + resourceLocation);
        item.b(resourceLocation);
        item.setCreativeTable(tab);
        return item;
    }
    private static Item register(String resourceLocation,Item item){
        item.setResourceLocation(item.getResourceLocationPrefix() +resourceLocation);
        item.b(resourceLocation);
        return item;
    }
}
