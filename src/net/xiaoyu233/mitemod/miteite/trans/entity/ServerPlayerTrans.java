package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.util.ReflectHelper;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerChestMinecart;
import net.xiaoyu233.mitemod.miteite.inventory.container.ContainerForgingTable;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerTrans extends EntityPlayer implements ICrafting {
   @Shadow
   @Final
   public List destroyedItemsNetCache;
   @Shadow
   @Final
   public List loadedChunks;
   @Shadow
   public double managedPosX;
   @Shadow
   public double managedPosZ;
   @Shadow
   public MinecraftServer mcServer;
   @Shadow
   public PlayerConnection playerNetServerHandler;
   @Shadow
   public PlayerInteractManager theItemInWorldManager;
   @Shadow
   private int currentWindowId;
   @Shadow
   private float field_130068_bO;
   @Shadow
   private float lastHealth;
   @Shadow
   private int last_experience;
   @Shadow
   private int last_nutrition;
   private int last_phytonutrients;
   private int last_protein;
   @Shadow
   private int last_satiation;
   @Shadow
   private int phytonutrients;
   @Shadow
   private int protein;
   public ServerPlayerTrans(World par1World, String par2Str) {
      super(par1World, par2Str);
   }

   @Override
   @Shadow
   public boolean canCommandSenderUseCommand(int i, String s) {
      return false;
   }

   public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {
      if (this.openContainer != this.inventoryContainer) {
         this.closeScreen();
      }

      this.getNextWindowId();
      this.playerNetServerHandler.sendPacket(new Packet100OpenWindow(this.currentWindowId, 12, par1IInventory.getCustomNameOrUnlocalized(), par1IInventory.getSizeInventory(), par1IInventory.hasCustomName()));
      this.openContainer = new ContainerChestMinecart(this, par1IInventory);
      this.openContainer.windowId = this.currentWindowId;
//      this.openContainer.a(this);
      this.openContainer.onCraftGuiOpened(this);
   }

   public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots) {
      this.getNextWindowId();
      TileEntity tile_entity = this.worldObj.getBlockTileEntity(x, y, z);
      this.playerNetServerHandler.sendPacket((new Packet100OpenWindow(this.currentWindowId, 14, tile_entity.getCustomInvName(), 9, tile_entity.hasCustomName())).setCoords(x, y, z));
      this.openContainer = new ContainerForgingTable(slots, this, x, y, z);
      this.openContainer.windowId = this.currentWindowId;
      ReflectHelper.dyCast(ServerPlayer.class, this).updateCraftingInventory(this.openContainer, ((ContainerForgingTable)this.openContainer).getInventory());
//      this.openContainer.a(this);
      this.openContainer.onCraftGuiOpened(this);
   }

   @Override
   @Shadow
   public ChunkCoordinates getCommandSenderPosition() {
      return null;
   }

   @Shadow
   public INetworkManager getNetManager() {
      return null;
   }

   @Shadow protected abstract void getNextWindowId();

   @Shadow public abstract int getPhytonutrients();

   @Shadow public abstract int getProtein();

   @Overwrite
   public void onUpdateEntity() {
      try {
         super.onUpdate();

         for(int var1 = 0; var1 < this.inventory.getSizeInventory(); ++var1) {
            ItemStack var6 = this.inventory.getStackInSlot(var1);
            if (var6 != null && Item.itemsList[var6.itemID].isMap() && this.playerNetServerHandler.getNumChunkDataPackets() <= 5) {
               Packet var8 = ((ItemWorldMapBase)Item.itemsList[var6.itemID]).getUpdatePacket(var6, this.worldObj, this);
               if (var8 != null) {
                  this.playerNetServerHandler.sendPacket(var8);
               }
            }
         }

         float health = this.getHealth();
         int satiation = this.getSatiation();
         int nutrition = this.getNutrition();
         if (health != this.lastHealth || satiation != this.last_satiation || nutrition != this.last_nutrition || this.vision_dimming > 0.0F || this.phytonutrients != this.last_phytonutrients || this.protein != this.last_protein) {
            Packet8UpdateHealth par1Packet = new Packet8UpdateHealth(health, satiation, nutrition, this.vision_dimming);
            par1Packet.setPhytonutrients(this.phytonutrients);
            par1Packet.setProtein(this.protein);
            this.playerNetServerHandler.sendPacket(par1Packet);
            this.lastHealth = health;
            this.last_satiation = satiation;
            this.last_nutrition = nutrition;
            this.vision_dimming = 0.0F;
            this.last_phytonutrients = phytonutrients;
            this.last_protein = protein;
         }

         if (this.getHealth() + this.getAbsorptionAmount() != this.field_130068_bO) {
            this.field_130068_bO = this.getHealth() + this.getAbsorptionAmount();
            Collection var5 = this.getWorldScoreboard().func_96520_a(IScoreboardCriteria.health);

            for (Object o : var5) {
               ScoreboardObjective var9 = (ScoreboardObjective) o;
               this.getWorldScoreboard().func_96529_a(this.getEntityName(), var9).func_96651_a(Arrays.asList(this));
            }
         }

         if (this.experience != this.last_experience) {
            this.last_experience = this.experience;
            this.playerNetServerHandler.sendPacket(new Packet43SetExperience(this.experience));
         }

      } catch (Throwable var8) {
         CrashReport var2 = CrashReport.makeCrashReport(var8, "Ticking player");
         CrashReportSystemDetails var3 = var2.makeCategory("Player being ticked");
         this.addEntityCrashInfo(var3);
         throw new ReportedException(var2);
      }
   }

   @Shadow
   public void sendChatToPlayer(ChatMessage chatMessage) {
   }

   @Override
   @Shadow
   public void sendProgressBarUpdate(Container container, int i, int i1) {

   }

   @Override
   @Shadow
   public void sendSlotContents(Container container, int i, ItemStack itemStack) {

   }

   @Override
   @Shadow
   public void updateCraftingInventory(Container container, List list) {

   }
}
