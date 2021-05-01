package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.gui.GuiForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(bex.class)
public abstract class ClientPlayerTrans extends beu {
   @Shadow
   protected Minecraft d;

   public ClientPlayerTrans(World par1World, String par2Str) {
      super(par1World, par2Str);
   }

   @Shadow
   public void a(ChatMessage chatMessage) {
   }

   @Shadow
   public boolean a(int i, String s) {
      return false;
   }

   @Shadow
   public ChunkCoordinates b() {
      return null;
   }

   public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
      this.d.a(new axj(ReflectHelper.dyCast(this), par1IInventory));
   }

   public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots) {
      this.d.a(new GuiForgingTable(ReflectHelper.dyCast(this), x, y, z, slots));
   }

   @Shadow
   private float getBenchAndToolsModifier(Container container) {
      return 0.0F;
   }

   @Overwrite
   public int getCraftingPeriod(float quality_adjusted_crafting_difficulty) {
      int period = bex.calcUnmodifiedCraftingPeriod(quality_adjusted_crafting_difficulty);
      if (this.hasCurse(Curse.clumsiness)) {
         period *= 2;
      }

      float bench_and_tools_modifier = this.getBenchAndToolsModifier(this.openContainer);
      return Math.round(Math.max((float)period / (1.0F + this.getLevelModifier(EnumLevelBonus.CRAFTING) + bench_and_tools_modifier), 25.0F) / (this.getCraftingBoostFactor() + 1.0F));
   }
}
