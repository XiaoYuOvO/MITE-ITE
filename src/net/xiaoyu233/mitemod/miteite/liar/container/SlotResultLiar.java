package net.xiaoyu233.mitemod.miteite.liar.container;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.block.Blocks;
import net.xiaoyu233.mitemod.miteite.item.Items;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

@Deceive(SlotResult.class)
public class SlotResultLiar {
    @Liar
    private EntityHuman b;
    @Liar
    private int c;

    protected void b(ItemStack par1ItemStack) {
        par1ItemStack.a(this.b.q, this.b, this.c);
        this.c = 0;
        Item item = par1ItemStack.b();
        Block block = item instanceof ItemBlock ? ((ItemBlock) item).getBlock() : null;
        if (block instanceof BlockFurnace && ((BlockFurnace) block).isOven()) {
            this.b.a(AchievementList.buildOven, 1);
        } else if (par1ItemStack.d == Block.aD.cF) {
            Material tool_material = BlockWorkbench.getToolMaterial(par1ItemStack.getItemSubtype());
            if (tool_material.isMetal()) {
                this.b.a(AchievementList.betterTools, 1);
            } else {
                this.b.a(AchievementList.h, 1);
            }
        } else if (block == Block.av) {
            this.b.a(AchievementList.buildTorches, 1);
        } else if (item == Item.pickaxeCopper || item == Item.pickaxeSilver || item == Item.K) {
            if (!this.b.q.I) {
                DedicatedServer.checkForTournamentWinner(this.b, EnumTournamentType.pickaxe);
            }

            this.b.a(AchievementList.i, 1);
        } else if (par1ItemStack.d == Block.aG.cF) {
            this.b.a(AchievementList.j, 1);
        } else if (par1ItemStack.d == Block.furnaceObsidianIdle.cF) {
            this.b.a(AchievementList.obsidianFurnace);
        } else if (par1ItemStack.d == Block.furnaceNetherrackIdle.cF) {
            this.b.a(AchievementList.netherrackFurnace);
        } else if (item instanceof ItemHoe || item instanceof ItemMattock) {
            this.b.a(AchievementList.l, 1);
        } else if (par1ItemStack.d == Item.bb.cv) {
            this.b.a(AchievementList.n, 1);
        } else if (item instanceof ItemTool && item.getAsTool().isEffectiveAgainstBlock(Block.au, 0)) {
            this.b.a(AchievementList.o, 1);
            if (this.b.q instanceof WorldServer) {
                this.b.q.N().fullfillVillageCondition(16, (WorldServer) this.b.q);
            }

            if (item.getAsTool().isEffectiveAgainstBlock(Block.blockMithril, 0)) {
                this.b.a(AchievementList.crystalBreaker);
            }
        } else if (item == Item.hatchetFlint || item == Item.knifeFlint) {
            this.b.a(AchievementList.cuttingEdge, 1);
        } else if (item == Item.clubWood) {
            this.b.a(AchievementList.buildClub, 1);
        } else if (item instanceof ItemAxe && !(item instanceof ItemHatchet)) {
            this.b.a(AchievementList.buildAxe, 1);
        } else if (par1ItemStack.d == Block.enchantmentTableEmerald.cF) {
            this.b.a(AchievementList.D, 1);
        } else if (par1ItemStack.d == Block.bJ.cF) {
            this.b.a(AchievementList.D, 1);
        } else if (par1ItemStack.d == Block.as.cF) {
            this.b.a(AchievementList.F, 1);
        } else if (item instanceof ItemShovel && !(item instanceof ItemMattock)) {
            this.b.a(AchievementList.buildShovel, 1);
        } else if (item instanceof ItemScythe) {
            this.b.a(AchievementList.buildScythe, 1);
        } else if (item instanceof ItemArmor && item.isChainMail()) {
            this.b.a(AchievementList.buildChainMail, 1);
        } else if (item instanceof ItemFishingRod) {
            this.b.a(AchievementList.fishingRod);
        } else if (item == Item.flour) {
            this.b.a(AchievementList.flour);
        } else if (item instanceof ItemBowl && (item == Item.bowlSalad || ItemBowl.isSoupOrStew(item))) {
            this.b.a(AchievementList.fineDining);
        }else if (item == Items.VIBRANIUM_INGOT){
            this.b.a(Achievements.vibraniumIngot);
        }else if (block == Blocks.anvilVibranium){
            this.b.a(Achievements.vibraniumAnvil);
        }


    }

}
