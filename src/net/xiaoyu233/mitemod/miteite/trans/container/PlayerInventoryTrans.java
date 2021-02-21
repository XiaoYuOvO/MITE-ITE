package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;

@Transform(PlayerInventory.class)
public class PlayerInventoryTrans {

    @Link
    public ItemStack[] a;
    @Link
    public ItemStack[] b;
    @Link
    public int c;
    @Link
    private ItemStack f;
    @Link
    public EntityHuman d;
    @Link
    private ItemStack g;
    @Link
    public boolean e;

    public boolean trySwitchItemOrRestock(Item previous_item, int item_subtype, boolean immediately) {
        this.d.sendPacket(new CPacketSyncItems());
        if (!this.d.q.I) {
            Minecraft.setErrorMessage("trySwitchItemOrRestock is only meant to be called on client");
            return false;
        } else if (previous_item == null) {
            Minecraft.setErrorMessage("trySwitchItemOrRestock: previous_item is null");
            return false;
        } else {
            bdc player_controller = Minecraft.O.c;
            if (immediately) {
                player_controller.item_switch_or_restock_pending = false;
                int slot_index = this.getNextHotbarOrInventorySlotContainingMostSimilarItem(previous_item, item_subtype, this.c);
                if (slot_index == -1) {
                    return false;
                } else if (slot_index == this.c) {
                    return false;
                } else if (slot_index < PlayerInventory.i()) {
                    this.c = slot_index;
                    ((bdi)this.d).change_rendering_for_item_equipping = true;
                    player_controller.k();
                    return true;
                } else if (!player_controller.autoStockEnabled()) {
                    return false;
                } else if (this.a[this.c] != null) {
                    return false;
                } else {
                    ItemStack item_stack = this.a(slot_index);
                    this.a(this.c, item_stack);
                    this.a(slot_index, null);
                    player_controller.sendPacket(new Packet5EntityEquipment(this.d.k, this.c, item_stack));
                    player_controller.sendPacket(new Packet5EntityEquipment(this.d.k, slot_index, null));
                    player_controller.setLastUsedItem(null, 0);
                    return true;
                }
            } else {
                player_controller.setLastUsedItem(previous_item, item_subtype);
                player_controller.item_switch_or_restock_pending = true;
                return false;
            }
        }
    }
    @Marker
    public int getNextHotbarOrInventorySlotContainingMostSimilarItem(Item item, int item_subtype, int hotbar_slot_index){
        return 0;
    }
    @Marker
    public void a(int par1, ItemStack par2ItemStack) {}
    @Marker
    public ItemStack a(int par1) {
        return null;
    }
}
