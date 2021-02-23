package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.gui.GuiForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;

import static net.minecraft.bex.calcUnmodifiedCraftingPeriod;
import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

@Transform(bex.class)
public class ClientPlayerTrans extends beu{
    @Link
    protected Minecraft d;

    @Marker
    public ClientPlayerTrans(World par1World, String par2Str) {
        super(par1World, par2Str);
    }

    public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots){
        this.d.a(new GuiForgingTable(dyCast(this), x, y, z,slots));
    }

    public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
        this.d.a(new axj(dyCast(this), par1IInventory));
    }

    public int getCraftingPeriod(float quality_adjusted_crafting_difficulty) {
        int period = calcUnmodifiedCraftingPeriod(quality_adjusted_crafting_difficulty);
        if (this.hasCurse(Curse.clumsiness)) {
            period *= 2;
        }

        float bench_and_tools_modifier = this.getBenchAndToolsModifier(this.bp);
        return Math.round(Math.max((float)period / (1.0F + this.getLevelModifier(EnumLevelBonus.CRAFTING) + bench_and_tools_modifier), 25.0F) / (this.getCraftingBoostFactor() + 1)) ;
    }

    @Marker
    public float getLevelModifier(EnumLevelBonus kind){
        return 0;
    }

    @Marker
    public boolean hasCurse(Curse curse) {
        return false;
    }

    @Override
    @Marker
    public INetworkManager getNetManager() {
        return null;
    }

    @Marker
    private float getBenchAndToolsModifier(Container container){
        return 0;
    }

    @Override
    @Marker
    public void a(ChatMessage chatMessage) {

    }

    @Override
    @Marker
    public boolean a(int i, String s) {
        return false;
    }

    @Override
    @Marker
    public ChunkCoordinates b() {
        return null;
    }
}
