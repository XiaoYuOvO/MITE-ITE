package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityZombie.class)
class EntityZombieLiar extends EntityAnimalWatcher {

    @Liar
    protected static IAttribute bp;

    @Stealing
    public EntityZombieLiar(World world) {
        super(world);
    }
    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.15d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 48)) / 10 * (float)this.aD().nextInt(18)));
        }
    }
    protected void az() {
        super.az();
        this.setEntityAttribute(GenericAttributes.b, 40.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.23F);
        this.setEntityAttribute(GenericAttributes.e, 10.0D);
        this.setEntityAttribute(GenericAttributes.a, 40.0D);
        this.setEntityAttribute(bp, this.aD().nextDouble() * (double)0.1F);
    }
}
