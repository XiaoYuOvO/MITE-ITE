package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityMinecartChest;
import net.minecraft.Minecraft;
import net.minecraft.axj;
import net.minecraft.bex;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.gui.GuiForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

@Transform(bex.class)
public class ClientPlayerTrans {
    @Link
    protected Minecraft d;
    public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots){
        this.d.a(new GuiForgingTable(dyCast(this), x, y, z,slots));
    }

    public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
        this.d.a(new axj(dyCast(this), par1IInventory));
    }
}
