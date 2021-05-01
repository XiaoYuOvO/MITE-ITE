package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntitySheep;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EntitySheep.class)
public abstract class EntitySheepTrans extends EntityLivestockTrans {
   public EntitySheepTrans(World world) {
      super(world);
   }

   @SoftOverride
   public int getBreedExp() {
      return Configs.Entities.Animals.BREED_XP_SHEEP.get();
   }
}
