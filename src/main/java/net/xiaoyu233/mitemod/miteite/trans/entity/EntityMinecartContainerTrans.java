package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityMinecart;
import net.minecraft.EntityMinecartContainer;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityMinecartContainer.class)
public abstract class EntityMinecartContainerTrans extends EntityMinecart {
    public EntityMinecartContainerTrans(World par1World) {
      super(par1World);
   }

}
