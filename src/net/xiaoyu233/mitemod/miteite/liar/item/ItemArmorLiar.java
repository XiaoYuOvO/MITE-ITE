package net.xiaoyu233.mitemod.miteite.liar.item;

import net.minecraft.ItemArmor;
import net.minecraft.Material;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

@Deceive(ItemArmor.class)
public class ItemArmorLiar {
    @Liar
    private Material effective_material;
    @Liar
    private boolean is_chain_mail;

    public int getMaterialProtection() {
        int protection;
        if (this.effective_material == Material.leather) {
            protection = 2;
        } else if (this.effective_material == Material.rusted_iron) {
            protection = 6;
        } else if (this.effective_material == Material.copper) {
            protection = 7;
        } else if (this.effective_material == Material.silver) {
            protection = 7;
        } else if (this.effective_material == Material.gold) {
            protection = 6;
        } else if (this.effective_material != Material.f && this.effective_material != Material.ancient_metal) {
            if (this.effective_material == Material.mithril) {
                protection = 9;
            } else {
                if (this.effective_material == Material.adamantium) {
                    protection = 10;
                } else if (this.effective_material == Materials.vibranium){
                    protection = 15;
                }else{
                    return 0;
                }

            }
        } else {
            protection = 8;
        }

        if (this.is_chain_mail) {
            protection -= 2;
        }

        return protection;
    }
}
