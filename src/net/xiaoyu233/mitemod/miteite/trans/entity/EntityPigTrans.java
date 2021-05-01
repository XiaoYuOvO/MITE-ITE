package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityAgeable;
import net.minecraft.EntityPig;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EntityPig.class)
public class EntityPigTrans extends EntityLivestockTrans {
   public EntityPigTrans(World world) {
      super(world);
   }

   @Shadow
   public EntityAgeable createChild(EntityAgeable var1) {
      return null;
   }

   @SoftOverride
   public int getBreedExp() {
      return (Configs.Entities.Animals.BREED_XP_PIG.get());
   }

   @Shadow
   public void produceGoods() {
   }
}
