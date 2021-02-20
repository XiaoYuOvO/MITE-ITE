package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.CreativeModeTab;
import net.minecraft.ItemReferencedBook;
import net.minecraft.ItemStack;
import net.minecraft.ItemWrittenBook;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.util.List;

@Transform(ItemReferencedBook.class)
public class ItemReferencedBookTrans extends ItemWrittenBook {
    public ItemReferencedBookTrans(int id) {
        super(id);
        this.setCreativeTable(CreativeModeTab.f);
    }

    public void a(int par1, CreativeModeTab par2CreativeTabs, List par3List) {
        int maxIndex = 9;
        for (int i1 = 1; i1 <= maxIndex; i1++) {
            par3List.add(this.generateBookT(i1));
        }
    }

    public ItemStack generateBookT(int index) {
        return (new ItemStack(this)).setTagCompound(ItemReferencedBook.generateBookContents(index));
    }

    public static int getXPReward(ItemStack item_stack) {
        return 0;
    }
}
