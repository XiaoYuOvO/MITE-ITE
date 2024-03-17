package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.Block;
import net.minecraft.Item;
import net.minecraft.RecipesIngots;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipesIngots.class)
public class RecipeIngotsTrans {
   @Final
   @Shadow
   @Mutable
   private static Object[][] block_or_crystal_dismantling_based_items;
   @Final
   @Shadow
   @Mutable
   private static Object[][] shard_3x3_based_items;

   @Inject(method = "<clinit>", at = @At("RETURN"))
   //TODO Make only inject?
   private static void injectModifyEmeraldShardRecipe(CallbackInfo ci) {
      shard_3x3_based_items = new Object[][]{{Item.shardDiamond, Item.shardNetherQuartz, Item.shardGlass, Item.shardObsidian, null}, {Item.diamond, Item.netherQuartz, Block.thinGlass, Block.obsidian, new String[]{"???", "???", "???"}}};
      block_or_crystal_dismantling_based_items = new Object[][]{{Item.diamond, Item.netherQuartz, Block.thinGlass, Block.obsidian}, {Item.shardDiamond, Item.shardNetherQuartz, Item.shardGlass, Item.shardObsidian, null, 9}};
   }
}
