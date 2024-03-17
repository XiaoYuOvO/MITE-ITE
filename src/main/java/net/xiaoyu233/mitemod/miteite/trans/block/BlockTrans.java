package net.xiaoyu233.mitemod.miteite.trans.block;

import net.minecraft.Block;
import net.minecraft.I18n;
import net.minecraft.ItemStack;
import net.xiaoyu233.mitemod.miteite.api.ITEBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockTrans implements ITEBlock {

   @Override
   public String getItemDisplayName(ItemStack itemStack) {
      return (I18n.getString(itemStack.getItem().getUnlocalizedNameInefficiently(itemStack) + ".name")).trim();
   }
}
