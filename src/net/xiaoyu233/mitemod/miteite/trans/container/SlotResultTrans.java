package net.xiaoyu233.mitemod.miteite.trans.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SlotResult.class)
public class SlotResultTrans {
   @Shadow
   private int amountCrafted;
   @Shadow
   private EntityPlayer thePlayer;

   @Shadow public CraftingResult crafting_result;

   @Redirect(method = "modifyStackForRightClicks",at = @At(value = "INVOKE",target = "Lnet/minecraft/ItemStack;setQuality(Lnet/minecraft/EnumQuality;)Lnet/minecraft/ItemStack;"))
   private ItemStack redirectRemoveSetQuality(ItemStack caller, EnumQuality quality){
      //Do nothing to remove it!
      return caller;
   }

   @Redirect(method = "canPlayerCraftItem",at = @At(value = "INVOKE",target = "Lnet/minecraft/InventoryCrafting;hasDamagedItem()Z"))
   private boolean removeDamageLimitation(InventoryCrafting caller){
      aah recipe = this.crafting_result.recipe;
      if (recipe instanceof ShapedRecipes){
         return !(((ShapedRecipes) recipe).isExtendsNBT()) && caller.hasDamagedItem();
      }else if (recipe instanceof ShapelessRecipes){
         return !((ShapelessRecipes) recipe).isExtendsNBT() && caller.hasDamagedItem();
      }
      return caller.hasDamagedItem();
   }

   @Overwrite
   protected void onCrafting(ItemStack par1ItemStack) {
      par1ItemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
      this.amountCrafted = 0;
      Item item = par1ItemStack.getItem();
      Block block = item instanceof ItemBlock ? ((ItemBlock)item).getBlock() : null;
      if (block instanceof BlockFurnace && ((BlockFurnace)block).isOven()) {
         this.thePlayer.addStat(AchievementList.buildOven, 1);
      } else if (par1ItemStack.itemID == Block.workbench.blockID) {
         Material tool_material = BlockWorkbench.getToolMaterial(par1ItemStack.getItemSubtype());
         if (tool_material.isMetal()) {
            this.thePlayer.addStat(AchievementList.betterTools, 1);
         } else {
            this.thePlayer.addStat(AchievementList.buildWorkBench, 1);
         }
      } else if (block == Block.torchWood) {
         this.thePlayer.addStat(AchievementList.buildTorches, 1);
      } else if (item != Item.pickaxeCopper && item != Item.pickaxeSilver && item != Item.pickaxeGold) {
         if (par1ItemStack.itemID == Block.furnaceIdle.blockID) {
            this.thePlayer.addStat(AchievementList.buildFurnace, 1);
         } else if (par1ItemStack.itemID == Block.furnaceObsidianIdle.blockID) {
            this.thePlayer.triggerAchievement(AchievementList.obsidianFurnace);
         } else if (par1ItemStack.itemID == Block.furnaceNetherrackIdle.blockID) {
            this.thePlayer.triggerAchievement(AchievementList.netherrackFurnace);
         } else if (!(item instanceof ItemHoe) && !(item instanceof ItemMattock)) {
            if (par1ItemStack.itemID == Item.cake.itemID) {
               this.thePlayer.addStat(AchievementList.bakeCake, 1);
            } else if (item instanceof ItemTool && item.getAsTool().isEffectiveAgainstBlock(Block.obsidian, 0)) {
               this.thePlayer.addStat(AchievementList.buildBetterPickaxe, 1);
               if (this.thePlayer.worldObj instanceof WorldServer) {
                  this.thePlayer.worldObj.getWorldInfo().fullfillVillageCondition(16, (WorldServer)this.thePlayer.worldObj);
               }

               if (item.getAsTool().isEffectiveAgainstBlock(Block.blockMithril, 0)) {
                  this.thePlayer.triggerAchievement(AchievementList.crystalBreaker);
               }
            } else if (item != Item.hatchetFlint && item != Item.knifeFlint) {
               if (item == Item.clubWood) {
                  this.thePlayer.addStat(AchievementList.buildClub, 1);
               } else if (item instanceof ItemAxe && !(item instanceof ItemHatchet)) {
                  this.thePlayer.addStat(AchievementList.buildAxe, 1);
               } else if (par1ItemStack.itemID != Block.enchantmentTable.blockID && par1ItemStack.itemID != Block.enchantmentTableEmerald.blockID) {
                  if (par1ItemStack.itemID == Block.bookShelf.blockID) {
                     this.thePlayer.addStat(AchievementList.bookcase, 1);
                  } else if (item instanceof ItemShovel && !(item instanceof ItemMattock)) {
                     this.thePlayer.addStat(AchievementList.buildShovel, 1);
                  } else if (item instanceof ItemScythe) {
                     this.thePlayer.addStat(AchievementList.buildScythe, 1);
                  } else if (item instanceof ItemArmor && item.isChainMail()) {
                     this.thePlayer.addStat(AchievementList.buildChainMail, 1);
                  } else if (item instanceof ItemFishingRod) {
                     this.thePlayer.triggerAchievement(AchievementList.fishingRod);
                  } else if (item == Item.flour) {
                     this.thePlayer.triggerAchievement(AchievementList.flour);
                  } else if (item instanceof ItemBowl && (item == Item.bowlSalad || ItemBowl.isSoupOrStew(item))) {
                     this.thePlayer.triggerAchievement(AchievementList.fineDining);
                  } else if (item == Items.VIBRANIUM_INGOT) {
                     this.thePlayer.triggerAchievement(Achievements.vibraniumIngot);
                  } else if (block == Blocks.anvilVibranium) {
                     this.thePlayer.triggerAchievement(Achievements.vibraniumAnvil);
                  }
               } else {
                  this.thePlayer.addStat(AchievementList.enchantments, 1);
               }
            } else {
               this.thePlayer.addStat(AchievementList.cuttingEdge, 1);
            }
         } else {
            this.thePlayer.addStat(AchievementList.buildHoe, 1);
         }
      } else {
         if (!this.thePlayer.worldObj.isRemote) {
            DedicatedServer.checkForTournamentWinner(this.thePlayer, EnumTournamentType.pickaxe);
         }

         this.thePlayer.addStat(AchievementList.buildPickaxe, 1);
      }

   }
}
