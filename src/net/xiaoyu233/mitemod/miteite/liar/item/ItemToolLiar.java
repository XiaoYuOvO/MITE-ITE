package net.xiaoyu233.mitemod.miteite.liar.item;

import net.minecraft.ItemTool;
import net.minecraft.Material;
import net.minecraft.atv;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

@Deceive(ItemTool.class)
public class ItemToolLiar {
    @Liar
    private Material effective_material;

    public float getMaterialHarvestEfficiency() {
        if (this.effective_material == Material.d) {
            return 1.0F;
        } else if (this.effective_material == Material.flint) {
            return 1.25F;
        } else if (this.effective_material == Material.obsidian) {
            return 1.5F;
        } else if (this.effective_material == Material.rusted_iron) {
            return 1.25F;
        } else if (this.effective_material == Material.copper) {
            return 1.75F;
        } else if (this.effective_material == Material.silver) {
            return 1.75F;
        } else if (this.effective_material == Material.gold) {
            return 1.75F;
        } else if (this.effective_material == Material.f) {
            return 2.0F;
        } else if (this.effective_material == Material.mithril) {
            return 2.5F;
        } else if (this.effective_material == Material.adamantium) {
            return 3.0F;
        } else if (this.effective_material == Material.diamond) {
            return 2.5F;
        } else if (this.effective_material == Material.ancient_metal) {
            return 2.0F;
        } else if (this.effective_material == Materials.vibranium){
            return 3.5F;
        }else{
            atv.setErrorMessage("getMaterialHarvestEfficiency: tool material not handled");
            return 0.0F;
        }
    }
}
