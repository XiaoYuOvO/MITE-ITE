package net.xiaoyu233.mitemod.miteite.inventory.container;

import com.google.common.collect.Lists;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingRecipe;
import net.xiaoyu233.mitemod.miteite.item.recipe.ForgingTableRecipes;
import net.xiaoyu233.mitemod.miteite.network.SPacketFinishForging;
import net.xiaoyu233.mitemod.miteite.network.SPacketForgingTableInfo;
import net.xiaoyu233.mitemod.miteite.tileentity.TileEntityForgingTable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ForgingTableSlots extends InventorySubcontainer {
    private final Slot up;
    private final Slot downLeft;
    private final Slot downRight;
    private final Slot left;
    private final Slot right;
    private final Slot tool;
    private final Slot axe;
    private final Slot hammer;
    private final Slot output;
    private ContainerForgingTable container;
    private final TileEntityForgingTable tileEntityForgingTable;
    public static final int slotSize = 9;

    public int getSize(){
        return slotSize;
    }

    public int getAxeSlotIndex(){
        return 6;
    }

    public int getHammerSlotIndex(){
        return 7;
    }

    public int getOutputIndex(){
        return 8;
    }

    public ForgingTableSlots(IInventory forgingTable) {
        super("ForgingTable",true,slotSize/*Size*/);
        if (forgingTable instanceof TileEntityForgingTable){
            this.tileEntityForgingTable = (TileEntityForgingTable) forgingTable;
        }else {
            this.tileEntityForgingTable = null;
        }
        up = new Slot(forgingTable,0, 42,6);
        left = new Slot(forgingTable,1, 15,31);
        right = new Slot(forgingTable,2, 69,31);
        downLeft = new Slot(forgingTable,3, 24,62);
        downRight = new Slot(forgingTable,4, 60,62);
        tool = new SlotTool(forgingTable,this.getToolItemSlotIndex(),42,34);
        this.axe = new Slot(forgingTable, this.getAxeSlotIndex(), 137, 62) {
            public boolean isItemValid(ItemStack par1ItemStack) {
                return par1ItemStack.getItem() instanceof ItemAxe;
            }
        };
        this.hammer = new Slot(forgingTable, this.getHammerSlotIndex(), 137, 6) {
            public boolean isItemValid(ItemStack par1ItemStack) {
                return par1ItemStack.getItem() instanceof ItemWarHammer;
            }
        };
        this.output = new Slot(forgingTable, this.getOutputIndex(), 137, 34) {
            public boolean isItemValid(ItemStack par1ItemStack) {
                return false;
            }
        };
    }

    public void costItems(ForgingRecipe recipe) {
        List<Slot> currentMaterials = Lists.newArrayList(this.up, this.left, this.right, this.downLeft, this.downRight);
        List<ItemStack> materialsRequired = Lists.newArrayList(recipe.getMaterialsToUpgrade());

        for (Slot current : currentMaterials) {
            for (ItemStack req : materialsRequired) {
                if (current.getStack() != null && ItemStack.areItemStacksEqual(req, current.getStack(), true, false, false, true)) {
                    int resultSize = current.getStack().stackSize - req.stackSize;
                    if (resultSize > 0) {
                        current.getStack().setStackSize(resultSize);
                    } else {
                        current.putStack(null);
                    }
                }
            }
        }

    }

    public void damageHammerAndAxe(int hammerDurabilityCost, int axeDurabilityCost) {
        this.hammer.putStack(this.damageItem(this.hammer.getStack(), hammerDurabilityCost));
        this.axe.putStack(this.damageItem(this.axe.getStack(), axeDurabilityCost));
    }

    private ItemStack damageItem(ItemStack itemStack, int damage) {
        if (itemStack != null) {
            int rawDamage = itemStack.getItemDamage();
            int maxDamage = itemStack.getMaxDamage();
            int resultDamage = rawDamage + damage;
            return resultDamage > maxDamage ? null : itemStack.setItemDamage(resultDamage);
        } else {
            return null;
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
       this.tryWriteSlotStack(nbt,this.up,"Up");
       this.tryWriteSlotStack(nbt,this.left,"Left");
       this.tryWriteSlotStack(nbt,this.right,"Right");
       this.tryWriteSlotStack(nbt,this.downLeft,"DownLeft");
       this.tryWriteSlotStack(nbt,this.downRight,"DownRight");
       this.tryWriteSlotStack(nbt,this.tool,"Tool");
       this.tryWriteSlotStack(nbt,this.axe,"Axe");
       this.tryWriteSlotStack(nbt,this.hammer,"Hammer");
       this.tryWriteSlotStack(nbt,this.output,"Output");
    }

    public int getChanceOfFailure(@Nonnull ForgingRecipe recipe){
        int bounceChanceFromTool = 0;
        ItemStack hammer = this.getHammerItem();
        if (hammer != null){
            bounceChanceFromTool += Math.max((hammer.getMaterialForRepairs().getMinHarvestLevel() - Material.iron.getMinHarvestLevel()) * 3,0);
        }
        ItemStack axeItem = this.getAxeItem();
        if (axeItem != null) {
            bounceChanceFromTool += Math.max((axeItem.getMaterialForRepairs().getMinHarvestLevel() - Material.iron.getMinHarvestLevel()) * 2,0);
        }
        return recipe.getChanceOfFailure() - bounceChanceFromTool;
    }

    public int getToolItemSlotIndex(){
        return 5;
    }

    public void dropItems(World world, int x, int y, int z) {
        for(int var2 = 0; var2 < this.tileEntityForgingTable.getSizeInventory(); ++var2) {
            ItemStack var3 = this.tileEntityForgingTable.getItem(var2);
            if (var3 != null) {
                float var10 = world.rand.nextFloat() * 0.8F + 0.1F;
                float var11 = world.rand.nextFloat() * 0.8F + 0.1F;

                EntityItem var14;
                for(float var12 = world.rand.nextFloat() * 0.8F + 0.1F; var3.stackSize > 0; world.spawnEntityInWorld(var14)) {
                    int var13 = world.rand.nextInt(21) + 10;
                    if (var13 > var3.stackSize) {
                        var13 = var3.stackSize;
                    }

                    var3.stackSize -= var13;
                    var14 = new EntityItem(world, (float)x + var10, (float)y + var11, (float)z + var12, new ItemStack(var3.itemID, var13, var3.getItemSubtype()));
                    if (var3.isItemDamaged()) {
                        var14.getEntityItem().setItemDamage(var3.getItemDamage());
                    }

                    float var15 = 0.05F;
                    var14.motionX = (float)world.rand.nextGaussian() * var15;
                    var14.motionY = (float)world.rand.nextGaussian() * var15 + 0.2F;
                    var14.motionZ = (float)world.rand.nextGaussian() * var15;
                    if (var3.getItem().hasQuality()) {
                        var14.getEntityItem().setQuality(var3.getQuality());
                    }

                    if (var3.hasTagCompound()) {
                        var14.getEntityItem().setTagCompound((NBTTagCompound)var3.getTagCompound().copy());
                    }
                }
            }
        }

    }

    public ItemStack getAxeItem() {
        return this.axe.getStack();
    }

    public float getEffectivityFactorFromTool() {
        return this.axe.getStack() != null && this.hammer.getStack() != null ? 0.25F +
                (float)this.axe.getStack().getItem().getMaterialForRepairs().getMinHarvestLevel()
                        / 5.0F + (float)this.hammer.getStack().getItem().getMaterialForRepairs().getMinHarvestLevel() /
                4.0F : 1.0F;
    }

    public ItemStack getHammerItem() {
        return this.hammer.getStack();
    }

    public List<ItemStack> getNeedItems(@Nonnull ForgingRecipe recipe) {
        List<ItemStack> currentMaterials = Lists.newArrayList(this.up.getStack(), this.left.getStack(), this.right.getStack(), this.downLeft.getStack(), this.downRight.getStack());
        List<ItemStack> materialsRequired = Lists.newArrayList(recipe.getMaterialsToUpgrade());
        materialsRequired.removeIf((req) -> currentMaterials.stream().anyMatch((current) -> ItemStack.areItemStacksEqual(req, current, true, false, false, true) && current.stackSize >= req.stackSize));
        return materialsRequired;
    }


    public void onItemsChanged(){
        ItemStack toolItem = this.getToolItem();
        if (toolItem != null){
            ForgingRecipe recipeFromTool = this.getRecipeFromTool(toolItem);
            this.updateInfo(recipeFromTool);
        }else {
            this.updateInfo(null);
        }
    }

    public ItemStack getOutput() {
        return this.output.getStack();
    }

    public void setOutput(ItemStack output) {
        this.output.putStack(output);
    }

    @Nullable
    public ForgingRecipe getRecipeFromTool(@Nonnull ItemStack toolStack) {
        return ForgingTableRecipes.getRecipe(toolStack.getItem().getMaterialForRepairs(), toolStack.getForgingGrade());
    }

    public void updateInfo(@Nullable ForgingRecipe recipe){
        if (this.container != null) {
            this.container.updateInfo(recipe);
        }
    }

    public void onFinishForging(SPacketFinishForging.Status finishStatus){
        if (this.container != null){
            this.container.finishForging(finishStatus);
        }
    }

    public ItemStack getToolItem() {
        return this.tool.getStack();
    }

    public void updateSlots(){
        if (this.container != null){
            this.container.onCraftMatrixChanged();
        }
    }

    public void updateTime(int time){
        if (this.container != null){
            this.container.updateTime(time);
        }
    }

    public void setToolItem(ItemStack toolItem) {
        this.tool.putStack(toolItem);
    }

    public ForgingRecipe getUsedRecipe(int blockLevel) {
        ItemStack toolStack = this.tool.getStack();
        if (toolStack != null) {
            ForgingRecipe recipe = this.getRecipeFromTool(toolStack);
            if (recipe != null) {
                if (blockLevel >= recipe.getForgingTableLevelReq().getLevel()) {
                    if (this.hammer.getStack() != null) {
                        if (this.axe.getStack() != null) {
                            if (this.getNeedItems(recipe).isEmpty()) {
                                return recipe;
                            }
                        } else {
                            this.container.sendToolInfo(SPacketForgingTableInfo.ToolInfo.Tool.AXE);
                        }
                    } else {
                        this.container.sendToolInfo(SPacketForgingTableInfo.ToolInfo.Tool.HAMMER);
                    }
                } else {
                    this.container.sendTableInfo(SPacketForgingTableInfo.TableLevelInfo.of(recipe.getForgingTableLevelReq()));
                }
            }else {
                this.container.sendToolInfo(SPacketForgingTableInfo.ToolInfo.Tool.MAX_LEVEL);
            }
        }

        return null;
    }

    public void initSlots(ContainerForgingTable forgingTable){
        forgingTable.addSlot(up);
        forgingTable.addSlot(left);
        forgingTable.addSlot(right);
        forgingTable.addSlot(downLeft);
        forgingTable.addSlot(downRight);
        forgingTable.addSlot(tool);
        forgingTable.addSlot(axe);
        forgingTable.addSlot(hammer);
        forgingTable.addSlot(output);
        if (this.tileEntityForgingTable != null){
            this.tileEntityForgingTable.openChest();
        }
        this.container = forgingTable;
    }

    void onContainerClosed() {
        if (!this.container.world.isRemote && this.tileEntityForgingTable != null) {
            this.tileEntityForgingTable.closeChest();
        }

    }

    public void readFromNBT(NBTTagCompound nbt,TileEntityForgingTable tileEntityForgingTable){
        tileEntityForgingTable.setItem(0, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Up")));
        tileEntityForgingTable.setItem(1, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Left")));
        tileEntityForgingTable.setItem(2, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Right")));
        tileEntityForgingTable.setItem(3, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("DownLeft")));
        tileEntityForgingTable.setItem(4, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("DownRight")));
        tileEntityForgingTable.setItem(this.getToolItemSlotIndex(), ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Tool")));
        tileEntityForgingTable.setItem(this.getAxeSlotIndex(), ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Axe")));
        tileEntityForgingTable.setItem(this.getHammerSlotIndex(), ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Hammer")));
        tileEntityForgingTable.setItem(this.getOutputIndex(), ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Output")));
    }

    public int getForgingTime(ForgingRecipe recipe) {
        return Math.round(recipe.getTimeReq() / this.getEffectivityFactorFromTool());
    }

    private void tryWriteSlotStack(NBTTagCompound nbt,Slot slot,String name){
        if (slot.getStack() != null) {
            nbt.setCompoundTag(name, slot.getStack().writeToNBT(new NBTTagCompound()));
        }
    }
}
