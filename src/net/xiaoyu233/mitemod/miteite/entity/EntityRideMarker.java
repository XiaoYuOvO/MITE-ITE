package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.BlockStairs;
import net.minecraft.EntityInsentient;
import net.minecraft.IAnimal;
import net.minecraft.World;

public class EntityRideMarker extends EntityInsentient implements IAnimal {
    public EntityRideMarker(World par1World) {
        super(par1World);
        this.a(0.0f,0.0f);
    }

    @Override
    public void l_() {
        if (this.n == null){
            this.x();
        }
        if (!(super.q.getBlock(super.getBlockPosX(),super.getBlockPosY(),super.getBlockPosZ()) instanceof BlockStairs)){
            this.x();
        }
    }

    @Override
    public boolean canCatchFire() {
        return false;
    }
}
