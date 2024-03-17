package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityVampireBat.class)
public class EntityVampireBatTrans extends EntityBat {

   public EntityVampireBatTrans(World par1World) {
      super(par1World);
   }

   @Inject(
           method = {"collideWithEntity(Lnet/minecraft/Entity;)V"},
           at = @At(
                   value = "INVOKE",
                   shift = At.Shift.AFTER,
                   target = "Lnet/minecraft/EntityVampireBat;heal(FLnet/minecraft/EnumEntityFX;)V"),
           require = 1
   )
   private void injectAttack(Entity targeEntity,CallbackInfo c){
      if (this.rand.nextBoolean() && (Configs.Entities.BAT_POISON_ATTACK.get())) {
         targeEntity.getAsEntityLivingBase().addPotionEffect(new PotionEffect(Potion.poison.id, 480, 0));
      }
   }

}
