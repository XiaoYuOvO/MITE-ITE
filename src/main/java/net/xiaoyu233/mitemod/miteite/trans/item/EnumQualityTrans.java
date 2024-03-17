package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.EnumQuality;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnumQuality.class)
public class EnumQualityTrans {
    @Inject(method = "getDurabilityModifier", cancellable = true, at = @At("HEAD"))
    public void getDurabilityModifier(CallbackInfoReturnable<Float> cir) {
        //To remove quality modifier
        cir.setReturnValue(1.0f);
    }
}
