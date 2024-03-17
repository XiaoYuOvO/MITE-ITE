package net.xiaoyu233.mitemod.miteite.trans.item;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemManure.class)
public class ItemManureTrans extends Item {
   @Inject(method = "onItemRightClick", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
   public void injectHealCrops(EntityPlayer player, float partial_tick, boolean ctrl_is_down, CallbackInfoReturnable<Boolean> cir, @Local RaycastCollision rc) {
      if (rc != null && rc.isBlock()) {
         World world = rc.world;
         int x = rc.block_hit_x;
         int y = rc.block_hit_y;
         int z = rc.block_hit_z;
         ItemStack item_stack = player.getHeldItemStack();
         if (!ItemDye.tryFertilize(item_stack, world, x, y, z, rc.face_hit)) {
            cir.setReturnValue(false);
         } else {
            if (player.onServer() && !player.inCreativeMode()) {
               player.convertOneOfHeldItem(null);
            }

            if (ItemDye.suppress_standard_particle_effect) {
               ItemDye.suppress_standard_particle_effect = false;
               cir.setReturnValue(true);
            } else {
               if (player.onServer()) {
                  Block block = rc.getBlockHit();
                  if (block == Block.grass || block == Block.tilledField || block == Block.mycelium) {
                     ++y;
                  }

                  world.playAuxSFX(2005, x, y, z, 0);
               }

               if (world.getBlock(x, y, z) instanceof BlockCrops) {
                  player.triggerAchievement(AchievementList.plantDoctor);
               }

               cir.setReturnValue(true);
            }
         }
      }
   }

}
