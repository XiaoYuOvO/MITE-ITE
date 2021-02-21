package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.Collections;
import java.util.Random;

@Transform(EntityVillager.class)
public class EntityVillagerTrans extends EntityAgeable implements IMerchant, NPC {
    @Link
    private float bA;
    @Link
    private MerchantRecipeList bu;

    @Marker
    public EntityVillagerTrans(World par1World) {
        super(par1World);
    }

    private void q(int par1) {
        if (this.bu != null) {
            this.bA = MathHelper.c((float)this.bu.size()) * 0.2F;
        } else {
            this.bA = 0.0F;
        }

        MerchantRecipeList var2;
        var2 = new MerchantRecipeList();
        int var6;
        label51:
        switch(this.bT()) {
            case 0:
                a(var2, Item.V.cv, this.ab, this.p(0.9F));
                forEmeraldShard(var2, Block.ag.cF, this.ab, this.p(0.5F));
                a(var2, Item.bm.cv, this.ab, this.p(0.5F));
                a(var2, Item.aX.cv, this.ab, this.p(0.4F));
                b(var2, Item.W.cv, this.ab, this.p(0.9F));
                b(var2, Item.bh.cv, this.ab, this.p(0.3F));
                b(var2, Item.l.cv, this.ab, this.p(0.3F));
                b(var2, Item.be.cv, this.ab, this.p(0.3F));
                b(var2, Item.bg.cv, this.ab, this.p(0.3F));
                b(var2, Item.k.cv, this.ab, this.p(0.3F));
                b(var2, Item.bn.cv, this.ab, this.p(0.3F));
                b(var2, Item.arrowFlint.cv, this.ab, this.p(0.5F));
                if (this.ab.nextFloat() < this.p(0.5F)) {
                    var2.add(new MerchantRecipe(new ItemStack(Block.K, 4), new ItemStack(Item.bJ), new ItemStack(Item.ar.cv, 4 + this.ab.nextInt(2), 0)));
                }
                break;
            case 1:
                a(var2, Item.aM.cv, this.ab, this.p(0.8F));
                a(var2, Item.aN.cv, this.ab, this.p(0.8F));
                a(var2, Item.bI.cv, this.ab, this.p(0.3F));
                b(var2, Block.as.cF, this.ab, this.p(0.8F));
                b(var2, Block.R.cF, this.ab, this.p(0.2F));
                b(var2, Item.aS.cv, this.ab, this.p(0.2F));
                b(var2, Item.aU.cv, this.ab, this.p(0.2F));
                if (this.ab.nextFloat() < this.p(0.07F)) {
                    Enchantment var8 = Enchantment.c[this.ab.nextInt(Enchantment.c.length)];
                    int var10 = MathHelper.a(this.ab, 1, var8.getNumLevels());
                    ItemStack var11 = Item.bY.a(new EnchantmentInstance(var8, var10));
                    var6 = 2 + this.ab.nextInt(5 + var10 * 10) + 3 * var10;
                    var2.add(new MerchantRecipe(new ItemStack(Item.aN), new ItemStack(Item.bJ, var6), var11));
                }
                break;
            case 2:
                b(var2, Item.bC.cv, this.ab, this.p(0.3F));
                b(var2, Item.aE.cv, this.ab, this.p(0.4F));
                b(var2, Block.bi.cF, this.ab, this.p(0.3F));
                int[] var3 = new int[]{Item.swordCopper.cv, Item.s.cv, Item.plateCopper.cv, Item.ag.cv, Item.axeCopper.cv, Item.j.cv, Item.pickaxeCopper.cv, Item.i.cv};
                int[] var4 = var3;
                int var5 = var3.length;
                var6 = 0;

                while(true) {
                    if (var6 >= var5) {
                        break label51;
                    }

                    int var7 = var4[var6];
                    if (this.ab.nextFloat() < this.p(0.05F)) {
                        var2.add(new MerchantRecipe(new ItemStack(var7, 1, 0), new ItemStack(Item.bJ, 2 + this.ab.nextInt(3), 0), EnchantmentManager.a(this.ab, new ItemStack(var7, 1, 0), 5 + this.ab.nextInt(15))));
                    }

                    ++var6;
                }
            case 3:
                a(var2, Item.o.cv, this.ab, this.p(0.7F));
                a(var2, Item.q.cv, this.ab, this.p(0.5F));
                a(var2, Item.r.cv, this.ab, this.p(0.5F));
                b(var2, Item.s.cv, this.ab, this.p(0.5F));
                b(var2, Item.j.cv, this.ab, this.p(0.3F));
                b(var2, Item.i.cv, this.ab, this.p(0.5F));
                b(var2, Item.h.cv, this.ab, this.p(0.2F));
                b(var2, Item.R.cv, this.ab, this.p(0.2F));
                b(var2, Item.af.cv, this.ab, this.p(0.2F));
                b(var2, Item.ag.cv, this.ab, this.p(0.2F));
                b(var2, Item.ah.cv, this.ab, this.p(0.2F));
                b(var2, Item.ai.cv, this.ab, this.p(0.2F));
                b(var2, Item.pickaxeCopper.cv, this.ab, this.p(0.5F));
                b(var2, Item.shovelCopper.cv, this.ab, this.p(0.2F));
                b(var2, Item.axeCopper.cv, this.ab, this.p(0.3F));
                b(var2, Item.hoeCopper.cv, this.ab, this.p(0.2F));
                b(var2, Item.daggerCopper.cv, this.ab, this.p(0.5F));
                b(var2, Item.swordCopper.cv, this.ab, this.p(0.5F));
                b(var2, Item.daggerIron.cv, this.ab, this.p(0.5F));
                b(var2, Item.helmetCopper.cv, this.ab, this.p(0.2F));
                b(var2, Item.plateCopper.cv, this.ab, this.p(0.2F));
                b(var2, Item.legsCopper.cv, this.ab, this.p(0.2F));
                b(var2, Item.bootsCopper.cv, this.ab, this.p(0.2F));
                b(var2, Item.helmetChainCopper.cv, this.ab, this.p(0.1F));
                b(var2, Item.plateChainCopper.cv, this.ab, this.p(0.1F));
                b(var2, Item.legsChainCopper.cv, this.ab, this.p(0.1F));
                b(var2, Item.bootsChainCopper.cv, this.ab, this.p(0.1F));
                b(var2, Item.helmetChainIron.cv, this.ab, this.p(0.1F));
                b(var2, Item.plateChainIron.cv, this.ab, this.p(0.1F));
                b(var2, Item.legsChainIron.cv, this.ab, this.p(0.1F));
                b(var2, Item.bootsChainIron.cv, this.ab, this.p(0.1F));
                break;
            case 4:
                a(var2, Item.o.cv, this.ab, this.p(0.7F));
                a(var2, Item.as.cv, this.ab, this.p(0.5F));
                a(var2, Item.bk.cv, this.ab, this.p(0.5F));
                a(var2, Item.lambchopRaw.cv, this.ab, this.p(0.5F));
                b(var2, Item.aC.cv, this.ab, this.p(0.1F));
                b(var2, Item.Y.cv, this.ab, this.p(0.3F));
                b(var2, Item.aa.cv, this.ab, this.p(0.3F));
                b(var2, Item.X.cv, this.ab, this.p(0.3F));
                b(var2, Item.Z.cv, this.ab, this.p(0.3F));
                b(var2, Item.at.cv, this.ab, this.p(0.3F));
                b(var2, Item.bl.cv, this.ab, this.p(0.3F));
                b(var2, Item.lambchopCooked.cv, this.ab, this.p(0.3F));
        }

        if (var2.isEmpty()) {
            a(var2, Item.r.cv, this.ab, 1.0F);
        }

        Collections.shuffle(var2);
        if (this.bu == null) {
            this.bu = new MerchantRecipeList();
        }

        for(int var9 = 0; var9 < par1 && var9 < var2.size(); ++var9) {
            this.bu.a((MerchantRecipe)var2.get(var9));
        }

    }

