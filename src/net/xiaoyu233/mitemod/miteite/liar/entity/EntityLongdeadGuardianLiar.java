package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityLongdeadGuardian.class)
public class EntityLongdeadGuardianLiar extends EntityLongdead {
    @Stealing
    public EntityLongdeadGuardianLiar(World world) {
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
