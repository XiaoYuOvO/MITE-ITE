package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.BlockStairs;
import net.minecraft.EntityInsentient;
import net.minecraft.IAnimal;
import net.minecraft.World;

public class EntityRideMarker extends EntityInsentient implements IAnimal {
    public EntityRideMarker(World par1World) {
        super(par1World);
        this.setSize(0.0f,0.0f);
    }

    @Override
    public void onUpdate() {
        if (this.riddenByEntity == null) {
            this.setDead();
        }

        if (!(super.worldObj.getBlock(super.getBlockPosX(), super.getBlockPosY(), super.getBlockPosZ()) instanceof BlockStairs)) {
            this.setDead();
        }

    }

    @Override
    public boolean canCatchFire() {
        return false;
    }
}
