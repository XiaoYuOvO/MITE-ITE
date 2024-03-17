package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.DamageSource;
import net.minecraft.EntityInfernalCreeper;
import net.minecraft.World;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityInfernalCreeper.class)
public class EntityInfernalCreeperTrans extends EntityCreeperTrans {
   public EntityInfernalCreeperTrans(World world) {
      super(world);
      this.setSize(this.width* getScale(), this.height* getScale());
   }

   @Inject(method = "getNaturalDefense", at = @At("HEAD"), cancellable = true)
   public void getNaturalDefense(DamageSource damage_source, CallbackInfoReturnable<Float> cir) {
      if (Configs.Entities.INFERNAL_CREEPER_BOOST.get()) {
         float addition = 0;
         if (!damage_source.bypassesMundaneArmor()) {
            addition = 2.0F;
         }
          if (this.getWorld() != null) {
              cir.setReturnValue(super.getNaturalDefense(damage_source) + (float) this.getWorld().getDayOfOverworld() * 0.075F + addition);
          } else {
              cir.setReturnValue(super.getNaturalDefense(damage_source) + addition);
          }
      }
   }

   @Inject(
           method = {"<init>(Lnet/minecraft/World;)V"},
           at = {@At("RETURN")}
   )
   private void injectCtorModifyExplosion(CallbackInfo callbackInfo) {
      if (Configs.Entities.INFERNAL_CREEPER_BOOST.get()) {
         this.explosionRadius *= 2.0F;
      }
   }
}
