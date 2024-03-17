package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EntityLongdeadGuardian.class)
public class EntityLongdeadGuardianTrans extends EntityLongdead {
   public EntityLongdeadGuardianTrans(World world) {
      super(world);
   }

   /**
    * @author XiaoYu233
    * @reason Move stowed item logic to Skeleton entity
    * @see EntitySkeletonTrans
    */
   @Overwrite
   public void addRandomWeapon() {
      super.addRandomWeapon();
   }

   /**
    * @author XiaoYu233
    * @reason Move stowed item logic to Skeleton entity
    * @see EntitySkeletonTrans
    */
   @Overwrite
   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
   }

   @SoftOverride
   protected void enchantEquipment(ItemStack item_stack) {
      int day = this.getWorld().getDayOfOverworld();
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double) day / 64.0D / 10.0D) {
         EnchantmentHelper.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + day / 32) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }
}
