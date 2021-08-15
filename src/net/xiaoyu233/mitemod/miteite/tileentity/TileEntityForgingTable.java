package net.xiaoyu233.mitemod.miteite.tileentity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingRecipe;
import net.xiaoyu233.mitemod.miteite.item.recipe.IFaultFeedback;
import net.xiaoyu233.mitemod.miteite.network.SPacketFinishForging;

import java.util.Arrays;

public class TileEntityForgingTable extends TileEntity implements IInventory {
    private String customName;
    private final ItemStack[] items = new ItemStack[ForgingTableSlots.slotSize];
    private final ForgingTableSlots slots = new ForgingTableSlots(this);
    private int forgingTime;
    private int currentFailCheckTime;
    //nullable
    private ForgingRecipe usedRecipe;
    private int maxTime;
    private boolean isForging;
    private boolean isUsing;

    public void setItem(int index,ItemStack itemStack) {
        this.items[index] = itemStack;
    }

    @Override
    //onContainerClosed
    public void closeChest() {
        this.finishForging();
        this.isUsing = false;
    }

    private void completeForging() {
        ForgingRecipe currentRecipe = this.usedRecipe;
        ItemStack toolItem = this.slots.getToolItem();
        toolItem.setForgingGrade(currentRecipe.getLevelToUpgrade() + 1);
        EnumQuality qualityReward = currentRecipe.getQualityReward();
        if (qualityReward != null){
            toolItem.setQuality(qualityReward);
        }
        this.getWorldObj().playSoundAtBlock(this.xCoord, this.yCoord, this.zCoord, "random.levelup", 1.0F, 1.0F);
        this.slots.setOutput(toolItem);
        this.slots.onFinishForging(SPacketFinishForging.Status.COMPLETED);
        this.slots.damageHammerAndAxe(currentRecipe.getHammerDurabilityCost(), currentRecipe.getAxeDurabilityCost());
        this.slots.costItems(currentRecipe);
        this.slots.setToolItem(null);
        this.slots.updateSlots();
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if (this.items[index] != null) {
            ItemStack var3;
            if (this.items[index].stackSize <= count) {
                var3 = this.items[index];
                this.items[index] = null;
            } else {
                var3 = this.items[index].splitStack(count);
                if (this.items[index].stackSize == 0) {
                    this.items[index] = null;
                }
            }

            return var3;
        } else {
            return null;
        }
    }

    public void dropAllItems() {
        this.slots.dropItems(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
    }

    private void failForging() {
        ForgingRecipe currentRecipe = this.usedRecipe;
        this.getWorldObj().playSoundAtBlock(this.xCoord, this.yCoord, this.zCoord, "random.anvil_break", 1.0F, 1.1F);
        this.slots.onFinishForging(SPacketFinishForging.Status.FAILED);
        this.slots.damageHammerAndAxe(currentRecipe.getHammerDurabilityCost() / 2, currentRecipe.getAxeDurabilityCost() / 2);
        this.slots.costItems(currentRecipe);
        ItemStack result = this.slots.getToolItem();
        for (IFaultFeedback iFaultFeedback : currentRecipe.getFaultFeedback()) {
            result = iFaultFeedback.accept(result);
        }
        this.slots.setToolItem(result);
        this.slots.updateSlots();
    }

    private void finishForging() {
        this.isForging = false;
        this.forgingTime = 0;
        this.usedRecipe = null;
        this.maxTime = 0;
        this.slots.updateTime(0);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public ItemStack getItem(int index) {
        return this.items[index];
    }

    public boolean b() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public int getSizeInventory() {
        return ForgingTableSlots.slotSize;
    }

    public ForgingTableSlots getSlots() {
        return this.slots;
    }

    @Override
    //getItemStack
    public ItemStack getStackInSlot(int var1) {
        return this.items[var1];
    }

    @Override
    //removeItem
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.items[par1] != null) {
            ItemStack var2 = this.items[par1];
            this.items[par1] = null;
            return var2;
        } else {
            return null;
        }
    }

    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return this.slots.isItemValidForSlot(var1, var2);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        if (player.getWorld().getBlock(this.xCoord, this.yCoord, this.zCoord) instanceof BlockForgingTable && player.getWorld().getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) instanceof TileEntityForgingTable) {
            return player.getDistance((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
        } else {
            return false;
        }
    }

    @Override
    public void onInventoryChanged() {
        super.onInventoryChanged();
        ForgingRecipe usedRecipe = this.slots.getUsedRecipe(this.getBlockMetadata());
        ItemStack toolItem = this.slots.getToolItem();
        if (toolItem != null) {
            ForgingRecipe recipeFromTool = this.slots.getRecipeFromTool(toolItem);
            this.slots.updateInfo(recipeFromTool);
        } else {
            this.slots.updateInfo(null);
        }

        if ( this.usedRecipe != null) {
            if (this.usedRecipe != usedRecipe || this.slots.getForgingTime(this.usedRecipe) != this.maxTime) {
                this.finishForging();
                this.slots.onFinishForging(SPacketFinishForging.Status.CANCELED);
            }
        }

    }

    @Override
    public void openChest() {
        this.isUsing = true;
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.slots.readFromNBT(par1NBTTagCompound, this);
    }

    @Override
    //setItem
    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.items[var1] = var2;
    }

    public boolean startForging() {
        this.usedRecipe = this.slots.getUsedRecipe(this.getBlockMetadata());
        if (this.usedRecipe != null) {
            this.isForging = true;
            this.maxTime = Math.round((float)this.usedRecipe.getTimeReq() / this.slots.getEffectivityFactorFromTool());
            this.currentFailCheckTime = this.getWorldObj().rand.nextInt(this.maxTime);
            return true;
        } else {
            return false;
        }
    }

    public void destroyInventory() {
        Arrays.fill(this.items, null);
    }

    public void updateEntity() {
        if (!this.getWorldObj().isRemote && this.isForging) {
            ++this.forgingTime;
            this.slots.updateTime(this.forgingTime);
            if (this.getWorldObj().rand.nextInt(100) < 1) {
                this.getWorldObj().getAsWorldServer().playSoundAtBlock(this.xCoord, this.yCoord, this.zCoord, "random.anvil_land", 1.0F, 1.0F);
            }

            if (this.forgingTime == this.currentFailCheckTime) {
                if (this.getWorldObj().rand.nextInt(100) < this.slots.getChanceOfFailure(this.usedRecipe)) {
                    this.failForging();
                    this.finishForging();
                }
            } else if (this.forgingTime >= this.maxTime) {
                this.completeForging();
                this.finishForging();
            }
        }

    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        if (this.b()) {
            par1NBTTagCompound.setString("CustomName", this.customName);
        }

        this.slots.writeToNBT(par1NBTTagCompound);
    }

    public boolean isUsing() {
        return this.isUsing;
    }
}
