package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.ItemRock;
import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRock.class)
public class ItemGemsTrans extends Item {
   @Overwrite
   public static int getExperienceValueWhenSacrificed(ItemStack item_stack) {
      Item item = item_stack.getItem();
      if (item == Item.dyePowder && item_stack.getItemSubtype() == 4) {
         return 40;
      } else {
         return item == netherQuartz ? 35 : (item == emerald ? 250 : (item == diamond ? Configs.Item.DIAMOND_EXP.get() : 0));
      }
   }

   @Inject(
           method = "onItemRightClick(Lnet/minecraft/EntityPlayer;Lnet/minecraft/ItemStack;FZ)Z",
           at = @At(value = "INVOKE",
                   shift = At.Shift.BEFORE,
                   target = "Lnet/minecraft/EntityPlayer;causeBreakingItemEffect(Lnet/minecraft/Item;I)V"),
           cancellable = true)
   private static void injectNetherQuartzLimit(EntityPlayer player, ItemStack item_stack,float partial_tick,boolean ctrl_is_down,CallbackInfoReturnable<Boolean> callbackInfoReturnable){
      if (item_stack.getItem() == netherQuartz && player.getExperienceLevel() >= (Configs.Item.QUARTZ_MAX_EXP_LEVEL.get())) {
         callbackInfoReturnable.setReturnValue(false);
         callbackInfoReturnable.cancel();
      }
   }

//   @Overwrite
//   public static boolean onItemRightClick(EntityPlayer player, ItemStack item_stack, float partial_tick, boolean ctrl_is_down) {
//      int xp_value = getExperienceValueWhenSacrificed(item_stack);
//      if (xp_value < 1) {
//         return false;
//      } else {
//         if (player.onServer()) {
//
//
//            player.causeBreakingItemEffect(item_stack.getItem(), player.bn.currentItem);
//            player.convertOneOfHeldItem((ItemStack)null);
//            player.s(xp_value);
//         } else {
//            player.bobItem();
//         }
//
//         return true;
//      }
//   }
}
