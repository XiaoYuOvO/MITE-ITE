package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.util.BlockPos;

import java.util.List;

@Transform(TileEntityBeacon.class)
public class TileEntityBeaconTrans extends TileEntity{
    @Link
    private boolean d;
    //Beacon level
    @Link
    private int e;
    @Link
    private int f;
    @Link
    private int g;
    private BlockPos currentPos;

    public void a(NBTTagCompound par1NBTTagCompound) {
        super.a(par1NBTTagCompound);
        this.f = par1NBTTagCompound.e("Primary");
        this.g = par1NBTTagCompound.e("Secondary");
        this.e = par1NBTTagCompound.e("Levels");
        this.currentPos = new BlockPos(this.l,this.m,this.n);
    }

    public void clearAllPlayersBoost(){
        double var1 = this.e * 10 + 10;
        AxisAlignedBB var4 = AxisAlignedBB.a().a(this.l, this.m, this.n, this.l + 1, this.m + 1, this.n + 1).b(var1, var1, var1);
        for (EntityHuman entityHuman : (List<EntityHuman>)this.k.a(EntityHuman.class, var4)) {
            entityHuman.setCraftingBoostFactor(0,this.currentPos);
        }
    }

    private void u() {
        if (this.d && this.e > 0 && !this.k.I && this.f > 0) {
            byte var3 = 0;
            if (this.e >= 4 && this.f == this.g) {
                var3 = 1;
            }

            double var1 = this.e * 10 + 10;
            AxisAlignedBB var4 = AxisAlignedBB.a().a(this.l, this.m, this.n, this.l + 1, this.m + 1, this.n + 1).b(var1, var1, var1);
            var4.e = this.k.R();

            List<EntityHuman> var5 = this.k.a(EntityHuman.class, var4);
            for (EntityHuman entityHuman : var5) {
                entityHuman.c(new MobEffect(this.f, 180, var3, true));
                entityHuman.setCraftingBoostFactor(this.getCraftingBoostFactor(this.e),this.currentPos);
                entityHuman.setCraftingBoostTimer(80);
            }


            if (this.e >= 4 && this.f != this.g && this.g > 0) {
                for (EntityHuman entityHuman : var5) {
                    entityHuman.c(new MobEffect(this.g, 180, 0, true));
                }
            }
        }

    }

    private void v() {
        if (this.currentPos == null){
            this.currentPos = new BlockPos(this.l,this.m,this.n);
        }
        if (!this.k.l(this.l, this.m + 1, this.n)) {
            this.d = false;
            this.e = 0;
        } else {
            int previousLevel = this.e;
            this.d = true;
            this.e = 0;

            for(int var1 = 1; var1 <= 4; this.e = var1++) {
                int var2 = this.m - var1;
                if (var2 < 0) {
                    break;
                }

                boolean var3 = true;

                for(int var4 = this.l - var1; var4 <= this.l + var1 && var3; ++var4) {
                    for(int var5 = this.n - var1; var5 <= this.n + var1; ++var5) {
                        int var6 = this.k.a(var4, var2, var5);
                        if (var6 != Block.ca.cF && var6 != Block.aC.cF && var6 != Block.blockCopper.cF && var6 != Block.blockSilver.cF && var6 != Block.am.cF && var6 != Block.an.cF && var6 != Block.blockMithril.cF && var6 != Block.blockAdamantium.cF && var6 != Blocks.blockVibranium.cF) {
                            var3 = false;
                            break;
                        }
                    }
                }

                if (!var3) {
                    break;
                }
            }

            if (this.e == 0) {
                this.d = false;
                if (previousLevel != 0){
                    double var1 = previousLevel * 10 + 10;
                    AxisAlignedBB var4 = AxisAlignedBB.a().a(this.l, this.m, this.n, this.l + 1, this.m + 1, this.n + 1).b(var1, var1, var1);
                    for (EntityHuman entityHuman : (List<EntityHuman>)this.k.a(EntityHuman.class, var4)) {
                        entityHuman.setCraftingBoostFactor(0,this.currentPos);
                    }
                }
            }else {
                this.d(this.f);
            }
        }

    }

    @Marker
    public void d(int par1) {}

    private float getCraftingBoostFactor(int level){
        switch (level){
            case 1 :
                return 0.1f;
            case 2 :
                return 0.35f;
            case 3 :
                return 1f;
            case 4:
                return 1.75f;
            default:
                return 0;
        }
    }
}
