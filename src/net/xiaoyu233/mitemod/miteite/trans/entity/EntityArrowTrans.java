package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.item.Items;

@Transform(EntityArrow.class)
public class EntityArrowTrans extends Entity {
    @Link
    private Entity c;
    @Link
    private int j;

    @Marker
    public EntityArrowTrans(World par1World) {
        super(par1World);
    }

    @Override
    @Marker
    protected void b(NBTTagCompound nbtTagCompound) {

    }

    @Override
    @Marker
    protected void a(NBTTagCompound nbtTagCompound) {

    }

    @Override
    @Marker
    protected void a() {

    }

    public void c(double par1, double par3, double par5, float velocity, float par8) {
        ItemStack launcher = this.getLauncher();
        if (launcher != null && launcher.b() == Items.VIBRANIUM_BOW && this.c instanceof EntityHuman) {
            velocity *= 1.75F;
        }

        if (launcher != null && launcher.b() == Item.bowMithril && this.c instanceof EntityHuman) {
            velocity *= 1.25F;
        }

        if (launcher != null && launcher.b() == Item.bowAncientMetal && this.c instanceof EntityHuman) {
            velocity *= 1.1F;
        }

        float var9 = MathHelper.a(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= var9;
        par3 /= var9;
        par5 /= var9;
        par1 += super.ab.nextGaussian() * (double)(super.ab.nextBoolean() ? -1 : 1) * 1.856746317E-314D * (double)par8;
        par3 += super.ab.nextGaussian() * (double)(super.ab.nextBoolean() ? -1 : 1) * 1.856746317E-314D * (double)par8;
        par5 += super.ab.nextGaussian() * (double)(super.ab.nextBoolean() ? -1 : 1) * 1.856746317E-314D * (double)par8;
        par1 *= velocity;
        par3 *= velocity;
        par5 *= velocity;
        super.x = par1;
        super.y = par3;
        super.z = par5;
        float var10 = MathHelper.a(par1 * par1 + par5 * par5);
        super.C = super.A = (float)(Math.atan2(par1, par5) * 0.0D / 6.984873503E-315D);
        super.D = super.B = (float)(Math.atan2(par3, var10) * 0.0D / 6.984873503E-315D);
        this.j = 0;
    }

    @Marker
    private ItemStack getLauncher() {
        return null;
    }
}
