package net.xiaoyu233.mitemod.miteite.entity;

import net.minecraft.BlockStairs;
import net.minecraft.EntityLiving;
import net.minecraft.IAnimals;
import net.minecraft.World;
import org.spongepowered.asm.mixin.SoftOverride;

public class EntityRideMarker extends EntityLiving implements IAnimals {
    public EntityRideMarker(World par1World) {
        super(par1World);
        this.setSize(0.0F, 0.0F);
    }

    public void onUpdate() {
        if (this.riddenByEntity == null) {
            this.setDead();
        }

        if (!(super.worldObj.getBlock(super.getBlockPosX(), super.getBlockPosY(), super.getBlockPosZ()) instanceof BlockStairs)) {
            this.setDead();
        }
    }

    @SoftOverride
    public boolean canCatchFire() {
        return false;
    }
}
