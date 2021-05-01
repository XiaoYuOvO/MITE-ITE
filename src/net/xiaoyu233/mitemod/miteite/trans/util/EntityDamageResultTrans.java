package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.EntityDamageResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityDamageResult.class)
public class EntityDamageResultTrans {
   @Shadow
   private boolean entity_was_destroyed;

   public void setEntity_was_destroyed(boolean entity_was_destroyed) {
      this.entity_was_destroyed = entity_was_destroyed;
   }
}
