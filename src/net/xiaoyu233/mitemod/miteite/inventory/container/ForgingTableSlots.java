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
        axe = new Slot(forgingTable, this.getAxeSlotIndex(),137,62){
            @Override
            public boolean a(ItemStack par1ItemStack) {
                return par1ItemStack.b() instanceof ItemAxe;
            }
        };
        hammer = new Slot(forgingTable, this.getHammerSlotIndex(),137,6){
            @Override
            public boolean a(ItemStack par1ItemStack) {
                return par1ItemStack.b() instanceof ItemWarHammer;
            }
        };
        output = new Slot(forgingTable,this.getOutputIndex(),137,34){
            @Override
            public boolean a(ItemStack par1ItemStack) {
                return false;
            }
        };
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
        this.container = forgingTable;
    }

    public void readFromNBT(NBTTagCompound nbt,TileEntityForgingTable tileEntityForgingTable){
        tileEntityForgingTable.setItem(0,ItemStack.a(nbt.l("Up")));
        tileEntityForgingTable.setItem(1,ItemStack.a(nbt.l("Left")));
        tileEntityForgingTable.setItem(2,ItemStack.a(nbt.l("Right")));
        tileEntityForgingTable.setItem(3,ItemStack.a(nbt.l("DownLeft")));
        tileEntityForgingTable.setItem(4,ItemStack.a(nbt.l("DownRight")));
        tileEntityForgingTable.setItem(this.getToolItemSlotIndex(),ItemStack.a(nbt.l("Tool")));
        tileEntityForgingTable.setItem(this.getAxeSlotIndex(),ItemStack.a(nbt.l("Axe")));
        tileEntityForgingTable.setItem(this.getHammerSlotIndex(),ItemStack.a(nbt.l("Hammer")));
        tileEntityForgingTable.setItem(this.getOutputIndex(),ItemStack.a(nbt.l("Output")));
    }

    private void tryWriteSlotStack(NBTTagCompound nbt,Slot slot,String name){
        if (slot.d() != null){
            nbt.a(name,slot.d().b(new NBTTagCompound()));
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

    public int getToolItemSlotIndex(){
        return 5;
    }

    public ItemStack getToolItem(){
        return this.tool.d();
    }

    public void  setToolItem(ItemStack toolItem){
        this.tool.c(toolItem);
    }

    public void setOutput(ItemStack output){
        this.output.c(output);
    }

    public void dropItems(World world,int x,int y,int z) {
        for(int var2 = 0; var2 < tileEntityForgingTable.j_(); ++var2) {
            ItemStack var3 = tileEntityForgingTable.a_(var2);
            if (var3 != null) {
                float var10 = world.s.nextFloat() * 0.8F + 0.1F;
                float var11 = world.s.nextFloat() * 0.8F + 0.1F;

                EntityItem var14;
                for(float var12 = world.s.nextFloat() * 0.8F + 0.1F; var3.b > 0; world.d(var14)) {
                    int var13 = world.s.nextInt(21) + 10;
                    if (var13 > var3.b) {
                        var13 = var3.b;
                    }

                    var3.b -= var13;
                    var14 = new EntityItem(world, (float)x + var10, (float)y + var11, (float)z + var12, new ItemStack(var3.d, var13, var3.getItemSubtype()));
                    if (var3.i()) {
                        var14.d().setItemDamage(var3.k());
                    }

                    float var15 = 0.05F;
                    var14.x = (float)world.s.nextGaussian() * var15;
                    var14.y = (float)world.s.nextGaussian() * var15 + 0.2F;
                    var14.z = (float)world.s.nextGaussian() * var15;
                    if (var3.b().hasQuality()) {
                        var14.d().setQuality(var3.getQuality());
                    }

                    if (var3.p()) {
                        var14.d().setTagCompound((NBTTagCompound)var3.q().b());
                    }
                }
            }
        }
    }

    void onContainerClosed(){
        if (!this.container.world.I){
            if (this.tileEntityForgingTable != null){
                this.tileEntityForgingTable.g();
            }
        }
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

    @Nullable
    public ForgingRecipe getRecipeFromTool(@Nonnull ItemStack toolStack){
        return ForgingTableRecipes.getRecipe(toolStack.b().getMaterialForRepairs(), toolStack.getForgingGrade());
    }

    public List<ItemStack> getNeedItems(@Nonnull ForgingRecipe recipe){
        List<ItemStack> currentMaterials = Lists.newArrayList(this.up.d(),this.left.d(),this.right.d(),this.downLeft.d(),this.downRight.d());
        List<ItemStack> materialsRequired = Lists.newArrayList(recipe.getMaterialsToUpgrade());
        materialsRequired.removeIf((req) -> currentMaterials.stream().anyMatch(current -> ItemStack.areItemStacksEqual(req,current,true,false,false,true) && current.b >= req.b));
        return materialsRequired;
    }

    public ForgingRecipe getUsedRecipe() {
        ItemStack toolStack = this.tool.d();
        if (toolStack != null) {
            if (this.hammer.d() != null) {
                if (this.axe.d() != null) {
                    ForgingRecipe recipe = this.getRecipeFromTool(toolStack);
                    if (recipe != null) {
                        if (this.getNeedItems(recipe).isEmpty()) {
                            return recipe;
                        }
                    }
                }else {
                    this.container.sendToolInfo(SPacketForgingTableInfo.ToolInfo.Tool.AXE);
                }
            }else {
                this.container.sendToolInfo(SPacketForgingTableInfo.ToolInfo.Tool.HAMMER);
            }
        }
        return null;
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

    public ItemStack getOutput() {
        return this.output.d();
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

    public void damageHammerAndAxe(int hammerDurabilityCost, int axeDurabilityCost) {
        this.hammer.c(this.damageItem(this.hammer.d(),hammerDurabilityCost));
        this.axe.c(this.damageItem(this.axe.d(),axeDurabilityCost));
    }

    private ItemStack damageItem(ItemStack itemStack,int damage){
        if (itemStack != null){
            int rawDamage = itemStack.k();
            int maxDamage = itemStack.l();
            int resultDamage = rawDamage + damage;
            if (resultDamage > maxDamage){
                return null;
            }else {
                return itemStack.setItemDamage(resultDamage);
            }
        }
        return null;
    }

    public ItemStack getAxeItem() {
        return this.axe.d();
    }

    public ItemStack getHammerItem() {
        return this.hammer.d();
    }

    public float getEffectivityFactorFromTool(){
        if (this.axe.d() != null && this.hammer.d() != null){
            return 0.25f + this.axe.d().b().getMaterialForRepairs().getMinHarvestLevel() / 5f + this.hammer.d().b().getMaterialForRepairs().getMinHarvestLevel() / 4f;
        }
        return 1;
    }

    public int getForgingTime(ForgingRecipe recipe) {
        return Math.round(recipe.getTimeReq() / this.getEffectivityFactorFromTool());
    }

    public void costItems(ForgingRecipe recipe){
        List<Slot> currentMaterials = Lists.newArrayList(this.up,this.left,this.right,this.downLeft,this.downRight);
        List<ItemStack> materialsRequired = Lists.newArrayList(recipe.getMaterialsToUpgrade());
        for (Slot current : currentMaterials) {
            for (ItemStack req : materialsRequired) {
                if (current.d() != null){
                    if (ItemStack.areItemStacksEqual(req,current.d(),true,false,false,true)){
                        int resultSize = current.d().b - req.b;
                        if (resultSize > 0){
                            current.d().setStackSize(resultSize);
                        }else {
                            current.c(null);
                        }
                    }
                }
            }
        }
    }
}
