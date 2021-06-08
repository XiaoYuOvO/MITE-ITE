package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityBoneLord.class)
public class EntityBoneLordTrans extends EntitySkeletonTrans {
   public EntityBoneLordTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void addRandomEquipment() {
      this.addRandomWeapon();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64, 0) : 0;
      if (day < 96) {
         this.setBoots((new ItemStack(Item.bootsRustedIron)).randomizeForMob(this, true));
         this.setLeggings((new ItemStack(Item.legsRustedIron)).randomizeForMob(this, true));
         this.setCuirass((new ItemStack(Item.plateRustedIron)).randomizeForMob(this, true));
         this.setHelmet((new ItemStack(Item.helmetRustedIron)).randomizeForMob(this, true));
      } else {
         MonsterUtil.addDefaultArmor(day, this, true);
      }
      this.initStockedWeapon();

   }

   @Override
   protected boolean willChangeWeapon() {
      return Configs.Entities.BONE_LORD_AND_LONGDEAD_CHANGE_WEAPON_CHANCE.get() > this.rand.nextFloat();
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      boolean boneLordTweak = Configs.Entities.BONE_LORD_TWEAK.get();
      this.setEntityAttribute(GenericAttributes.followRange, 44.0D);
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.27000001072883606D);
      this.setEntityAttribute(GenericAttributes.attackDamage, boneLordTweak ? 10.0D : 5.5D);
      this.setEntityAttribute(GenericAttributes.maxHealth, boneLordTweak ? 44.0D : 22.0D);
   }

   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double)this.getWorld().getDayOfWorld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)((this.getRNG().nextInt(15 + this.getWorld().getDayOfWorld() / 24) + 3) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }

   @Overwrite
   public int getExperienceValue() {
      return Configs.Entities.BONE_LORD_TWEAK.get() ? super.getExperienceValue() * 6 : super.getExperienceValue() * 3;
   }
}
