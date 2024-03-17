package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityArachnid;
import net.minecraft.EntitySpider;
import net.minecraft.SharedMonsterAttributes;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntitySpider.class)
public class EntitySpiderTrans extends EntityArachnid {

   public EntitySpiderTrans(World par1World, float scaling) {
      super(par1World, scaling);
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld() - 32, 0) : 0;
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth, this.getEntityAttributeValue(SharedMonsterAttributes.maxHealth) * 1.5D + (double)day / 24.0D);
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage, this.getEntityAttributeValue(SharedMonsterAttributes.attackDamage) + (double)day / 24.0D);
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 64.0D);
   }
}
