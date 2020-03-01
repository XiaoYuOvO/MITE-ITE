package net.xiaoyu233.mitemod.miteite.liar.item;

import net.minecraft.Item;
import net.minecraft.ItemIngot;
import team.unknowndomain.liar.annotation.Deceive;

@Deceive(ItemIngot.class)
public class ItemIngotLiar extends Item {
    @Override
    public String getResourceLocationPrefix() {
        return "ingots/";
    }
}
