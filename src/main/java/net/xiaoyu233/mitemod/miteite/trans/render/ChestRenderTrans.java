package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.TileEntity;
import net.minecraft.TileEntityChest;
import net.minecraft.TileEntityChestRenderer;
import net.minecraft.TileEntitySpecialRenderer;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityChestRenderer.class)
public class ChestRenderTrans extends TileEntitySpecialRenderer {
    @Override
    @Shadow
    public void renderTileEntityAt(TileEntity tileEntity, double v, double v1, double v2, float v3) {

    }

    @Inject(
            method = {"renderTileEntityChestAt(Lnet/minecraft/TileEntityChest;DDDF)V"},
            slice = {@Slice(
                    from = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/TileEntityChestRenderer;RES_CHRISTMAS_SINGLE:Lnet/minecraft/ResourceLocation;",
                            opcode = 178
                    ),
                    to = @At(
                            value = "FIELD",
                            target = "Lnet/minecraft/TileEntityChestRenderer;largeChestModel:Lnet/minecraft/ModelChest;",
                            opcode = 180
                    )
            )},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/TileEntityChestRenderer;bindTexture(Lnet/minecraft/ResourceLocation;)V",
                    shift = At.Shift.AFTER
            )}
    )
    private void injectGetVibraniumTexture(TileEntityChest par1TileEntityChest, double par2, double par4, double par6, float par8,CallbackInfo callbackInfo){
        if (par1TileEntityChest.getBlockMaterial() == Materials.vibranium) {
            this.bindTexture(Constant.RES_VIBRANIUM_SINGLE);
        }
    }
}
