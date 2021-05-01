package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityCreeper;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityCreeper.class)
public class EntityCreeperTrans extends EntityMonster {
   @Shadow
   protected float explosionRadius;
   @Shadow
   private int fuseTime;

   public EntityCreeperTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 32, 0) : 0;
      this.explosionRadius = day >= 128 ? 5.25F + 0.1F * (float)(day - 128) / 8.0F : 5.25F;
      this.setExplosionTime(Math.max(this.getExplosionTime() * 3 - (int)((double)day * 0.3D), 20));
      this.setEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.25D);
      this.setEntityAttribute(GenericAttributes.maxHealth, 18.0D + (double)day / 14.0D);
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
   }

   public int getExplosionTime() {
      return this.fuseTime;
   }

   public void setExplosionTime(int br) {
      this.fuseTime = br;
   }
}
