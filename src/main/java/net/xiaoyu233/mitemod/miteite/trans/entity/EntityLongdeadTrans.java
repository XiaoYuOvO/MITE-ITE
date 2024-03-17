package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEWorld;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLongdead.class)
public abstract class EntityLongdeadTrans extends EntitySkeletonTrans {
   @Shadow public abstract void addRandomWeapon();

   public EntityLongdeadTrans(World par1World) {
      super(par1World);
   }

   @Inject(method = "addRandomEquipment", at = @At("HEAD"), cancellable = true)
   protected void addRandomEquipment(CallbackInfo ci) {
      this.addRandomWeapon();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld(), 0) : 0;
      if (day < 96) {
         this.setBoots((new ItemStack(Item.bootsIron)).randomizeForMob(this, true));
         this.setLeggings((new ItemStack(Item.legsIron)).randomizeForMob(this, true));
         this.setCuirass((new ItemStack(Item.plateIron)).randomizeForMob(this, true));
         this.setHelmet((new ItemStack(Item.helmetIron)).randomizeForMob(this, true));
      } else {
         MonsterUtil.addDefaultArmor(day, this, true);
      }
      this.initStockedWeapon();
      ci.cancel();
   }

   @Override
   protected boolean willChangeWeapon() {
      return Configs.Entities.BONE_LORD_AND_LONGDEAD_CHANGE_WEAPON_CHANCE.get() > this.rand.nextFloat();
   }

   @Inject(method = "applyEntityAttributes", at = @At("RETURN"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 40.0D);
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth, (this.isGuardian() ? 26.0D : 15.0D) + day / 16D);
      this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.28999999165534973D);
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage, (this.isGuardian() ? 10.0D : 8.0D) + day / 20D);
   }

   protected void enchantEquipment(ItemStack item_stack) {
      int day = this.getWorld().getDayOfOverworld();
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double) day / 64.0D / 10.0D) {
         EnchantmentHelper.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + day / 48) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }

   @Shadow
   public boolean isGuardian() {
      return false;
   }
}
