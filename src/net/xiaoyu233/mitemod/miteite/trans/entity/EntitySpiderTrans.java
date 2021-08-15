package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityArachnid;
import net.minecraft.EntitySpider;
import net.minecraft.GenericAttributes;
import net.minecraft.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntitySpider.class)
public class EntitySpiderTrans extends EntityArachnid {

   public EntitySpiderTrans(World par1World, float scaling) {
      super(par1World, scaling);
   }

   @Overwrite
   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfOverworld() - 32, 0) : 0;
      this.setEntityAttribute(GenericAttributes.maxHealth, this.getEntityAttributeValue(GenericAttributes.maxHealth) * 1.5D + (double)day / 24.0D);
      this.setEntityAttribute(GenericAttributes.attackDamage, this.getEntityAttributeValue(GenericAttributes.attackDamage) + (double)day / 24.0D);
      this.setEntityAttribute(GenericAttributes.followRange, 64.0D);
   }
}
