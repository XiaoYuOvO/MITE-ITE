package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityBoneLord.class)
public class EntityBoneLordLiar extends EntitySkeleton {
    @Stealing
    public EntityBoneLordLiar(World par1World) {
        super(par1World);
    }

    @Override
    protected void az() {
        super.az();
        this.setEntityAttribute(GenericAttributes.b, 44.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.27F);
        this.setEntityAttribute(GenericAttributes.e, 6.5D);
        this.setEntityAttribute(GenericAttributes.a, 25.0D);
    }

    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.2d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10 * (float)this.aD().nextInt(18)));
        }
    }
}
