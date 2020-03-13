package net.xiaoyu233.mitemod.miteite.liar.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

import java.util.List;

@Deceive(ItemBow.class)
public class ItemBowLiar extends Item {
    @Liar
    private final Material reinforcement_material;

    public ItemBowLiar(int id, Material reinforcement_material) {
        super(id, Material.d, "bows/" + reinforcement_material.getName() + "/");
        if (reinforcement_material != null && reinforcement_material != Material.d) {
            this.addMaterial(reinforcement_material);
        }

        this.reinforcement_material = reinforcement_material;
        this.d(1);
        this.e(reinforcement_material == Materials.vibranium ? 512 : (reinforcement_material == Material.mithril ? 128 : (reinforcement_material == Material.ancient_metal ? 64 : 32)));
        this.a(CreativeModeTab.j);
        this.setSkillsetThatCanRepairThis(reinforcement_material.isMetal() ? Skill.ARCHERY.getID() + Skill.BLACKSMITHING.getID() : Skill.ARCHERY.getID());
    }

    public void addInformation(ItemStack item_stack, EntityHuman player, List info, boolean extended_info, Slot slot) {
        if (extended_info && this.reinforcement_material.isMetal()) {
            int bonus = this.reinforcement_material == Material.mithril ? 25 :(this.reinforcement_material == Materials.vibranium ? 75 : 10);
            info.add("");
            info.add(EnumChatFormat.j + Translator.getFormatted("item.tooltip.velocityBonus", bonus));
        }

        super.addInformation(item_stack, player, info, extended_info, slot);
    }

}
