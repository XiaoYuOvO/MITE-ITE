package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.CreativeModeTab;
import net.minecraft.Item;
import net.minecraft.ItemStack;
import net.minecraft.Material;

public class ItemBlazeCoalPowder extends Item {
    public ItemBlazeCoalPowder(int par1) {
        super(par1, Material.coal, "blaze_coal_powder");
        this.setMaxStackSize(16);
        this.setCreativeTab(CreativeModeTab.tabMaterials);
    }
    public int getBurnTime(ItemStack item_stack) {
        return 6800;
    }

    public int getHeatLevel(ItemStack item_stack) {
        return 5;
    }
}
