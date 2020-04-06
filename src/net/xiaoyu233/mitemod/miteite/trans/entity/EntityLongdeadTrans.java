package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityLongdead.class)
public class EntityLongdeadTrans extends EntitySkeleton {
    @Marker
    public EntityLongdeadTrans(World par1World) {
        super(par1World);
    }

    protected void az() {
        super.az();
        this.setEntityAttribute(GenericAttributes.b, 40.0D);
        this.setEntityAttribute(GenericAttributes.a, this.isGuardian() ? 26.0D : 14.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.29F);
        this.setEntityAttribute(GenericAttributes.e, this.isGuardian() ? 10.0D : 8.0D);
    }

    protected void addRandomEquipment() {
        this.addRandomWeapon();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        if (day < 160) {
            this.setBoots((new ItemStack(Item.bootsChainAncientMetal)).randomizeForMob(this, true));
            this.setLeggings((new ItemStack(Item.legsChainAncientMetal)).randomizeForMob(this, true));
            this.setCuirass((new ItemStack(Item.plateChainAncientMetal)).randomizeForMob(this, true));
            this.setHelmet((new ItemStack(Item.helmetChainAncientMetal)).randomizeForMob(this, true));
        }else{
            EntityMonsterTrans.addDefaultArmor(day,this,true);
        }
    }

    @Marker
    protected boolean isGuardian() {
        return false;
    }

    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.2d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 48)) / 10 * (float)this.aD().nextInt(18)));
        }
    }
}
