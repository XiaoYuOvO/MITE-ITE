package net.xiaoyu233.mitemod.miteite.tileentity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.block.BlockForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingRecipe;
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

    public ItemStack getItem(int index) {
        return items[index];
    }

    public ForgingTableSlots getSlots() {
        return slots;
    }

    @Override
    //writeToNBT(NBTTagCompound)
    public void b(NBTTagCompound par1NBTTagCompound) {
        super.b(par1NBTTagCompound);
        if (this.b()) {
            par1NBTTagCompound.a("CustomName", this.customName);
        }
        this.slots.writeToNBT(par1NBTTagCompound);
    }

    @Override
    //tick
    public void h() {
        if (!this.az().I){
            if (this.isForging){
                this.forgingTime++;
                this.slots.updateTime(this.forgingTime);
                if (this.az().s.nextInt(100) < 1){
                    this.az().getAsWorldServer().playSoundAtBlock(this.l,this.m,this.n,"random.anvil_land",1.0f,1.0f);
                }
                if (this.forgingTime == this.currentFailCheckTime){
                    if (this.az().s.nextInt(100) < this.usedRecipe.getChanceOfFailure()) {
                        this.failForging();
                        this.finishForging();
                    }
                }else if (this.forgingTime >= this.maxTime){
                    this.completeForging();
                    this.finishForging();
                }
            }
        }
    }

    private void failForging(){
        //avoid for the change of setting items
        ForgingRecipe currentRecipe = this.usedRecipe;
        this.az().playSoundAtBlock(this.l,this.m,this.n,"random.anvil_break",1,1.1f);
        this.slots.onFinishForging(SPacketFinishForging.Status.FAILED);
        this.slots.damageHammerAndAxe(currentRecipe.getHammerDurabilityCost()/2,currentRecipe.getAxeDurabilityCost()/2);
        this.slots.costItems(currentRecipe);
        this.slots.setToolItem(currentRecipe.getFaultFeedback().accept(this.slots.getToolItem()));
        this.slots.updateSlots();
    }

    private void completeForging(){
        //avoid for the change of setting items
        ForgingRecipe currentRecipe = this.usedRecipe;
        ItemStack toolItem = this.slots.getToolItem();
        toolItem.setForgingGrade(currentRecipe.getLevelToUpgrade() + 1);
        this.az().playSoundAtBlock(this.l, this.m, this.n,"random.levelup",1.0f,1.0f);
        this.slots.setOutput(toolItem);
        this.slots.onFinishForging(SPacketFinishForging.Status.COMPLETED);
        this.slots.damageHammerAndAxe(currentRecipe.getHammerDurabilityCost(),currentRecipe.getAxeDurabilityCost());
        this.slots.costItems(currentRecipe);
        this.slots.setToolItem(null);
        this.slots.updateSlots();
    }

    private void finishForging(){
        this.isForging = false;
        this.forgingTime = 0;
        this.usedRecipe = null;
        this.maxTime = 0;
        this.slots.updateTime(0);
    }

    @Override
    //readFromNBT()
    public void a(NBTTagCompound par1NBTTagCompound) {
        super.a(par1NBTTagCompound);
        this.slots.readFromNBT(par1NBTTagCompound,this);
    }

    public boolean b() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    //getSize
    public int j_() {
        return ForgingTableSlots.slotSize;
    }

    @Override
    //getItemStack
    public ItemStack a(int var1) {
        return this.items[var1];
    }

    @Override
    //DecreaseItem
    public ItemStack a(int index, int count) {
        if (this.items[index] != null) {
            ItemStack var3;
            if (this.items[index].b <= count) {
                var3 = this.items[index];
                this.items[index] = null;
            } else {
                var3 = this.items[index].a(count);
                if (this.items[index].b == 0) {
                    this.items[index] = null;
                }
            }
            return var3;
        } else {
            return null;
        }
    }

    @Override
    //removeItem
    public ItemStack a_(int par1) {
        if (this.items[par1] != null) {
            ItemStack var2 = this.items[par1];
            this.items[par1] = null;
            return var2;
        } else {
            return null;
        }
    }

    @Override
    //setItem
    public void a(int var1, ItemStack var2) {
        this.items[var1] = var2;
    }

    @Override
    //maxStackSize
    public int d() {
        return 64;
    }

    @Override
    public boolean a(EntityHuman player) {
        if (player.getWorld().getBlock(this.l, this.m, this.n) instanceof BlockForgingTable && player.getWorld().r(this.l, this.m, this.n) instanceof TileEntityForgingTable) {
            return player.e((double)this.l + 0.5D, (double)this.m + 0.5D, (double)this.n + 0.5D) <= 64.0D;
        } else {
            return false;
        }
    }

    @Override
    //onContainerOpened
    public void k_() {
        this.isUsing = true;
    }

    @Override
    //onContainerClosed
    public void g() {
        this.finishForging();
        this.isUsing = false;
    }

    @Override
    //onContainerChanged
    public void e() {
        super.e();
        ForgingRecipe usedRecipe = this.slots.getUsedRecipe();
        ItemStack toolItem = this.slots.getToolItem();
        if (toolItem != null){
            ForgingRecipe recipeFromTool = this.slots.getRecipeFromTool(toolItem);
            this.slots.updateInfo(recipeFromTool);
        }else {
            this.slots.updateInfo(null);
        }
        if (this.usedRecipe != null) {
            if (this.slots.getForgingTime(this.usedRecipe) != this.maxTime){
                this.finishForging();
                this.slots.onFinishForging(SPacketFinishForging.Status.CANCELED);
            }
            if (usedRecipe == null && this.slots.getOutput() == null) {
                this.finishForging();
                this.slots.onFinishForging(SPacketFinishForging.Status.CANCELED);
            }
        }
    }

    @Override
    public boolean b(int var1, ItemStack var2) {
        return this.slots.b(var1, var2);
    }

    @Override
    public void destroyInventory() {
        Arrays.fill(this.items, null);
    }

    public void dropAllItems() {
        this.slots.dropItems(this.k,this.l,this.m,this.n);
    }

    public boolean startForging() {
        this.usedRecipe = slots.getUsedRecipe();
        if (this.usedRecipe != null){
            this.isForging = true;
            this.maxTime = Math.round(this.usedRecipe.getTimeReq() / slots.getEffectivityFactorFromTool());
            this.currentFailCheckTime = this.az().s.nextInt(this.maxTime);
            return true;
        }
        return false;
    }

    public boolean isUsing() {
        return this.isUsing;
    }
}
