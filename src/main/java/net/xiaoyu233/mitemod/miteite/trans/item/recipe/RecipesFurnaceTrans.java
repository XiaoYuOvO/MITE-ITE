package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(FurnaceRecipes.class)
public class RecipesFurnaceTrans {
   @Shadow
   private final Map smeltingList = new HashMap();

   @Inject(method = "doesSmeltingRecipeExistFor", at = @At("HEAD"), cancellable = true)
   public void doesSmeltingRecipeExistFor(ItemStack input_item_stack, CallbackInfoReturnable<Boolean> cir) {
       if (input_item_stack.isBlock()) {
          int blockID = input_item_stack.getItemAsBlock().getBlock().blockID;
          if (blockID == Block.blockIron.blockID || blockID == Block.blockMithril.blockID) {
              cir.setReturnValue(true);
          }
       }
   }

   //TODO Add furnace recipe add/modify API in Loader
   public ItemStack getSmeltingResult(ItemStack input_item_stack, int heat_level) {
      if (input_item_stack == null) {
         return null;
      } else {
         int input_item_id = input_item_stack.itemID;
         if (heat_level == -1) {
            return (ItemStack)this.smeltingList.get(input_item_id);
         } else {
            ItemStack result_item_stack;
            if (input_item_id == Block.sand.blockID) {
               result_item_stack = (heat_level != 1 || input_item_stack.stackSize >= 4) && input_item_stack.stackSize >= 4 ? new ItemStack(heat_level == 1 ? Block.sandStone : Block.glass) : null;
            } else if (input_item_id == Block.blockIron.blockID) {
               result_item_stack = heat_level >= 3 && input_item_stack.stackSize >= Configs.Item.Recipes.IRON_BLOCK_COUNT_TO_MITHRIL.get() ? new ItemStack(Item.ingotMithril) : null;
            } else if (input_item_id == Block.blockMithril.blockID) {
               result_item_stack = heat_level >= 4 && input_item_stack.stackSize >= Configs.Item.Recipes.MITHRIL_BLOCK_COUNT_TO_ADAMANTIUM.get() ? new ItemStack(Item.ingotAdamantium) : null;
            } else {
               result_item_stack = (ItemStack)this.smeltingList.get(input_item_id);
            }

            return heat_level < TileEntityFurnace.getHeatLevelRequired(input_item_stack.itemID) ? null : result_item_stack;
         }
      }
   }


}
