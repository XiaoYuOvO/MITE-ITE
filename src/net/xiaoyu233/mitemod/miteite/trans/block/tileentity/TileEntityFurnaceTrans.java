package net.xiaoyu233.mitemod.miteite.trans.block.tileentity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(TileEntityFurnace.class)
public class TileEntityFurnaceTrans extends TileEntity{
    @Link
    public int a;
    @Link
    public int heat_level = 0;
    @Link
    public int b;
    @Link
    public int c;
    @Link
    private final ItemStack[] g = new ItemStack[3];

    public int d(int par1) {
        return this.g[0] == null ? this.c * par1 / 200 : this.c * par1 / (this.g[0].b().getCookTime()/ Math.max(this.heat_level,1));

    }


    public static int getHeatLevelRequired(int item_id) {
        if (item_id == Block.cE.cF){
            return 5;
        }else if (item_id == Block.oreAdamantium.cF) {
            return 4;
        } else if (item_id == Block.oreMithril.cF) {
            return 3;
        } else if (item_id != Block.oreCopper.cF && item_id != Block.oreSilver.cF && item_id != Block.L.cF && item_id != Block.M.cF) {
            if (item_id != Block.cu.cF && item_id != Block.bW.cF && item_id != Block.aB.cF && item_id != Block.aS.cF) {
                if (item_id == Block.S.cF) {
                    return 2;
                } else if (item_id == Block.V.cF) {
                    return 2;
                } else {
                    return item_id == Block.J.cF ? 1 : 1;
                }
            } else {
                return 2;
            }
        } else {
            return 2;
        }
    }

    public void h() {
        if (super.k.I || this.a == 1 || !this.isFlooded() && !this.isSmotheredBySolidBlock()) {
            boolean var1 = this.a > 0;
            boolean var2 = false;
            if (this.a > 0) {
                --this.a;
            } else {
                this.heat_level = 0;
            }

            if (!super.k.I) {
                if (this.a == 0 && this.canSmelt(this.getFuelHeatLevel())) {
                    this.b = this.a = this.a(this.g[1]);
                    if (this.a > 0) {
                        this.heat_level = this.getItemHeatLevel(this.g[1]);
                        var2 = true;
                        if (this.g[1] != null) {
                            --this.g[1].b;
                            if (this.g[1].b == 0) {
                                Item var3 = this.g[1].b().t();
                                this.g[1] = var3 != null ? new ItemStack(var3) : null;
                            }
                        }
                    }
                }

                if (this.j() && this.canSmelt(this.heat_level)) {
                    ++this.c;
                    if (this.c == this.g[0].b().getCookTime() / heat_level) {
                        this.c = 0;
                        this.smeltItem(this.heat_level);
                        var2 = true;
                    }
                } else
                    {
                    this.c = 0;
                }

                if (var1 != this.a > 0) {
                    var2 = true;
                    BlockFurnace.a(this.a > 0, super.k, super.l, super.m, super.n);
                }
            }

            if (var2) {
                super.e();
            }

        } else {
            if (this.a > 0) {
                if (this.isFlooded()) {
                    super.k.blockFX(EnumBlockFX.steam, super.l, super.m, super.n);
                }

                BlockFurnace.a(false, super.k, super.l, super.m, super.n);
            }

            this.a = 0;
            this.c = 0;
        }
    }

    @Marker
    public boolean j() {
        return false;
    }

    @Marker
    public int a(ItemStack itemStack) {
        return 0;
    }

    @Marker
    public int getFuelHeatLevel() {
        return 0;
    }

    @Marker
    public int getItemHeatLevel(ItemStack itemStack) {
        return 0;
    }

    @Marker
    public boolean canSmelt(int heat_level) {
        return false;
    }

    @Marker
    public boolean isFlooded() {
        return false;
    }

    @Marker
    public void smeltItem(int heat_level) {

    }

    @Marker
    public boolean isSmotheredBySolidBlock() {
        return false;
    }
}
