package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEMob;
import net.xiaoyu233.mitemod.miteite.util.MonsterUtil;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityZombie.class)
class EntityZombieTrans extends EntityAnimalWatcher implements ITEMob {
   @Shadow
   @Final
   protected static Attribute field_110186_bp;

   public EntityZombieTrans(World world) {
      super(world);
   }

   @Override
   protected void addRandomArmor() {
      super.addRandomArmor();
      if (this.worldObj.isUnderworld() && this.worldObj.getDayOfOverworld() < 64) {
         MonsterUtil.addDefaultArmor(64, this, true);
      }
   }

   @Inject(method = "applyEntityAttributes", at = @At("HEAD"))
   protected void applyEntityAttributes(CallbackInfo ci) {
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld() - 64, 0) : 0;
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 64.0D);
      this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.23000000417232513D);
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage, 8D + (double)day / 24.0D);
      this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 30D + (double)day / 16.0D);
      this.setEntityAttribute(field_110186_bp, this.getRNG().nextDouble() * 0.10000000149011612D);
   }

   @Override
   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.15D + (double)this.getWorld().getDayOfOverworld() / 64.0D / 10.0D) {
         EnchantmentHelper.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + this.getWorld().getDayOfOverworld() / 48) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }

   //
//      EntityDamageResult result = super.attackEntityFrom(damage);
//      if (result != null && !result.entityWasDestroyed() && result.entityWasNegativelyAffected() && damage.wasCausedByPlayer()) {
//         this.is_smart = true;
//      }
//
//      return result;

   @Override
   @Unique
   public float getChanceOfCausingFire() {
      return Math.min(0.05f + this.worldObj.getDayOfOverworld() / 800f,0.25f);
   }
}
