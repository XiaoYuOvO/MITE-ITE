package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityCreeper;
import net.minecraft.EntityMob;
import net.minecraft.SharedMonsterAttributes;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityCreeper.class)
public class EntityCreeperTrans extends EntityMob {
   @Shadow
   protected float explosionRadius;
   @Shadow
   private int fuseTime;

   public EntityCreeperTrans(World par1World) {
      super(par1World);
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      this.setEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.25D);
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 20 + (double)day / 8.0D);
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 64.0D);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectInit(CallbackInfo callbackInfo){
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      this.explosionRadius = 1.25f;
      this.setExplosionTime(Math.max(this.getExplosionTime() * 3 - (int)((double)day * 0.6D), 40));
   }

   @Unique
   public int getExplosionTime() {
      return this.fuseTime;
   }

   @Unique
   public void setExplosionTime(int br) {
      this.fuseTime = br;
   }
}
