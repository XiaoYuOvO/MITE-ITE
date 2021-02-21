package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

@Transform(EntityMinecartChest.class)
public class EntityMinecartChestTrans extends EntityMinecartContainer {
    @Link
    private ItemStack[] a;
    @Link
    private boolean b;
    private boolean isInPortal;
    @Marker
    public EntityMinecartChestTrans(World par1World) {
        super(par1World);
    }

    @Override
    @Marker
    public int l() {
        return 0;
    }

    public boolean onEntityRightClicked(EntityHuman player, ItemStack item_stack) {
        if (player.onServer() && !this.isInPortal()) {
            player.displayGUIChestForMinecartEntity(dyCast(this));
        }

        return true;
    }

    @Override
    protected void a(NBTTagCompound par1NBTTagCompound) {
        super.a(par1NBTTagCompound);
        this.isInPortal = false;
    }

    @Marker
    public int j_(){return 0;}

    public boolean isInPortal() {
        return isInPortal;
    }


    @Override
    public void setInPortal(int destination_dimension_id) {
        this.isInPortal = true;
        super.setInPortal(destination_dimension_id);
    }
}
