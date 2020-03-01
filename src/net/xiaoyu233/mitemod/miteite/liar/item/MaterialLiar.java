package net.xiaoyu233.mitemod.miteite.liar.item;

import net.minecraft.Material;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

@Deceive(Material.class)
public class MaterialLiar {
    @Liar
    protected float durability;
    @Liar
    protected String name;

    public float getDurability(){
        return this.durability;
    }

    public String getName() {
        return this.name;
    }
}
