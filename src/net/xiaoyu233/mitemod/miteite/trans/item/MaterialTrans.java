package net.xiaoyu233.mitemod.miteite.trans.item;

import net.minecraft.Material;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(Material.class)
public class MaterialTrans {
    @Link
    protected int min_harvest_level;
    @Link
    protected float durability;
    @Link
    protected String name;

    public float getDurability(){
        return this.durability;
    }

    public String getName() {
        return this.name;
    }

    public int getMinHarvestLevel() {
        return min_harvest_level;
    }
}
