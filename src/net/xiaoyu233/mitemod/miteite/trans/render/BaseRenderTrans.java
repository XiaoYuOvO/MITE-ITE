package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.Minecraft;
import net.minecraft.bgm;
import net.minecraft.bjo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(bgm.class)
public class BaseRenderTrans {
    @Shadow protected bjo[] textures;

    @Shadow protected bjo[] textures_glowing;

    protected boolean forceGlowOverride(){
        return false;
    }

    @Overwrite
    protected void setTexture(int index, String path, String glow_path) {
        if (this.textures[index] != null) {
            Minecraft.setErrorMessage("setTexture: texture [" + index + "] has already been set for " + this);
        } else {
            bjo texture = new bjo(path + ".png");
            this.textures[index] = texture;
            bjo texture_glowing = new bjo(glow_path + "_glow.png", false);
            if ((Minecraft.MITE_resource_pack != null && Minecraft.MITE_resource_pack.b(texture_glowing)) || this.forceGlowOverride() ) {
                this.textures_glowing[index] = texture_glowing;
            }

        }
    }
}
