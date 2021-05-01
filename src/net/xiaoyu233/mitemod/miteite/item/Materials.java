package net.xiaoyu233.mitemod.miteite.item;

import net.minecraft.EnumEquipmentMaterial;
import net.minecraft.Material;
import net.minecraft.MaterialMapColor;
import net.minecraft.Minecraft;

public class Materials extends Material {
    public static final Materials vibranium = (Materials) new Materials(EnumEquipmentMaterials.vibranium).setMetal(false).setHarmedByLava(false).setMinHarvestLevel(6);
    public Materials(EnumEquipmentMaterial enum_crafting_material) {
        super(enum_crafting_material);
    }

    public Materials(String name, MaterialMapColor map_color) {
        super(name, map_color);
    }

    public Materials(String name) {
        super(name);
    }

    public String getName() {
        return super.name;
    }

    public float getDurability(){
        return super.durability;
    }

    public float getDamageVsEntity() {
        if (this == wood) {
            return 0.0F;
        } else if (this == flint) {
            return 1.0F;
        } else if (this == obsidian) {
            return 2.0F;
        } else if (this == rusted_iron) {
            return 2.0F;
        } else if (this == copper) {
            return 3.0F;
        } else if (this == silver) {
            return 3.0F;
        } else if (this == gold) {
            return 2.0F;
        } else if (this == iron) {
            return 4.0F;
        } else if (this == ancient_metal) {
            return 4.0F;
        } else if (this == mithril) {
            return 5.0F;
        } else if (this == adamantium) {
            return 6.0F;
        } else if (this == diamond) {
            return 4.0F;
        } else if (this == vibranium) {
            return 9.0F;
        } else {
            Minecraft.setErrorMessage("getDamageVsEntity: unhandled material " + this.name);
            return 0.0F;
        }
    }
}
