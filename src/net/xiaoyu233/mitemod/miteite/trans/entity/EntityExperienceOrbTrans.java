package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityExperienceOrb;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityExperienceOrb.class)
public class EntityExperienceOrbTrans {
   @Overwrite
   public static int getXPSplit(int par0) {
      if (par0 >= 256000) {
         return 256000;
      } else if (par0 >= 128000) {
         return 128000;
      } else if (par0 >= 64000) {
         return 64000;
      } else if (par0 >= 32000) {
         return 32000;
      } else if (par0 >= 16000) {
         return 16000;
      } else if (par0 >= 8000) {
         return 8000;
      } else if (par0 >= 4000) {
         return 4000;
      } else if (par0 >= 2477) {
         return 2477;
      } else if (par0 >= 1237) {
         return 1237;
      } else if (par0 >= 617) {
         return 617;
      } else if (par0 >= 307) {
         return 307;
      } else if (par0 >= 149) {
         return 149;
      } else if (par0 >= 73) {
         return 73;
      } else if (par0 >= 37) {
         return 37;
      } else if (par0 >= 17) {
         return 17;
      } else if (par0 >= 7) {
         return 7;
      } else {
         return par0 >= 3 ? 3 : 1;
      }
   }
}
