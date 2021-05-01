package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.DamageSource;
import net.minecraft.EntityInfernalCreeper;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityInfernalCreeper.class)
public class EntityInfernalCreeperTrans extends EntityCreeperTrans {
   public EntityInfernalCreeperTrans(World world) {
      super(world);
      this.setSize(this.width* getScale(), this.height* getScale());
   }

   @Overwrite
   public float getNaturalDefense(DamageSource damage_source) {
      return Configs.Entities.INFERNAL_CREEPER_BOOST.get() ? super.getNaturalDefense(damage_source) + (damage_source.bypassesMundaneArmor() ? 0.0F : 2.0F) + (this.getWorld() != null ? (float)this.getWorld().getDayOfWorld() * 0.075F : 0.0F) : super.getNaturalDefense(damage_source) + (damage_source.bypassesMundaneArmor() ? 0.0F : 2.0F);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectCtorModifyExplosion(CallbackInfo callbackInfo){
      if ((Configs.Entities.INFERNAL_CREEPER_BOOST.get())) {
         int day = this.getWorld() != null ? this.getWorld().getDayOfWorld() : 0;
         this.explosionRadius *= 3.0F;
         if (day > 256) {
            this.explosionRadius *= 2.0F;
         }
         this.setExplosionTime(Math.max(this.getExplosionTime() * 3 - (int)((double)day * 0.3D), 20));
      }
   }
}
