package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Block;
import net.minecraft.Item;
import net.minecraft.ItemBlock;
import net.minecraft.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemBlock.class)
public abstract class ItemBlockTrans extends Item{
    @Shadow public abstract Block getBlock();

    public String getItemDisplayName(ItemStack item_stack) {
        return this.getBlock().getItemDisplayName(item_stack);
    }
}
