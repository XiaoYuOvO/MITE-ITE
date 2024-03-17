package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.api.ITEPlayer;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerChestMinecart;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.network.BiPacketUpdateDefense;
import net.xiaoyu233.mitemod.miteite.network.SPacketUpdateNutrition;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerTrans extends EntityPlayer implements ICrafting, ITEPlayer {
   @Shadow
   public NetServerHandler playerNetServerHandler;
   @Shadow
   private int currentWindowId;
   @Unique
   private int last_phytonutrients;
   @Unique
   private int last_protein;
   @Shadow
   private int phytonutrients;
   @Shadow
   private int protein;
   public ServerPlayerTrans(World par1World, String par2Str) {
      super(par1World, par2Str);
   }

   public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
      if (this.openContainer != this.inventoryContainer) {
         this.closeScreen();
      }
      this.incrementWindowID();
      this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 12, par1IInventory.getCustomNameOrUnlocalized(), par1IInventory.getSizeInventory(), par1IInventory.hasCustomName()));
      this.openContainer = new ContainerChestMinecart(this, par1IInventory);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.addCraftingToCrafters(this);
   }

   public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots) {
      this.incrementWindowID();
      TileEntity tile_entity = this.worldObj.getBlockTileEntity(x, y, z);
      this.playerNetServerHandler.sendPacketToPlayer((new Packet100OpenWindow(this.currentWindowId, 14, tile_entity.getCustomInvName(), 9, tile_entity.hasCustomName())).setCoords(x, y, z));
      this.openContainer = new ContainerForgingTable(slots, this, x, y, z);
      this.openContainer.windowId = this.currentWindowId;
      ReflectHelper.dyCast(ServerPlayer.class, this).sendContainerAndContentsToPlayer(this.openContainer, ((ContainerForgingTable)this.openContainer).getInventory());
      this.openContainer.addCraftingToCrafters(this);
   }

   @Shadow protected abstract void incrementWindowID();

   @Override
   public void setDefenseCooldown(int cooldown) {
      super.setDefenseCooldown(cooldown);
      if (playerNetServerHandler != null) {
         this.playerNetServerHandler.sendPacketToPlayer(new BiPacketUpdateDefense(cooldown));
      }
   }

   @Inject(method = "onUpdateEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/ServerPlayer;getNutrition()I", shift = At.Shift.AFTER))
   private void injectUpdateNurtrition(CallbackInfo ci){
      if (this.phytonutrients != last_phytonutrients || this.protein != last_protein){
         this.playerNetServerHandler.sendPacketToPlayer(new SPacketUpdateNutrition(phytonutrients, protein));
         this.last_phytonutrients = phytonutrients;
         this.last_protein = protein;
      }
   }
}
