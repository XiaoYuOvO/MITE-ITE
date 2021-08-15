package net.xiaoyu233.mitemod.miteite.inventory.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockForgingTable;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingRecipe;
import net.xiaoyu233.mitemod.miteite.network.SPacketFinishForging;
import net.xiaoyu233.mitemod.miteite.network.SPacketForgingTableInfo;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityForgingTable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ContainerForgingTable extends Container {
    @Nullable
    //null on client so we need to check when the method can be invoked in client
    private final TileEntityForgingTable tileentity;
    private final ForgingTableSlots slots;
    private final int blockX,blockY,blockZ;
    private int forgingTime;
    private int lastForgingTime;

    public ContainerForgingTable(ForgingTableSlots slots,EntityPlayer player, int x, int y, int z) {
        super(player);
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        slots.initSlots(this);
        this.slots = slots;
        if (!player.getWorld().isRemote) {
            this.tileentity = (TileEntityForgingTable) player.getWorldServer().getBlockTileEntity(x, y, z);
        }else {
            this.tileentity = null;
        }
        this.onCraftMatrixChanged(slots);

        int index;
        for(index = 0; index < 3; ++index) {
            for(int var8 = 0; var8 < 9; ++var8) {
                this.addSlot(new Slot(player.inventory, var8 + index * 9 + 9, 8 + var8 * 18, 84 + index * 18));
            }
        }

        for(index = 0; index < 9; ++index) {
            this.addSlot(new Slot(player.inventory, index, 8 + index * 18, 142));
        }

    }

    public void addSlot(Slot slot){
        this.addSlotToContainer(slot);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        if (this.world.getBlock(this.blockX, this.blockY, this.blockZ) instanceof BlockForgingTable && this.world.getBlockTileEntity(this.blockX, this.blockY, this.blockZ) instanceof TileEntityForgingTable) {
            return player.getDistanceSq((double)this.blockX + 0.5D, (double)this.blockY + 0.5D, (double)this.blockZ + 0.5D) <= 64.0D;
        } else {
            return false;
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (Object o : this.crafters) {
            ICrafting var2 = (ICrafting) o;
            if (this.lastForgingTime != this.forgingTime) {
                var2.sendProgressBarUpdate(this, 0, this.forgingTime);
            }
        }
        this.lastForgingTime = this.forgingTime;
    }

//    private boolean tryMergeToolItemToSlot(ItemStack itemStack){
//
//    }

    void finishForging(SPacketFinishForging.Status finishStatus){
        this.player.sendPacket(new SPacketFinishForging(finishStatus));
        if (!this.world.isRemote){
            switch (finishStatus) {
                case COMPLETED:
                    this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.Succeed.getInstance()));
                    break;
                case FAILED:
                    this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.Failed.getInstance()));
                    break;
                case CANCELED:
                    break;
            }
        }
    }

    public List<ItemStack> getInventory() {
        List<ItemStack> nonnulllist = new ArrayList<>();

        for (Object o : this.inventorySlots) {
            nonnulllist.add(((Slot) o).getStack());
        }
        return nonnulllist;
    }

    public void b(int index, int value) {
        if (index == 0){
            this.forgingTime = value;
        }
    }

    @Override
    //onContainerClosed
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        if (!this.world.isRemote){
            this.slots.onContainerClosed();
        }
    }

    @Override
    public void onCraftGuiOpened(ICrafting par1ICrafting) {
        super.onCraftGuiOpened(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.forgingTime);
    }

    //onCraftMatrixChanged
    public void onCraftMatrixChanged(IInventory par1IInventory) {
        this.detectAndSendChanges();
        if (par1IInventory == this.slots){
            this.slots.onItemsChanged();
        }
    }

    //onlyInServer
    void onCraftMatrixChanged(){
        ((ServerPlayer) this.player).updateCraftingInventory(this,this.getInventory());
        this.slots.onItemsChanged();
    }

    public void sendToolInfo(SPacketForgingTableInfo.ToolInfo.Tool tool) {
        if (!this.world.isRemote){
            this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.ToolInfo.of(tool)));
        }
    }

    public void sendTableInfo(SPacketForgingTableInfo.TableLevelInfo info) {
        if (!this.world.isRemote){
            this.player.sendPacket(new SPacketForgingTableInfo(info));
        }
    }

    //OnlyInServer
    public void startForging(){
        if (!this.tileentity.startForging()) {
            this.finishForging(SPacketFinishForging.Status.CANCELED);
            ItemStack toolItem = this.slots.getToolItem();
            if (toolItem != null){
                ForgingRecipe recipeFromTool = this.slots.getRecipeFromTool(toolItem);
                if (recipeFromTool != null && this.tileentity.getBlockMetadata() >= recipeFromTool.getForgingTableLevelReq().getLevel() && this.slots.getHammerItem() != null && this.slots.getAxeItem() != null && !this.world.isRemote){
                    this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.ReqItems.of(this.slots.getNeedItems(recipeFromTool))));
                }
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = null/*ItemStack.EMPTY*/;
        Slot slot = (Slot)this.inventorySlots/*inventorySlots*/.get(index);
        if (slot != null && slot.getHasStack()/*getHasStack()*/) {
            //ItemStack itemstack1 = slot.getStack();
            ItemStack itemstack1 = slot.getStack();
            //itemstack = itemstack1.copy();
            itemstack = itemstack1.copy();
//            if (index == 2) {
//                //  !this.mergeItemStack(itemstack1, 3, 39, true)
//                if (!this.a(itemstack1, 3, 39, true)) {
//                    return null;
//                }
//              //slot.onSlotChange(itemstack1, itemstack);
//                slot.a(itemstack1, itemstack);
//            } else if (index != 0 && index != 1) {
//                if (index < 39) {
//                    if (!this.a(itemstack1, 0, 2, false)) {
//                        return null;
//                    }
//                }    //!this.mergeItemStack(itemstack1, 3, 39, false)
//            } else if (!this.a(itemstack1, 3, 39, false)) {
//                return null;
//            }
            if (index < this.slots.getSize()){
                if (!this.mergeItemStack(itemstack1,this.slots.getSize(),this.inventorySlots.size(),false)){
                    return null;
                }
            }else {
                if (itemstack1.getItem() instanceof ItemAxe){
                    if (!this.mergeItemStack(itemstack1,this.slots.getAxeSlotIndex(),this.slots.getAxeSlotIndex() + 1,false)){
                        if (!this.mergeItemStack(itemstack1,this.slots.getToolItemSlotIndex(),this.slots.getToolItemSlotIndex() + 1,false)){
                            return null;
                        }
                    }
                }else if (itemstack1.getItem() instanceof ItemWarHammer){
                    if (!this.mergeItemStack(itemstack1,this.slots.getHammerSlotIndex(),this.slots.getHammerSlotIndex() + 1,false)){
                        if (!this.mergeItemStack(itemstack1,this.slots.getToolItemSlotIndex(),this.slots.getToolItemSlotIndex() + 1,false)){
                            return null;
                        }
                    }
                }else if (itemstack1.getItem() instanceof ItemTool || itemstack1.getItem() instanceof ItemArmor){
                    if (!this.mergeItemStack(itemstack1,this.slots.getToolItemSlotIndex(),this.slots.getToolItemSlotIndex() + 1,false)){
                        if (!this.mergeItemStack(itemstack1,0,this.slots.getSize(),false)){
                            return null;
                        }
                    }
                }else {
                    if (!this.mergeItemStack(itemstack1,0,this.slots.getSize(),false)){
                        return null;
                    }
                }
            }
/*
            if (itemstack1.isEmpty()) {
               slot.putStack(ItemStack.EMPTY);
            } else {
               slot.onSlotChanged();
            }
 */
            if (itemstack1.stackSize == 0) {
                slot.putStack(null);/*putStack(ItemStack.EMPTY)*/
            } else {
                slot.onSlotChanged();/*onSlotChanged*/
            }
            //  itemstack1.getCount() == itemstack.getCount()
            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }
            //onTake
            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }



    void updateInfo(@Nullable ForgingRecipe recipe) {
        if (!this.world.isRemote){
            if (recipe != null){
                this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.EnhanceInfo.getInstance(this.slots.getChanceOfFailure(recipe),recipe.getFaultFeedback(),this.slots.getForgingTime(recipe),recipe.getHammerDurabilityCost(),recipe.getAxeDurabilityCost())));
            }else {
                this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.EnhanceInfo.getInstance(0,null,0,0,0)));
            }
        }
    }

    public int getForgingTime() {
        return forgingTime;
    }

    void updateTime(int time){
        this.forgingTime  = time;
        this.detectAndSendChanges();
    }
}
