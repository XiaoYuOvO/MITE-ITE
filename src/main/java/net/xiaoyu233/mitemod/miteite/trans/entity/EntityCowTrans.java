package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityCow;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EntityCow.class)
public abstract class EntityCowTrans extends EntityLivestockTrans {

   public EntityCowTrans(World world) {
      super(world);
   }

   @SoftOverride
   public int getBreedExp() {
      return Configs.Entities.Animals.BREED_XP_COW.get();
   }
}
