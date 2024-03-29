package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.api.ITECart;
import net.xiaoyu233.mitemod.miteite.api.ITEPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityMinecartChest.class)
public class EntityMinecartChestTrans extends EntityMinecartContainerTrans implements ITECart {
   @Unique
   private boolean isInPortal;

   public EntityMinecartChestTrans(World par1World) {
      super(par1World);
   }

   @Shadow
   public int getMinecartType() {
      return 0;
   }

   @Shadow
   public int getSizeInventory() {
      return 0;
   }

   @Unique
   @Override
   public boolean isInPortal() {
      return this.isInPortal;
   }

   public void setInPortal(int destination_dimension_id) {
      this.isInPortal = true;
      super.setInPortal(destination_dimension_id);
   }

   public boolean onEntityRightClicked(EntityPlayer player, ItemStack item_stack) {
      if (player.onServer() && !this.isInPortal()) {
         player.displayGUIChestForMinecartEntity(ReflectHelper.dyCast(this));
      }

      return true;
   }

   protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
      super.readEntityFromNBT(par1NBTTagCompound);
      this.isInPortal = false;
   }
}
