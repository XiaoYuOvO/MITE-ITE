package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityBlaze;
import net.minecraft.EntityMonster;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityBlaze.class)
public class EntityBlazeTrans extends EntityMonster {
   public EntityBlazeTrans(World par1World) {
      super(par1World);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.setEntityAttribute(GenericAttributes.attackDamage).setAttribute(6.0D);
      this.setEntityAttribute(GenericAttributes.maxHealth).setAttribute(40.0D);
   }
}
