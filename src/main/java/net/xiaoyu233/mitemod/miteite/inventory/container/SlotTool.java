package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;

public class SlotTool extends Slot {
    public SlotTool(IInventory inventory, int slot_index, int display_x, int display_y) {
        super(inventory, slot_index, display_x, display_y);
    }

    public boolean isItemValid(ItemStack par1ItemStack) {
        return par1ItemStack.getItem() instanceof ItemTool || par1ItemStack.getItem() instanceof ItemArmor;
    }
}
