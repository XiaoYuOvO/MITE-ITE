package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityLongdead.class)
public class EntityLongdeadLiar extends EntitySkeleton {
    @Stealing
    public EntityLongdeadLiar(World par1World) {
        super(par1World);
    }

    protected void az() {
        super.az();
        this.setEntityAttribute(GenericAttributes.b, 40.0D);
        this.setEntityAttribute(GenericAttributes.a, this.isGuardian() ? 28.0D : 16.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.29F);
        this.setEntityAttribute(GenericAttributes.e, this.isGuardian() ? 9.0D : 7.0D);
    }

    @Stealing
    protected boolean isGuardian() {
        return false;
    }

    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.15d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 48)) / 10 * (float)this.aD().nextInt(18)));
        }
    }
}
