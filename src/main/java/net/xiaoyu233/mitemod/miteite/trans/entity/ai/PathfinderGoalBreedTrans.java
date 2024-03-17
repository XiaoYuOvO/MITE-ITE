package net.xiaoyu233.mitemod.miteite.trans.entity.ai;

import net.minecraft.EntityAIMate;
import net.minecraft.EntityAnimal;
import net.minecraft.EntityXPOrb;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.api.ITEAnimal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityAIMate.class)
public class PathfinderGoalBreedTrans {
   @Shadow
   World theWorld;
   @Shadow
   private EntityAnimal theAnimal;

   @Redirect(method = "spawnBaby",
           at = @At(value = "NEW",
                   target = "(Lnet/minecraft/World;DDDI)Lnet/minecraft/EntityXPOrb;"))
   private EntityXPOrb redirectSpawnEntity(World par1World, double par2, double par4, double par6, int par8){
      return new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, this.theAnimal.getBreedExp());
   }
}