    private static void forEmeraldShard(MerchantRecipeList par0MerchantRecipeList, int par1, Random par2Random, float par3) {
        if (par2Random.nextFloat() < par3) {
            par0MerchantRecipeList.add(new MerchantRecipe(new ItemStack(par1, MITEITEMod.CONFIG.get(Config.ConfigEntry.VILLAGER_WOOL_TO_EMERALD_SHARD_COUNT)), new ItemStack(Item.shardEmerald,MITEITEMod.CONFIG.get(Config.ConfigEntry.VILLAGER_WOOL_TO_EMERALD_SHARD_SHARD_COUNT))));
        }
    }

    @Marker
    private static ItemStack a(int par0, Random par1Random) {
        return new ItemStack(1);
    }
    @Marker
    private int bT() {
        return 0;
    }

    @Marker
    private float p(float par1) {
        return 0;
    }

    @Marker
    private static void a(MerchantRecipeList par0MerchantRecipeList, int par1, Random par2Random, float par3) {}

    @Marker
    private static void b(MerchantRecipeList par0MerchantRecipeList, int par1, Random par2Random, float par3) {

    }

    @Override
    @Marker
    public EntityAgeable a(EntityAgeable var1) {
        return null;
    }

    @Override
    @Marker
    public void a_(EntityHuman entityHuman) {

    }

    @Override
    @Marker
    public EntityHuman m_() {
        return null;
    }

    @Override
    @Marker
    public MerchantRecipeList b(EntityHuman entityHuman) {
        return null;
    }

    @Override
    @Marker
    public void a(MerchantRecipeList merchantRecipeList) {

    }

    @Override
    @Marker
    public void a(MerchantRecipe merchantRecipe) {

    }

    @Override
    @Marker
    public void a_(ItemStack itemStack) {

    }
}
