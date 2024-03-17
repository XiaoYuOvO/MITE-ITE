package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Material;
import net.xiaoyu233.mitemod.miteite.api.ITEMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Material.class)
public class MaterialTrans implements ITEMaterial {
   @Shadow
   protected float durability;
   @Shadow
   protected int min_harvest_level;
   @Shadow
   protected String name;

   @Override
   @Unique
   public float getDurability() {
      return this.durability;
   }

   @Override
   @Unique
   public int getMinHarvestLevel() {
      return this.min_harvest_level;
   }

   @Override
   @Unique
   public String getName() {
      return this.name;
   }
}
