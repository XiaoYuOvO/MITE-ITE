package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.ItemNugget;
import net.minecraft.Material;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemNugget.class)
public class ItemNuggetTrans {
   @Inject(method = "getForMaterial", cancellable = true, at = @At("HEAD"))
   public void injectVibraniumNugget(Material material, CallbackInfoReturnable<ItemNugget> cir) {
      if (material == Materials.vibranium){
         cir.setReturnValue(Items.VIBRANIUM_NUGGET);
      }
   }
}
