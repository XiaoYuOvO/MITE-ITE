package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.EntityMinecartChest;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.util.BlockPos;

public interface ITEPlayer {
    default void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots){
        throw new IllegalStateException("Should be implemented in Mixin");
    };

    default void setCraftingBoostFactor(float v, BlockPos currentPos){
        throw new IllegalStateException("Should be implemented in Mixin");
    };

    default void setCraftingBoostTimer(int i){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default float getCraftingBoostFactor(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default boolean canDefense(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default void setDefenseCooldown(int cooldown){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
    default void itemsSynced(){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
