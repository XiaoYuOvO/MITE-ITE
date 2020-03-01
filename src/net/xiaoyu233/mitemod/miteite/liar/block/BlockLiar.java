package net.xiaoyu233.mitemod.miteite.liar.block;

import net.minecraft.Block;
import net.minecraft.StepSound;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(Block.class)
public class BlockLiar{

    @Stealing
    protected Block d(String par1Str) {
        return null;
    }

    public Block setResourceLocation(String location){
        return this.d(location);
    }

    @Stealing
    protected Block c(float par1) {
        return null;
    }
    @Stealing
    protected Block a(StepSound par1StepSound) {
        return null;
    }

    @Stealing
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
