package net.xiaoyu233.mitemod.miteite.trans.util;

import net.minecraft.EntityDamageResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityDamageResult.class)
public interface EntityDamageResultAccessor {
   @Accessor
   void setEntity_was_destroyed(boolean entity_was_destroyed);
}
