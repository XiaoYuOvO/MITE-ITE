package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.ChunkProviderUnderworld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkProviderUnderworld.class)
public class ChunkProviderUnderworldTrans {
    @Inject(method = "replaceBlocksForBiome" , at = @At("HEAD"), cancellable = true)
    public void replaceBlocksForBiome(int par1, int par2, byte[] par3ArrayOfByte, CallbackInfo ci) {
        //Remove one layer of bedrock....Might be
        ci.cancel();
    }
}
