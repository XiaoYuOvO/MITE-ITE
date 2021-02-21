package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(wa.class)
public class SlotMerchantTrans extends Slot {
    @Link
    private InventoryMerchant a;
    @Link
    private IMerchant d;
    @Marker
    public SlotMerchantTrans(IInventory inventory, int slot_index, int display_x, int display_y) {
        super(inventory, slot_index, display_x, display_y);
    }

    public void a(EntityHuman par1EntityPlayer, ItemStack par2ItemStack) {
        if (!par1EntityPlayer.q.I){

            this.b(par2ItemStack);
            MerchantRecipe var3 = this.a.i();
            if (var3 != null) {
                ItemStack var4 = this.a.a(0);
                ItemStack var5 = this.a.a(1);
                if (this.a(var3, var4, var5) || this.a(var3, var5, var4)) {
                    this.d.a(var3);
                    if (var4 != null && var4.b <= 0) {
                        var4 = null;
                    }

                    if (var5 != null && var5.b <= 0) {
                        var5 = null;
                    }

                    this.a.a(0, var4);
                    this.a.a(1, var5);
                }
            }
        }
    }
    @Marker
    private boolean a(MerchantRecipe par1MerchantRecipe, ItemStack par2ItemStack, ItemStack par3ItemStack) {
        return false;
    }
}
