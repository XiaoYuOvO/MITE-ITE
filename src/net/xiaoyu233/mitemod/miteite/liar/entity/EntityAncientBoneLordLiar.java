package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityAncientBoneLord.class)
public class EntityAncientBoneLordLiar extends EntityBoneLord {
    @Stealing
    public EntityAncientBoneLordLiar(World world) {
        super(world);
    }


    @Override
    protected void az() {
        super.az();
            this.setEntityAttribute(GenericAttributes.b, 48.0D);
            this.setEntityAttribute(GenericAttributes.d, 0.30F);
            this.setEntityAttribute(GenericAttributes.e, 10.0D);
            this.setEntityAttribute(GenericAttributes.a, 25.0D);

    }

    @Override
    protected void addRandomEquipment() {
        this.addRandomWeapon();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        if (day < 192) {
            this.setBoots((new ItemStack(Item.bootsAncientMetal)).randomizeForMob(this, true));
            this.setLeggings((new ItemStack(Item.legsAncientMetal)).randomizeForMob(this, true));
            this.setCuirass((new ItemStack(Item.plateAncientMetal)).randomizeForMob(this, true));
            this.setHelmet((new ItemStack(Item.helmetAncientMetal)).randomizeForMob(this, true));
        }else{
            EntityMonsterLiar.addDefaultArmor(day,this,true);
        }
    }

    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.2d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10 * (float)this.aD().nextInt(18)));
        }
    }
}
