package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.Block;
import net.minecraft.StepSound;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(Block.class)
public class BlockTrans {

    @Marker
    protected Block d(String par1Str) {
        return null;
    }

    public Block setResourceLocation(String location){
        return this.d(location);
    }

    @Marker
    protected Block c(float par1) {
        return null;
    }
    @Marker
    protected Block a(StepSound par1StepSound) {
        return null;
    }

    @Marker
    protected Block a(float exp){
        return null;
    }

    public Block setStepSound(StepSound stepSound){
        return a(stepSound);
    }

    public Block setExplosionResistance(float v){
        return this.c(v);
    }

    public Block setBlockHardness(float resistance){
        return this.a(resistance);
    }
}
