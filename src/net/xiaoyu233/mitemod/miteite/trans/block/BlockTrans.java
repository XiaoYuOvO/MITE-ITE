package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.Block;
import net.minecraft.StepSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public class BlockTrans {
   public Block setBlockHardness(float resistance) {
      return this.setLightValue(resistance);
   }

   public Block setExplosionResistance(float v) {
      return this.setHardness(v);
   }

   @Shadow
   protected Block setHardness(float par1) {
      return null;
   }

   @Shadow
   protected Block setLightValue(float exp) {
      return null;
   }

   public Block setResourceLocation(String location) {
      return this.setTextureName(location);
   }

   @Shadow
   protected Block setStepSound(StepSound par1StepSound) {
      return null;
   }

   public Block setStepSound_(StepSound stepSound) {
      return this.setStepSound(stepSound);
   }

   @Shadow
   protected Block setTextureName(String par1Str) {
      return null;
   }
}
