package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.CreativeTabs;
import net.minecraft.ItemEditableBook;
import net.minecraft.ItemReferencedBook;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemReferencedBook.class)
public class ItemReferencedBookTrans extends ItemEditableBook {
   public ItemReferencedBookTrans(int id) {
      super(id);
   }

   @Inject(method = "getXPReward", at = @At("HEAD"), cancellable = true)
   private static void getXPReward(ItemStack item_stack, CallbackInfoReturnable<Integer> cir) {
      cir.setReturnValue(0);
   }

   public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      int maxIndex = 9;

      for(int i1 = 1; i1 <= maxIndex; ++i1) {
         par3List.add(this.generateBookT(i1));
      }
   }

   @Unique
   public ItemStack generateBookT(int index) {
      return (new ItemStack(this)).setTagCompound(ItemReferencedBook.generateBookContents(index));
   }
}
