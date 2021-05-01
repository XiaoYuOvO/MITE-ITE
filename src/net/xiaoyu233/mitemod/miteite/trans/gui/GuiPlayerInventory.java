package net.xiaoyu233.mitemod.miteite.trans.gui;

import net.minecraft.AchievementList;
import net.minecraft.EntityPlayer;
import net.minecraft.axp;
import net.minecraft.axv;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(axv.class)
public class GuiPlayerInventory extends axp {
   public GuiPlayerInventory(EntityPlayer par1EntityPlayer) {
      super(par1EntityPlayer.inventoryContainer);
      this.j = true;
      par1EntityPlayer.addStat(AchievementList.openInventory, 1);
      par1EntityPlayer.incrementStatForThisWorldFromClient(AchievementList.openInventory);
      par1EntityPlayer.sendPacket(new CPacketSyncItems());
   }

   @Shadow
   protected void a(float v, int i, int i1) {
   }
}
