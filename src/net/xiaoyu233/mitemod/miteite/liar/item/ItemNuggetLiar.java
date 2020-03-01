package net.xiaoyu233.mitemod.miteite.liar.item;

import net.minecraft.ItemNugget;
import net.minecraft.Material;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import team.unknowndomain.liar.annotation.Deceive;

import static net.minecraft.Item.*;

@Deceive(ItemNugget.class)
public class ItemNuggetLiar {
    public ItemNugget getForMaterial(Material material) {
        return material == Material.copper ? copperNugget : (material == Material.silver ? silverNugget : (material == Material.gold ? bs : (material == Material.f ? ironNugget : (material == Material.mithril ? mithrilNugget : (material == Material.adamantium ? adamantiumNugget : (material == Material.ancient_metal ? ancientMetalNugget : (material == Materials.vibranium ? Items.VIBRANIUM_NUGGET : null)))))));
    }
}
