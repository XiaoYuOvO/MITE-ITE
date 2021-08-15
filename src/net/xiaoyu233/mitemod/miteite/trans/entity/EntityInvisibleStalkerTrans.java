package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityInvisibleStalker;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityInvisibleStalker.class)
public class EntityInvisibleStalkerTrans extends EntityMonster {

   public EntityInvisibleStalkerTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? this.getWorld().getDayOfOverworld() : 0;
      this.getEntityAttribute(GenericAttributes.followRange).setAttribute(40.0D);
      this.getEntityAttribute(GenericAttributes.movementSpeed).setAttribute(0.23000000417232513D);
      this.getEntityAttribute(GenericAttributes.maxHealth).setAttribute(20D + day / 16D);
      this.getEntityAttribute(GenericAttributes.attackDamage).setAttribute(6.0D + day / 16D);
   }
}
