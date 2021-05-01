package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.CreativeModeTab;
import net.minecraft.ItemReferencedBook;
import net.minecraft.ItemStack;
import net.minecraft.ItemWrittenBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(ItemReferencedBook.class)
public class ItemReferencedBookTrans extends ItemWrittenBook {
   public ItemReferencedBookTrans(int id) {
      super(id);
   }

   @Overwrite
   public static int getXPReward(ItemStack item_stack) {
      return 0;
   }

   public void a(int par1, CreativeModeTab par2CreativeTabs, List par3List) {
      int maxIndex = 9;

      for(int i1 = 1; i1 <= maxIndex; ++i1) {
         par3List.add(this.generateBookT(i1));
      }

   }

   public ItemStack generateBookT(int index) {
      return (new ItemStack(this)).setTagCompound(ItemReferencedBook.generateBookContents(index));
   }
}
