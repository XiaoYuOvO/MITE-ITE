package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayer.class)
public abstract class RemotePlayerTrans extends bex {
   @Shadow
   private int delta_tournament_score;
   @Shadow
   private int delta_tournament_score_opacity;
   @Shadow
   private NetClientHandler netClientHandler;
   private int phytonutrients;
   private int protein;
   @Shadow
   private int tournament_score;

   public RemotePlayerTrans(Minecraft par1Minecraft, World par2World, PlayerNameSession par3Session, NetClientHandler par4NetClientHandler) {
      super(par1Minecraft, par2World, par3Session, 0);
   }

   @Shadow
   public void b(String par1Str) {
   }

   @Shadow
   public INetworkManager getNetManager() {
      return null;
   }

   public int getPhytonutrients() {
      return this.phytonutrients;
   }

   public void setPhytonutrients(int phytonutrients) {
      this.phytonutrients = phytonutrients;
   }

   public int getProtein() {
      return this.protein;
   }

   public void setProtein(int protein) {
      this.protein = protein;
   }
}
