package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityLongdeadGuardian.class)
public class EntityLongdeadGuardianTrans extends EntityLongdead {
    @Marker
    public EntityLongdeadGuardianTrans(World world) {
        super(world);
    }

    @Override
    protected void az() {
        super.az();
    }

    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.2d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 32)) / 10 * (float)this.aD().nextInt(18)));
        }
    }
}
