package net.xiaoyu233.mitemod.miteite.trans.entity.ai;

import net.minecraft.EntityAnimal;
import net.minecraft.EntityExperienceOrb;
import net.minecraft.PathfinderGoalBreed;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PathfinderGoalBreed.class)
public class PathfinderGoalBreedTrans {
   @Shadow
   double moveSpeed;
   @Shadow
   int spawnBabyDelay;
   @Shadow
   World theWorld;
   @Shadow
   private EntityAnimal targetMate;
   @Shadow
   private EntityAnimal theAnimal;

   @Redirect(method = "spawnBaby",
           at = @At(value = "NEW",
                   target = "(Lnet/minecraft/World;DDDI)Lnet/minecraft/EntityExperienceOrb;"))
   private EntityExperienceOrb redirectSpawnEntity(World par1World, double par2, double par4, double par6, int par8){
      return new EntityExperienceOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, this.theAnimal.getBreedExp());
   }
}
