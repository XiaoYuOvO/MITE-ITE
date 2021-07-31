package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.Block;
import net.minecraft.Item;
import net.minecraft.RecipeIngots;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RecipeIngots.class)
public class RecipeIngotsTrans {
   @Shadow
   @Mutable
   private static final Object[][] block_or_crystal_dismantling_based_items;
   @Shadow
   @Mutable
   private static final Object[][] ingot_based_items;
   @Shadow
   @Mutable
   private static final Object[][] nugget_based_items;
   @Shadow
   @Mutable
   private static final Object[][] shard_3x3_based_items;

   static {
      ingot_based_items = new Object[][]{{Item.ingotCopper, Item.ingotSilver, Item.ingotGold, Item.ingotIron, Item.ingotMithril, Item.ingotAdamantium, Item.ingotAncientMetal}, {Item.copperNugget, Item.silverNugget, Item.goldNugget, Item.ironNugget, Item.mithrilNugget, Item.adamantiumNugget, Item.ancientMetalNugget, null, 9}};
      nugget_based_items = new Object[][]{{Item.copperNugget, Item.silverNugget, Item.goldNugget, Item.ironNugget, Item.mithrilNugget, Item.adamantiumNugget, Item.ancientMetalNugget, null}, {Item.ingotCopper, Item.ingotSilver, Item.ingotGold, Item.ingotIron, Item.ingotMithril, Item.ingotAdamantium, Item.ingotAncientMetal, new String[]{"???", "???", "???"}}};
      shard_3x3_based_items = new Object[][]{{Item.shardDiamond, Item.shardNetherQuartz, Item.shardGlass, Item.shardObsidian, null}, {Item.diamond, Item.netherQuartz, Block.thinGlass, Block.obsidian, new String[]{"???", "???", "???"}}};
      block_or_crystal_dismantling_based_items = new Object[][]{{Item.diamond, Item.netherQuartz, Block.thinGlass, Block.obsidian}, {Item.shardDiamond, Item.shardNetherQuartz, Item.shardGlass, Item.shardObsidian, null, 9}};
   }
}
