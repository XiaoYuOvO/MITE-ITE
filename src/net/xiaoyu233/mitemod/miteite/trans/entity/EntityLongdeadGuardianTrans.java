package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EntityLongdeadGuardian.class)
public class EntityLongdeadGuardianTrans extends EntityLongdeadTrans {
   public EntityLongdeadGuardianTrans(World world) {
      super(world);
   }

   @Overwrite
   public void addRandomWeapon() {
      super.addRandomWeapon();
   }

   @Overwrite
   public void onLivingUpdate() {
      super.onLivingUpdate();
   }

   @Overwrite
   public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
   }

   @Overwrite
   public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
      super.writeEntityToNBT(par1NBTTagCompound);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
   }

   @SoftOverride
   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double)this.getWorld().getDayOfOverworld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + this.getWorld().getDayOfOverworld() / 32) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }
}
