package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Block;
import net.minecraft.Item;
import net.minecraft.RecipeIngots;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(RecipeIngots.class)
public class RecipeIngotsTrans {
    @Link
    private static final Object[][] ingot_based_items;
    @Link
    private static final Object[][] nugget_based_items;
    @Link
    private static final Object[][] shard_3x3_based_items;
    @Link
    private static final Object[][] block_or_crystal_dismantling_based_items;
    static {
        ingot_based_items = new Object[][]{{Item.ingotCopper, Item.ingotSilver, Item.r, Item.q, Item.ingotMithril, Item.ingotAdamantium, Item.ingotAncientMetal}, {Item.copperNugget, Item.silverNugget, Item.bs, Item.ironNugget, Item.mithrilNugget, Item.adamantiumNugget, Item.ancientMetalNugget, null, 9}};
        nugget_based_items = new Object[][]{{Item.copperNugget, Item.silverNugget, Item.bs, Item.ironNugget, Item.mithrilNugget, Item.adamantiumNugget, Item.ancientMetalNugget, null}, {Item.ingotCopper, Item.ingotSilver, Item.r, Item.q, Item.ingotMithril, Item.ingotAdamantium, Item.ingotAncientMetal, new String[]{"???", "???", "???"}}};
        shard_3x3_based_items = new Object[][]{{Item.shardDiamond, Item.shardNetherQuartz, Item.shardGlass, Item.shardObsidian, null}, { Item.p, Item.cb, Block.bv, Block.au, new String[]{"???", "???", "???"}}};
        block_or_crystal_dismantling_based_items = new Object[][]{{Item.p, Item.cb, Block.bv, Block.au}, {Item.shardDiamond, Item.shardNetherQuartz, Item.shardGlass, Item.shardObsidian, null, 9}};
    }
}
