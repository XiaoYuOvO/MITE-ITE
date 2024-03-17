package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityAgeable;
import net.minecraft.EntityAnimal;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.api.ITEAnimal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityAnimal.class)
public abstract class EntityAnimalTrans extends EntityAgeable implements ITEAnimal {
   public EntityAnimalTrans(World par1World) {
      super(par1World);
   }

   @Override
   public int getBreedExp() {
      return this.getRNG().nextInt(7) + 1;
   }
}
