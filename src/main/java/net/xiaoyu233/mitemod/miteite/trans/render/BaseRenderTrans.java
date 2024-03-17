package net.xiaoyu233.mitemod.miteite.trans.render;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.Render;
import net.minecraft.ResourceLocation;
import net.xiaoyu233.mitemod.miteite.api.ITERenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public class BaseRenderTrans implements ITERenderer {
    @Shadow
    protected ResourceLocation[] textures;
    @Shadow
    protected ResourceLocation[] textures_glowing;

    public boolean forceGlowOverride() {
        return false;
    }

    @Inject(method = "setTexture(ILjava/lang/String;Ljava/lang/String;)V", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/ResourceLocation;<init>(Ljava/lang/String;Z)V"))
    private void captureGlowingAndSet(int index, String path, String glow_path, CallbackInfo ci, @Local(ordinal = 0, name = "texture_glowing") ResourceLocation texture_glowing){
        if (this.forceGlowOverride()) {
            this.textures_glowing[index] = texture_glowing;
        }
    }
}
