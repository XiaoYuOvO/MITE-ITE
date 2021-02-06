package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityAgeable;
import net.minecraft.EntityCow;
import net.minecraft.EntityLivestock;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

@Transform(EntityCow.class)
public class EntityCowTrans extends EntityLivestock {
    @Marker
    public EntityCowTrans(World world) {
        super(world);
    }

    @Override
    public int getBreedExp() {
        return MITEITEMod.CONFIG.get(Config.ConfigEntry.BREED_XP_COW);
    }

    @Marker
    @Override
    public EntityAgeable a(EntityAgeable var1) {
        return null;
    }

    @Marker
    @Override
    public void produceGoods() {

    }
}
