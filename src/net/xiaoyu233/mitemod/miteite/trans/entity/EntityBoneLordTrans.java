package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityBoneLord.class)
public class EntityBoneLordTrans extends EntitySkeleton {
    @Marker
    public EntityBoneLordTrans(World par1World) {
        super(par1World);
    }

    @Override
    protected void az() {
        super.az();
        this.setEntityAttribute(GenericAttributes.b, 44.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.27F);
        this.setEntityAttribute(GenericAttributes.e, 5.5D);
        this.setEntityAttribute(GenericAttributes.a, 22.0D);
    }

    @Override
    protected void addRandomEquipment() {
        this.addRandomWeapon();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        if (day < 128) {
            this.setBoots((new ItemStack(Item.bootsRustedIron)).randomizeForMob(this, true));
            this.setLeggings((new ItemStack(Item.legsRustedIron)).randomizeForMob(this, true));
            this.setCuirass((new ItemStack(Item.plateRustedIron)).randomizeForMob(this, true));
            this.setHelmet((new ItemStack(Item.helmetRustedIron)).randomizeForMob(this, true));
        }else{
            EntityMonsterTrans.addDefaultArmor(day,this,true);
        }
    }

    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.2d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10 * (float)this.aD().nextInt(18)));
        }
    }
}
