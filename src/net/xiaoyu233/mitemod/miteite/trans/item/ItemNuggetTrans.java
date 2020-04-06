package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.ItemNugget;
import net.minecraft.Material;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;

import static net.minecraft.Item.*;

@Transform(ItemNugget.class)
public class ItemNuggetTrans {
    public ItemNugget getForMaterial(Material material) {
        return material == Material.copper ? copperNugget : (material == Material.silver ? silverNugget : (material == Material.gold ? bs : (material == Material.f ? ironNugget : (material == Material.mithril ? mithrilNugget : (material == Material.adamantium ? adamantiumNugget : (material == Material.ancient_metal ? ancientMetalNugget : (material == Materials.vibranium ? Items.VIBRANIUM_NUGGET : null)))))));
    }
}
