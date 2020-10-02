package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.Block;
import net.minecraft.Entity;
import net.minecraft.NBTTagCompound;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

@Transform(Entity.class)
public class EntityTrans extends Entity {
    private int netherrackWalkTime = 0;
    @Marker
    public EntityTrans(World par1World) {
        super(par1World);
    }

    @Override
    @Marker
    protected void a() {

    }

    public boolean isInFire() {
        if (MITEITEMod.CONFIG.get(Config.ConfigEntry.NETHERRACK_DAMAGE) && this.q.isTheNether() &&  this.q
                .doesBoundingBoxContainBlock(this.E.b(0.001D, 0.001D, 0.001D), Block.bg.cF, -1)){
            netherrackWalkTime++;
            if (netherrackWalkTime > 20){
                this.netherrackWalkTime = 0;
                return true;
            }
        }
        return (((this.q.isUnderworld() && (this.E.b <= 3.0D)) || (this.q.isTheNether() && ((this.E.b <= 1.0D) || (this.E.e >= 123.0D)))) && this.q
                .doesBoundingBoxContainBlock(this.E.b(0.001D, 0.001D, 0.001D), Block.mantleOrCore.cF, -1)) || this.q.isBoundingBoxBurning(this.E
                .e(0.001D, 0.001D, 0.001D), false);
    }

    @Override
    @Marker
    protected void a(NBTTagCompound var1) {

    }

    @Override
    @Marker
    protected void b(NBTTagCompound var1) {

    }
}
