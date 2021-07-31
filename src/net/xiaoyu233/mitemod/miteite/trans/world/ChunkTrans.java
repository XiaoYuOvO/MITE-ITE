package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.Chunk;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Random;

@Mixin(Chunk.class)
public class ChunkTrans {
    @Redirect(method = "<init>(Lnet/minecraft/World;[BII)V",at = @At(value = "INVOKE",target = "Ljava/util/Random;nextInt(I)I"),
            slice = @Slice(
                    from = @At(value = "FIELD",target = "Lnet/minecraft/ChunkProviderUnderworld;bedrock_strata_4_bump_noise:[D"),
                    to = @At(value = "FIELD",target = "Lnet/minecraft/Block;mantleOrCore:Lnet/minecraft/BlockMantleOrCore;",ordinal = 1)))
    private int redirectRandomBedrockNum(Random caller,int bound){
        //In fact this is the height of the mantle blocks
        return Configs.WorldGen.Underworld.UNDERWORLD_MANTLE_BLOCK_OFFSET.get();
    }
}
