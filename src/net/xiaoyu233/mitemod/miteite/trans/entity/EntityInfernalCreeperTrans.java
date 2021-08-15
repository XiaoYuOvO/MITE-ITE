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
      if (Configs.Entities.INFERNAL_CREEPER_BOOST.get()) {
         if (damage_source.bypassesMundaneArmor()) {
            if (this.getWorld() != null) {
               return super.getNaturalDefense(damage_source) + (float) this.getWorld().getDayOfOverworld() * 0.075F;
            }
            return super.getNaturalDefense(damage_source);
         }
         if (this.getWorld() != null) {
            return super.getNaturalDefense(damage_source) + 2.0F + (float) this.getWorld().getDayOfOverworld() * 0.075F;
         }
         return super.getNaturalDefense(damage_source) + 2.0F;
      }
      return super.getNaturalDefense(damage_source) + (damage_source.bypassesMundaneArmor() ? 0F : 2.0F);
   }

   @Inject(method = "<init>",at = @At("RETURN"))
   private void injectCtorModifyExplosion(CallbackInfo callbackInfo){
      if ((Configs.Entities.INFERNAL_CREEPER_BOOST.get())) {
         this.explosionRadius *= 2.0F;
      }
   }
}
