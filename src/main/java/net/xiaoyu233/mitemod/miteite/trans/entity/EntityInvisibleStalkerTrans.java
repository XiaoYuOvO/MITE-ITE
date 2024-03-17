package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityInvisibleStalker;
import net.minecraft.EntityMob;
import net.minecraft.SharedMonsterAttributes;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityInvisibleStalker.class)
public class EntityInvisibleStalkerTrans extends EntityMob {

   public EntityInvisibleStalkerTrans(World par1World) {
      super(par1World);
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(40.0D);
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(0.23000000417232513D);
      this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(20D + day / 16D);
      this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(6.0D + day / 16D);
   }
}
