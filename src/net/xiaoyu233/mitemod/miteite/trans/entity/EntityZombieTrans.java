package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityZombie.class)
class EntityZombieTrans extends EntityAnimalWatcher {

    @Link
    protected static IAttribute bp;

    @Marker
    public EntityZombieTrans(World world) {
        super(world);
    }
    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.15d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 48)) / 10 * (float)this.aD().nextInt(18)));
        }
    }
    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        this.setEntityAttribute(GenericAttributes.b, 64.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.23F);
        this.setEntityAttribute(GenericAttributes.e, 10D + day / 48D);
        this.setEntityAttribute(GenericAttributes.a, 50.0D + day / 14D);
        this.setEntityAttribute(bp, this.aD().nextDouble() * (double)0.1F);
    }
}
