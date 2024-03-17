package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITESkeleton;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityAncientBoneLord.class)
public class EntityAncientBoneLordTrans extends EntityBoneLord {
   public EntityAncientBoneLordTrans(World world) {
      super(world);
   }

   @Inject(method = "addRandomEquipment", at = @At("HEAD"), cancellable = true)
   protected void addRandomEquipment(CallbackInfo ci) {
      this.addRandomWeapon();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld() - 16, 0) : 0;
      if (day < 96) {
         this.setBoots(new ItemStack(Item.bootsAncientMetal).randomizeForMob(this, true));
         this.setLeggings(new ItemStack(Item.legsAncientMetal).randomizeForMob(this, true));
         this.setCuirass(new ItemStack(Item.plateAncientMetal).randomizeForMob(this, true));
         this.setHelmet(new ItemStack(Item.helmetAncientMetal).randomizeForMob(this, true));
      } else {
         MonsterUtil.addDefaultArmor(day, this, true);
      }

      ((ITESkeleton) this).initStockedWeapon();
      ci.cancel();
   }

   @Inject(method = "applyEntityAttributes", at = @At("HEAD"), cancellable = true)
   protected void applyEntityAttributes(CallbackInfo ci) {
      super.applyEntityAttributes();
      boolean boneLordTweak = Configs.Entities.BONE_LORD_TWEAK.get();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 48.0);
      this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.3F);
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage, boneLordTweak ? 13.0 + (double)day / 20.0 : 8.0);
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth, boneLordTweak ? 40.0 + (double)day / 12.0 : 25.0);
      ci.cancel();
   }

   @Override
   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2 + (double)this.getWorld().getDayOfOverworld() / 64.0 / 10.0) {
         EnchantmentHelper.addRandomEnchantment(
                 this.getRNG(),
                 item_stack,
                 (int)(5.0F + (float)((this.getRNG().nextInt(15 + this.getWorld().getDayOfOverworld() / 24) + 3) / 10) * (float)this.getRNG().nextInt(18))
         );
      }
   }

   public boolean getCanSpawnHere(boolean perform_light_check) {
      if (!this.getWorld().isOverworld()) {
         return super.getCanSpawnHere(perform_light_check);
      } else {
         Vec3 pos = this.getFootPos();
         return this.getWorld().isBloodMoon24HourPeriod()
                 && this.getWorld().getDayOfOverworld() >= Configs.GameMechanics.MobSpawning.ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY.get()
                 && !(
                 this.getWorld().getClosestEntityLivingBase(this, new Class[]{EntityAncientBoneLord.class}, 64.0, false, false) instanceof EntityAncientBoneLord
         )
                 && this.getWorld().getBlock(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ()) != Block.waterMoving
                 && !this.getWorld().anySolidBlockIn(this.boundingBox);
      }
   }

   @Inject(method = "getExperienceValue", at = @At("HEAD"), cancellable = true)
   public void injectExpVal(CallbackInfoReturnable<Integer> cir) {
      cir.setReturnValue(Configs.Entities.BONE_LORD_TWEAK.get() ? super.getExperienceValue() * 4 : super.getExperienceValue() * 2);
   }
}
