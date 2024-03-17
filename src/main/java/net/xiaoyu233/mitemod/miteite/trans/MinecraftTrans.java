package net.xiaoyu233.mitemod.miteite.trans;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.Minecraft;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public class MinecraftTrans {

    @ModifyExpressionValue(method = "getVersionDescriptor", at = @At(value = "CONSTANT", args = "stringValue=1.6.4-MITE"))
    private static String getVersionDescriptor(String constant) {
        return constant + "-ITE-" + Constant.MITE_ITE_VERSION + " modded by XiaoYu233 ";
    }
}
