package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Item;
import net.minecraft.ItemNugget;
import net.minecraft.Material;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ItemNugget.class)
public class ItemNuggetTrans {
   @Overwrite
   public ItemNugget getForMaterial(Material material) {
      return material == Material.copper ? Item.copperNugget : (material == Material.silver ? Item.silverNugget : (material == Material.gold ? Item.goldNugget : (material == Material.iron ? Item.ironNugget : (material == Material.mithril ? Item.mithrilNugget : (material == Material.adamantium ? Item.adamantiumNugget : (material == Material.ancient_metal ? Item.ancientMetalNugget : (material == Materials.vibranium ? Items.VIBRANIUM_NUGGET : null)))))));
   }
}
