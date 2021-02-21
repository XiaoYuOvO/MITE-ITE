package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.ContainerChest;
import net.minecraft.EntityHuman;
import net.minecraft.EntityMinecartChest;

public class ContainerChestMinecart extends ContainerChest {
    private final EntityMinecartChest minecart;
    public ContainerChestMinecart(EntityHuman player, EntityMinecartChest par2IInventory) {
        super(player, par2IInventory);
        minecart = par2IInventory;
    }

    @Override
    public boolean a(EntityHuman par1EntityPlayer) {
        return super.a(par1EntityPlayer) && !this.minecart.isInPortal();
    }
}
