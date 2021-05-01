package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityChicken;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EntityChicken.class)
public abstract class EntityChickenTrans extends EntityLivestockTrans {
   public EntityChickenTrans(World world) {
      super(world);
   }

   @SoftOverride
   public int getBreedExp() {
      return Configs.Entities.Animals.BREED_XP_CHICKEN.get();
   }

}
