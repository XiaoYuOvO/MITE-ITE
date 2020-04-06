package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Item;
import net.minecraft.ItemIngot;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(ItemIngot.class)
public class ItemIngotTransform extends Item {
    @Override
    public String getResourceLocationPrefix() {
        return "ingots/";
    }
}
