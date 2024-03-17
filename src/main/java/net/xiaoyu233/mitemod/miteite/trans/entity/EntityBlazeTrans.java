package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityBlaze;
import net.minecraft.EntityMob;
import net.minecraft.SharedMonsterAttributes;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityBlaze.class)
public class EntityBlazeTrans extends EntityMob {
   public EntityBlazeTrans(World par1World) {
      super(par1World);
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(6.0D);
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
   }
}
