package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockStrongbox.class)
public class BlockStrongboxTrans extends Block {
    protected BlockStrongboxTrans(int par1, Material par2Material, BlockConstants constants) {
        super(par1, par2Material, constants);
    }

    @Inject(method = "a",at = @At("RETURN"))
    private void injectTextureInit(mt register, CallbackInfo callbackInfo){
        if (this.blockMaterial == Materials.vibranium) {
            this.cW = register.a("block_vibranium");
        }
    }
}
