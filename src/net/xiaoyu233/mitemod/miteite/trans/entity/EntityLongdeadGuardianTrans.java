package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EnchantmentManager;
import net.minecraft.EntityLongdeadGuardian;
import net.minecraft.ItemStack;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

@Mixin(EntityLongdeadGuardian.class)
public class EntityLongdeadGuardianTrans extends EntityLongdeadTrans {
   public EntityLongdeadGuardianTrans(World world) {
      super(world);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
   }
   @SoftOverride
   protected void enchantEquipment(ItemStack item_stack) {
      if ((double)this.getRNG().nextFloat() <= 0.2D + (double)this.getWorld().getDayOfWorld() / 64.0D / 10.0D) {
         EnchantmentManager.addRandomEnchantment(this.getRNG(), item_stack, (int)(5.0F + (float)(this.getRNG().nextInt(15 + this.getWorld().getDayOfWorld() / 32) / 10) * (float)this.getRNG().nextInt(18)));
      }

   }
}
