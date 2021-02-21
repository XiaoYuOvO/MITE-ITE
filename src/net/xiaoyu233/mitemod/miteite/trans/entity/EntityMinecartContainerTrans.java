package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityMinecartAbstract;
import net.minecraft.EntityMinecartContainer;
import net.minecraft.ItemStack;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityMinecartContainer.class)
public class EntityMinecartContainerTrans extends EntityMinecartAbstract {
    @Link
    private ItemStack[] a;
    @Link
    private boolean b;
    @Marker
    public EntityMinecartContainerTrans(World par1World) {
        super(par1World);
    }

    @Override
    @Marker
    public int l() {
        return 0;
    }

    public void x() {
        super.x();
    }
}
