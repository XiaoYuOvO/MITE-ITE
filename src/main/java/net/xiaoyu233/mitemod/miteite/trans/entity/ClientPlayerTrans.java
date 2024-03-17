package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEMaterial;
import net.xiaoyu233.mitemod.miteite.api.ITEPlayer;
import net.xiaoyu233.mitemod.miteite.gui.GuiForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import static net.minecraft.ClientPlayer.calcUnmodifiedCraftingPeriod;

@Mixin(ClientPlayer.class)
public abstract class ClientPlayerTrans extends AbstractClientPlayer implements ITEPlayer {
   @Shadow
   protected Minecraft mc;

   public ClientPlayerTrans(World par1World, String par2Str) {
      super(par1World, par2Str);
   }

   @Shadow
   public void sendChatToPlayer(ChatMessageComponent chatMessage) {
   }

   @Shadow
   public boolean canCommandSenderUseCommand(int i, String s) {
      return false;
   }

   @Shadow
   public ChunkCoordinates getPlayerCoordinates() {
      return null;
   }

   @Shadow protected abstract float getBenchAndToolsModifier(Container container);

   @Override
   public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
      this.mc.displayGuiScreen(new GuiChest(this, par1IInventory));
   }

   @Override
   public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots) {
      this.mc.displayGuiScreen(new GuiForgingTable(this, x, y, z, slots));
   }

   @Inject(method = "getBenchAndToolsModifier", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/BlockWorkbench;getToolMaterial(I)Lnet/minecraft/Material;", shift = At.Shift.AFTER), cancellable = true,
           locals = LocalCapture.CAPTURE_FAILHARD
   )
   private void injectModifyWorkbenchModifier(Container container, CallbackInfoReturnable<Float> cir, ContainerWorkbench container_workbench, SlotCrafting slot_crafting, ItemStack item_stack, Item item, IRecipe recipe, Material material_to_check_tool_bench_hardness_against, Material benchMaterial) {
      if (benchMaterial.getMinHarvestLevel() < material_to_check_tool_bench_hardness_against.getMinHarvestLevel()) {
         cir.setReturnValue(0.0F);
      } else if (benchMaterial == Material.flint || benchMaterial == Material.obsidian) {
         cir.setReturnValue(0.0F);
      } else if (benchMaterial == Material.copper || benchMaterial == Material.silver || benchMaterial == Material.gold) {
         cir.setReturnValue(0.1F);
      } else if (benchMaterial == Material.iron) {
         cir.setReturnValue(0.2F);
      } else if (benchMaterial == Material.ancient_metal) {
         cir.setReturnValue(0.3F);
      } else if (benchMaterial == Material.mithril) {
         cir.setReturnValue(0.3F);
      } else if (benchMaterial == Material.adamantium) {
         cir.setReturnValue(0.4F);
      } else if (benchMaterial == Materials.vibranium) {
         cir.setReturnValue(0.55F);
      } else {
         Minecraft.setErrorMessage("getBenchAndToolsModifier: unrecognized tool material " + benchMaterial);
         cir.setReturnValue(0.0F);
      }
   }

   @Inject(method = "getCraftingPeriod", at = @At("HEAD"), cancellable = true)
   public void injectModifyCraftingPeriod(float quality_adjusted_crafting_difficulty, CallbackInfoReturnable<Integer> cir) {
      int period = calcUnmodifiedCraftingPeriod(quality_adjusted_crafting_difficulty);
      if (this.hasCurse(Curse.clumsiness)) {
         period *= 2;
      }
      float bench_and_tools_modifier = this.getBenchAndToolsModifier(this.openContainer);
      cir.setReturnValue(Math.round(Math.max((float)period * (1.0F - bench_and_tools_modifier) * (1.0f - Math.min(this.getExperienceLevel(),100) * 0.006f), 1) / (this.getCraftingBoostFactor() + 1.0F)));
   }
}
