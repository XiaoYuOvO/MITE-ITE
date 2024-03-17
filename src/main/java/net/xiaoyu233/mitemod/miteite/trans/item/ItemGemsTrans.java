package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.EntityPlayer;
import net.minecraft.Item;
import net.minecraft.ItemRock;
import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRock.class)
public class ItemGemsTrans extends Item {
   @ModifyConstant(method = "getExperienceValueWhenSacrificed", constant = @Constant(intValue = 25))
   private static int modifyLapisExp(int constant){
      return 40;
   }

   @ModifyConstant(method = "getExperienceValueWhenSacrificed", constant = @Constant(intValue = 50))
   private static int modifyQuartzExp(int constant){
      return Configs.Item.QUARTZ_EXP.get();
   }

   @ModifyConstant(method = "getExperienceValueWhenSacrificed", constant = @Constant(intValue = 500))
   private static int modifyDiamondExp(int constant){
      return Configs.Item.DIAMOND_EXP.get();
   }

   @ModifyConstant(method = "getExperienceValueWhenSacrificed", constant = @Constant(intValue = 500))
   private static int modifyEmeraldExp(int constant){
      return Configs.Item.EMERALD_EXP.get();
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
}
