package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityMinecartAbstract;
import net.minecraft.EntityMinecartContainer;
import net.minecraft.ItemStack;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityMinecartContainer.class)
public abstract class EntityMinecartContainerTrans extends EntityMinecartAbstract {
   @Shadow
   private boolean dropContentsWhenDead;
   @Shadow
   private ItemStack[] minecartContainerItems;

   public EntityMinecartContainerTrans(World par1World) {
      super(par1World);
   }

   public void setDead() {
      super.setDead();
   }
}
