package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

@Transform(EntityAncientBoneLord.class)
public class EntityAncientBoneLordTrans extends EntityBoneLord {
    @Marker
    public EntityAncientBoneLordTrans(World world) {
        super(world);
    }


    @Override
    protected void az() {
        super.az();

        boolean boneLordTweak = Config.ConfigEntry.BONE_LORD_TWEAK.getFrom(MITEITEMod.CONFIG);
        this.setEntityAttribute(GenericAttributes.b, 48.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.30F);
        this.setEntityAttribute(GenericAttributes.e, boneLordTweak ? 20.0d : 10.0D);
        this.setEntityAttribute(GenericAttributes.a, boneLordTweak ? 50.0D : 25.0D);
    }

    @Override
    protected void addRandomEquipment() {
        System.out.println("---Bone Lord Spawned--- : " + this.getFootPos().toString());
        this.addRandomWeapon();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64,0) : 0;
        if (day < 192) {
            this.setBoots((new ItemStack(Item.bootsAncientMetal)).randomizeForMob(this, true));
            this.setLeggings((new ItemStack(Item.legsAncientMetal)).randomizeForMob(this, true));
            this.setCuirass((new ItemStack(Item.plateAncientMetal)).randomizeForMob(this, true));
            this.setHelmet((new ItemStack(Item.helmetAncientMetal)).randomizeForMob(this, true));
        }else{
            EntityMonsterTrans.addDefaultArmor(day,this,true);
        }
    }

    protected void enchantEquipment(ItemStack item_stack) {
        if (this.aD().nextFloat() <= (0.2d + this.getWorld().getDayOfWorld() / 64d / 10)) {
            EnchantmentManager.a(this.aD(), item_stack, (int)(5.0F + (this.aD().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10 * (float)this.aD().nextInt(18)));
        }
    }

    public int getExperienceValue() {
        return Config.ConfigEntry.BONE_LORD_TWEAK.getFrom(MITEITEMod.CONFIG) ? super.getExperienceValue() * 4 : super.getExperienceValue() * 2;
    }

    @Override
    public boolean getCanSpawnHere(boolean perform_light_check) {
        if (this.getWorld().isOverworld()){
            Vec3D pos = this.getFootPos();
            return this.getWorld().isBloodMoon24HourPeriod() &&
                    this.getWorld().getDayOfWorld() >= MITEITEMod.CONFIG.get(Config.ConfigEntry.ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY) &&
                    !(this.getWorld().getClosestEntityLivingBase(this,new Class[]{EntityAncientBoneLord.class},64,false,false) instanceof EntityAncientBoneLord) &&
                    this.getWorld().getBlock(pos.getBlockX(),pos.getBlockY(),pos.getBlockZ()) != Block.F  && !this.getWorld().anySolidBlockIn(this.E) ;
        }else {
            return super.getCanSpawnHere(perform_light_check);
        }
    }
}
