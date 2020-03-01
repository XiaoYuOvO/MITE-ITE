package net.xiaoyu233.mitemod.miteite.liar.render;

import net.minecraft.*;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(RenderEarthElemental.class)
public class RenderEarthElementalLiar extends bgu {
    @Stealing
    public RenderEarthElementalLiar() {
        super(new ModelInvisibleStalker(), 0.5F);
    }

    @Override
    protected void setTextures() {
        this.setTexture(0, "stone", false);
        this.setTexture(1, "stone", true);
        this.setTexture(2, "obsidian", false);
        this.setTexture(3, "obsidian", true);
        this.setTexture(4, "netherrack", false);
        this.setTexture(5, "netherrack", true);
        this.setTexture(6, "end_stone", false);
        this.setTexture(7, "end_stone", true);
        this.setTexture(8, "clay", false);
        this.setTexture(9, "clay", false);
        this.setTexture(10, "plank", false);
    }
    @Stealing
    private void setTexture(int index, String name, boolean magma) {}

    protected bjo getTextures(EntityEarthElemental earth_elemental) {
        int type = earth_elemental.getType();
        if (type == EntityEarthElemental.CLAY_NORMAL) {
            return super.textures[8];
        } else {
            return type == EntityEarthElemental.CLAY_HARDENED ? super.textures[10] : super.textures[type == EntityEarthElemental.STONE_NORMAL ? 0 : (type == EntityEarthElemental.STONE_MAGMA ? 1 : (type == EntityEarthElemental.OBSIDIAN_NORMAL ? 2 : (type == EntityEarthElemental.OBSIDIAN_MAGMA ? 3 : (type == EntityEarthElemental.NETHERRACK_NORMAL ? 4 : (type == EntityEarthElemental.NETHERRACK_MAGMA ? 5 : (type == EntityEarthElemental.END_STONE_NORMAL ? 6 : (type == 5 ? 10 : 7)))))))];
        }
    }
}
