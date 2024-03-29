package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityPig;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EntityPig.class)
public class EntityPigTrans extends EntityLivestockTrans {
   public EntityPigTrans(World world) {
      super(world);
   }

   @SoftOverride
   public int getBreedExp() {
      return (Configs.Entities.Animals.BREED_XP_PIG.get());
   }

}
