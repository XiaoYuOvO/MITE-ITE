package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

@Transform(EntityFishingHook.class)
public abstract class EntityFishingHookTrans extends Entity {
    @Link
    private boolean h;
    @Link
    public int a;
    @Link
    public EntityHuman b;
    @Link
    private int au;
    @Link
    public Entity c;

    @Marker
    public EntityFishingHookTrans(World par1World) {
        super(par1World);
    }

    public int c() {
        if (this.q.I) {
            return 0;
        } else {
            byte var1 = 0;
            if (this.c != null) {
                double var2 = this.b.u - this.u;
                double var4 = this.b.v - this.v;
                double var6 = this.b.w - this.w;
                double var8 = MathHelper.a(var2 * var2 + var4 * var4 + var6 * var6);
                double var10 = 0.1D;
                Entity var10000 = this.c;
                var10000.x += var2 * var10;
                var10000 = this.c;
                var10000.y += var4 * var10 + (double)MathHelper.a(var8) * 0.08D;
                var10000 = this.c;
                var10000.z += var6 * var10;
                var1 = 3;
            } else if (this.au > 0) {
                EntityItem var13 = new EntityItem(this.q, this.u, this.v, this.w, new ItemStack(this.getFishType()));
                double var3 = this.b.u - this.u;
                double var5 = this.b.v - this.v;
                double var7 = this.b.w - this.w;
                double var9 = MathHelper.a(var3 * var3 + var5 * var5 + var7 * var7);
                double var11 = 0.1D;
                var13.x = var3 * var11;
                var13.y = var5 * var11 + (double)MathHelper.a(var9) * 0.08D;
                var13.z = var7 * var11;
                this.q.d(var13);
                this.b.a(StatisticList.B, 1);
                this.b.q.d(new EntityExperienceOrb(this.b.q, this.b.u, this.b.v + 0.5D, this.b.w + 0.5D, (this.ab.nextInt(6) + 1) * MITEITEMod.CONFIG.get(Config.ConfigEntry.FISHING_XP_SCALE)));
                var1 = 1;
//                if (DedicatedServer.tournament_type == EnumTournamentType.score) {
//                    DedicatedServer.getOrCreateTournamentStanding(this.b).caught_a_fish = true;
//                    DedicatedServer.updateTournamentScoreOnClient(this.b, true);
//                }

                int worm_index = this.b.bn.getHotbarSlotContainItem(Item.wormRaw);
                if (worm_index >= 0) {
                    this.b.bn.decrementSlotStackSize(worm_index);
                }
            }

            if (this.h) {
                var1 = 2;
            }

            this.x();
            this.b.bM = null;
            return var1;
        }
    }

    @Marker
    private int getFishType() {
        return 0;
    }
}
