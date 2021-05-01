package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.ContainerChest;
import net.minecraft.EntityMinecartChest;
import net.minecraft.EntityPlayer;

public class ContainerChestMinecart extends ContainerChest {
    private final EntityMinecartChest minecart;
    public ContainerChestMinecart(EntityPlayer player, EntityMinecartChest par2IInventory) {
        super(player, par2IInventory);
        minecart = par2IInventory;
    }


    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return super.canInteractWith(par1EntityPlayer) && !this.minecart.isInPortal();
    }
}
