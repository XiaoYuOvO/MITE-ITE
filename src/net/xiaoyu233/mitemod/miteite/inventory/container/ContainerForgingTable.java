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

    public ContainerForgingTable(ForgingTableSlots slots,EntityHuman player, int x, int y, int z) {
        super(player);
        this.blockX = x;
        this.blockY = y;
        this.blockZ = z;
        slots.initSlots(this);
        this.slots = slots;
        if (!player.getWorld().I) {
            this.tileentity = (TileEntityForgingTable) player.getWorldServer().r(x, y, z);
        }else {
            this.tileentity = null;
        }
        this.a(slots);

        int index;
        for(index = 0; index < 3; ++index) {
            for(int var8 = 0; var8 < 9; ++var8) {
                this.a(new Slot(player.bn, var8 + index * 9 + 9, 8 + var8 * 18, 84 + index * 18));
            }
        }

        for(index = 0; index < 9; ++index) {
            this.a(new Slot(player.bn, index, 8 + index * 18, 142));
        }

    }

    public List<ItemStack> getInventory() {
        List<ItemStack> nonnulllist = new ArrayList<>();

        for (Object o : this.c) {
            nonnulllist.add(((Slot) o).d());
        }
        return nonnulllist;
    }

    @Override
    //onContainerClosed
    public void b(EntityHuman par1EntityPlayer) {
        super.b(par1EntityPlayer);
        if (!this.world.I){
            this.slots.onContainerClosed();
        }
    }

    @Override
    public ItemStack b(EntityHuman playerIn, int index) {
        ItemStack itemstack = null/*ItemStack.EMPTY*/;
        Slot slot = (Slot)this.c/*inventorySlots*/.get(index);
        if (slot != null && slot.e()/*getHasStack()*/) {
            //ItemStack itemstack1 = slot.getStack();
            ItemStack itemstack1 = slot.d();
            //itemstack = itemstack1.copy();
            itemstack = itemstack1.m();
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
                if (!this.a(itemstack1,this.slots.getSize(),this.c.size(),false)){
                    return null;
                }
            }else {
                if (itemstack1.b() instanceof ItemAxe){
                    if (!this.a(itemstack1,this.slots.getAxeSlotIndex(),this.slots.getAxeSlotIndex() + 1,false)){
                        if (!this.a(itemstack1,this.slots.getToolItemSlotIndex(),this.slots.getToolItemSlotIndex() + 1,false)){
                            return null;
                        }
                    }
                }else if (itemstack1.b() instanceof ItemWarHammer){
                    if (!this.a(itemstack1,this.slots.getHammerSlotIndex(),this.slots.getHammerSlotIndex() + 1,false)){
                        if (!this.a(itemstack1,this.slots.getToolItemSlotIndex(),this.slots.getToolItemSlotIndex() + 1,false)){
                            return null;
                        }
                    }
                }else if (itemstack1.b() instanceof ItemTool || itemstack1.b() instanceof ItemArmor){
                    if (!this.a(itemstack1,this.slots.getToolItemSlotIndex(),this.slots.getToolItemSlotIndex() + 1,false)){
                        if (!this.a(itemstack1,0,this.slots.getSize(),false)){
                            return null;
                        }
                    }
                }else {
                    if (!this.a(itemstack1,0,this.slots.getSize(),false)){
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
            if (itemstack1.b == 0) {
                slot.c(null)/*putStack(ItemStack.EMPTY)*/;
            } else {
                slot.f()/*onSlotChanged*/;
            }
            //  itemstack1.getCount() == itemstack.getCount()
            if (itemstack1.b == itemstack.b) {
                return null;
            }
            //onTake
            slot.a(playerIn, itemstack1);
        }

        return itemstack;
    }

//    private boolean tryMergeToolItemToSlot(ItemStack itemStack){
//
//    }

    //onCraftMatrixChanged
    public void a(IInventory par1IInventory) {
        this.b();
        if (par1IInventory == this.slots){
            this.slots.onItemsChanged();
        }
    }

    @Override
    public void b() {
        super.b();
        for (Object o : this.e) {
            ICrafting var2 = (ICrafting) o;
            if (this.lastForgingTime != this.forgingTime) {
                var2.a(this, 0, this.forgingTime);
            }
        }
        this.lastForgingTime = this.forgingTime;
    }

    public void b(int index, int value) {
        if (index == 0){
            this.forgingTime = value;
        }
    }

    @Override
    public void a(ICrafting par1ICrafting) {
        super.a(par1ICrafting);
        par1ICrafting.a(this, 0, this.forgingTime);
    }

    public void addSlot(Slot slot){
        this.a(slot);
    }

    @Override
    public boolean a(EntityHuman player) {
        if (this.world.getBlock(this.blockX, this.blockY, this.blockZ) instanceof BlockForgingTable && this.world.r(this.blockX, this.blockY, this.blockZ) instanceof TileEntityForgingTable) {
            return player.e((double)this.blockX + 0.5D, (double)this.blockY + 0.5D, (double)this.blockZ + 0.5D) <= 64.0D;
        } else {
            return false;
        }
    }

    //OnlyInServer
    public void startForging(){
        if (!this.tileentity.startForging()) {
            this.finishForging(SPacketFinishForging.Status.CANCELED);
            ItemStack toolItem = this.slots.getToolItem();
            if (toolItem != null){
                ForgingRecipe recipeFromTool = this.slots.getRecipeFromTool(toolItem);
                if (recipeFromTool != null && this.slots.getHammerItem() != null && this.slots.getAxeItem() != null && !this.world.I){
                    this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.ReqItems.of(this.slots.getNeedItems(recipeFromTool))));
                }
            }
        }
    }

    void finishForging(SPacketFinishForging.Status finishStatus){
        this.player.sendPacket(new SPacketFinishForging(finishStatus));
        if (!this.world.I){
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

    //onlyInServer
    void onCraftMatrixChanged(){
        ((EntityPlayer) this.player).a(this,this.getInventory());
        this.slots.onItemsChanged();
    }

    void updateTime(int time){
        this.forgingTime  = time;
        this.b();
    }


    void updateInfo(@Nullable ForgingRecipe recipe) {
        if (!this.world.I){
            if (recipe != null){
                this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.EnhanceInfo.getInstance(recipe.getChanceOfFailure(),recipe.getFaultFeedback().getName(),recipe.getFaultFeedback().getData(),this.slots.getForgingTime(recipe),recipe.getHammerDurabilityCost(),recipe.getAxeDurabilityCost())));
            }else {
                this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.EnhanceInfo.getInstance(0,"",0,0,0,0)));
            }
        }
    }

    public int getForgingTime() {
        return forgingTime;
    }

    public void sendToolInfo(SPacketForgingTableInfo.ToolInfo.Tool tool) {
        if (!this.world.I){
            this.player.sendPacket(new SPacketForgingTableInfo(SPacketForgingTableInfo.ToolInfo.of(tool)));
        }
    }
}
