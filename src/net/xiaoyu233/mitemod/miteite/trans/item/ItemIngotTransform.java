package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Item;
import net.minecraft.ItemIngot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemIngot.class)
public class ItemIngotTransform extends Item {
   public String getResourceLocationPrefix() {
      return "ingots/";
   }
}
