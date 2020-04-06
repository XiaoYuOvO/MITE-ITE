package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityRevenant.class)
public class EntityRevenantTrans extends EntityZombie {

    @Marker
    public EntityRevenantTrans(World world) {
        super(world);
    }

    protected void addRandomEquipment() {
        this.addRandomWeapon();
        int day = this.getWorld().getDayOfWorld();
        if (day < 128) {
            this.setBoots((new ItemStack(Item.bootsRustedIron)).randomizeForMob(this, true));
            this.setLeggings((new ItemStack(Item.legsRustedIron)).randomizeForMob(this, true));
            this.setCuirass((new ItemStack(Item.plateRustedIron)).randomizeForMob(this, true));
            this.setHelmet((new ItemStack(Item.helmetRustedIron)).randomizeForMob(this, true));
        }else{
            EntityMonsterTrans.addDefaultArmor(day,this,true);
        }
    }

    @Override
    protected void az() {
        super.az();
        this.setEntityAttribute(GenericAttributes.b, 48.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.28F);
        this.setEntityAttribute(GenericAttributes.e, 9.0D);
        this.setEntityAttribute(EntityZombie.bp, this.aD().nextDouble() * (double)0.1F);
        this.setEntityAttribute(GenericAttributes.a, 34.0D);
    }

    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.2d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10 * (float)this.aD().nextInt(18)));
        }
    }
}
