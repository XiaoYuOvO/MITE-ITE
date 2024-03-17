package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityBoneLord.class)
public abstract class EntityBoneLordTrans extends EntitySkeletonTrans {
   @Shadow public abstract void addRandomWeapon();

   public EntityBoneLordTrans(World par1World) {
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
      boolean boneLordTweak = Configs.Entities.BONE_LORD_TWEAK.get();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 44.0D);
      this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.27000001072883606D);
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage, boneLordTweak ? 10 + day /20D : 3.5D);
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth, boneLordTweak ? 35 + day / 16D : 22.0D);
   }

   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2 + (double)this.getWorld().getDayOfOverworld() / 64.0 / 10.0) {
         EnchantmentHelper.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)((this.getRNG().nextInt(15 + this.getWorld().getDayOfOverworld() / 24) + 3) / 10) * (float)this.getRNG().nextInt(18)));
      }
   }

   @Inject(method = "getExperienceValue", at = @At("HEAD"), cancellable = true)
   public void getExperienceValue(CallbackInfoReturnable<Integer> cir) {
      cir.setReturnValue(Configs.Entities.BONE_LORD_TWEAK.get() ? super.getExperienceValue() * 6 : super.getExperienceValue() * 3);
   }
}
