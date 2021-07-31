package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.EnumQuality;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EnumQuality.class)
public class EnumQualityTrans {
    @Overwrite
    public float getDurabilityModifier() {
        //To remove quality modifier
        return 1.0f;
    }
}
