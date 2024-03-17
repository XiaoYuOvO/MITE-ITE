package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(WorldGenMinable.class)
public abstract class WorldGenMinableTrans {
   @Shadow
   private int minableBlockId;

   @Shadow public abstract int getMinVeinHeight(World world);

   @Shadow public abstract int getMaxVeinHeight(World world);

   @Inject(method = "getRandomVeinHeight", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;setErrorMessage(Ljava/lang/String;)V"), cancellable = true)
   public void wrapNetherAdamantiumSetError(World world, Random rand, CallbackInfoReturnable<Integer> cir) {
      Block block = Block.blocksList[this.minableBlockId];
      if (block == Blocks.netherAdamantiumOre){
         int min_height = this.getMinVeinHeight(world);
         int height_range = this.getMaxVeinHeight(world) - min_height + 1;
         cir.setReturnValue(min_height + (int)(rand.nextFloat() * (float)height_range));
      }
   }

   @Inject(method = "getMinVeinHeight", at = @At("HEAD"), cancellable = true)
   public void injectMinVeinHeight(World world, CallbackInfoReturnable<Integer> cir) {
      Block block = Block.blocksList[this.minableBlockId];
      if (world.isUnderworld()) {
         if (block == Block.oreAdamantium){
            cir.setReturnValue(130);
         }else cir.setReturnValue(140);
      }else if (world.isTheNether()){
         cir.setReturnValue(35);
      }
   }

   @Inject(method = "generate(Lnet/minecraft/World;Ljava/util/Random;IIIZ)Z",at = @At("HEAD"),cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
   private void injectMinableGenerate(World world, Random rand, int x, int y, int z, boolean vein_size_increases_with_depth,CallbackInfoReturnable<Boolean> callback){
      if ((world.isUnderworld() || world.isTheNether()) && y < this.getMinVeinHeight(world)){
         callback.setReturnValue(false);
         callback.cancel();
      }
   }

   @Inject(method = "getMaxVeinHeight", at = @At("HEAD"), cancellable = true)
   public void injectMaxVeinHeight(World world, CallbackInfoReturnable<Integer> cir) {
      Block block = Block.blocksList[this.minableBlockId];
      if (world.isUnderworld()) {
         if (block == Block.oreAdamantium){
            cir.setReturnValue(230);
         }
         cir.setReturnValue(225);
      } else if (world.isTheNether()){
         cir.setReturnValue(115);
      }
   }
}
