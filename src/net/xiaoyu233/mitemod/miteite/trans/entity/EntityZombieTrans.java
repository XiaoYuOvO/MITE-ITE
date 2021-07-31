package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityZombie.class)
class EntityZombieTrans extends EntityAnimalWatcher {
   @Shadow
   @Final
   protected static IAttribute field_110186_bp;

   public EntityZombieTrans(World world) {
      super(world);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64, 0) : 0;
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.23000000417232513D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 8D + (double)day / 24.0D);
      this.setEntityAttribute(GenericAttributes.maxHealth, 30D + (double)day / 16.0D);
      this.setEntityAttribute(field_110186_bp, this.getRNG().nextDouble() * 0.10000000149011612D);
   }

   @Override
   protected float getChanceOfCausingFire() {
      return Math.min(0.05f + this.worldObj.getDayOfWorld() / 800f,0.25f);
   }

   //
//      EntityDamageResult result = super.attackEntityFrom(damage);
//      if (result != null && !result.entityWasDestroyed() && result.entityWasNegativelyAffected() && damage.wasCausedByPlayer()) {
//         this.is_smart = true;
//      }
//
//      return result;

   @Override
   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.15D + (double)this.getWorld().getDayOfWorld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + this.getWorld().getDayOfWorld() / 48) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }
}
