package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityLongdead.class)
public class EntityLongdeadTrans extends EntitySkeletonTrans {
   public EntityLongdeadTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void addRandomEquipment() {
      this.addRandomWeapon();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64, 0) : 0;
      if (day < 96) {
         this.setBoots((new ItemStack(Item.bootsChainAncientMetal)).randomizeForMob(this, true));
         this.setLeggings((new ItemStack(Item.legsChainAncientMetal)).randomizeForMob(this, true));
         this.setCuirass((new ItemStack(Item.plateChainAncientMetal)).randomizeForMob(this, true));
         this.setHelmet((new ItemStack(Item.helmetChainAncientMetal)).randomizeForMob(this, true));
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
      this.setEntityAttribute(GenericAttributes.followRange, 40.0D);
      this.setEntityAttribute(GenericAttributes.maxHealth, this.isGuardian() ? 26.0D : 14.0D);
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.28999999165534973D);
      this.setEntityAttribute(GenericAttributes.attackDamage, this.isGuardian() ? 10.0D : 8.0D);
   }

   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double)this.getWorld().getDayOfWorld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + this.getWorld().getDayOfWorld() / 48) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }

   @Shadow
   protected boolean isGuardian() {
      return false;
   }
}
