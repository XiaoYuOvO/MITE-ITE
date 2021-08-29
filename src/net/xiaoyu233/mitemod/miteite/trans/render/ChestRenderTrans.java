package net.xiaoyu233.mitemod.miteite.trans.render;

import jdk.internal.org.objectweb.asm.Opcodes;
import net.minecraft.TileEntity;
import net.minecraft.TileEntityChest;
import net.minecraft.biv;
import net.minecraft.bje;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(biv.class)
public class ChestRenderTrans extends bje {
    @Override
    @Shadow
    public void a(TileEntity tileEntity, double v, double v1, double v2, float v3) {

    }

    @Inject(method = "a(Lnet/minecraft/TileEntityChest;DDDF)V",
            slice = @Slice(from = @At(value = "FIELD",target = "Lnet/minecraft/biv;f:Lnet/minecraft/bjo;",opcode = Opcodes.GETSTATIC),
                    to = @At(value = "FIELD",target = "Lnet/minecraft/biv;i:Lnet/minecraft/bbd;",opcode = Opcodes.GETFIELD)),
    at = @At(value = "INVOKE",target = "Lnet/minecraft/biv;a(Lnet/minecraft/bjo;)V",shift = At.Shift.AFTER))
    private void injectGetVibraniumTexture(TileEntityChest par1TileEntityChest, double par2, double par4, double par6, float par8,CallbackInfo callbackInfo){
        if (par1TileEntityChest.getBlockMaterial() == Materials.vibranium) {
            this.a(Constant.RES_VIBRANIUM_SINGLE);
        }
    }
}
