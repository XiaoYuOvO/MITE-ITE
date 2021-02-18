package net.xiaoyu233.mitemod.miteite.item.recipe;

import com.google.common.base.Objects;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.ItemStack;
import net.minecraft.Material;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;

import java.util.function.Consumer;

public class ForgingTableRecipes {
    private static final BiMap<RecipeKey,ForgingRecipe> RECIPES = HashBiMap.create();
    private static final Consumer<ForgingRecipe> regiseterer = forgingRecipe -> {
        RecipeKey key = new RecipeKey(forgingRecipe.getMaterial(),forgingRecipe.getLevelToUpgrade());
        RECIPES.put(key,forgingRecipe);
    };

    public static ForgingRecipe getRecipe(Material toolItem, int forgingLevel){
        return RECIPES.get(new RecipeKey(toolItem,forgingLevel));
    }

    public static void registerAll(){
        ForgingRecipe.Builder.of(Materials.vibranium,0).
                setChanceOfFailure(0).
                setAxeDurabilityCost(2048).
                setHammerDurabilityCost(2048).
                setTimeReq(1200).
                setFaultFeedback(DurabilityFeedback.of(DurabilityFeedback.Type.ofPercentage(10))).
                addMaterials(new ItemStack(Items.br,1),
                        new ItemStack(Items.ingotMithril,1),
                        new ItemStack(Items.ENHANCE_STONE,1)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,1).
                setChanceOfFailure(10).
                setAxeDurabilityCost(2048).
                setHammerDurabilityCost(3072).
                setTimeReq(1800).
                setFaultFeedback(DurabilityFeedback.of(DurabilityFeedback.Type.ofPercentage(15))).
                addMaterials(new ItemStack(Items.br,2),
                        new ItemStack(Items.ingotMithril,1),
                        new ItemStack(Items.ENHANCE_STONE,1)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,2).
                setChanceOfFailure(20).
                setAxeDurabilityCost(3072).
                setHammerDurabilityCost(3072).
                setTimeReq(2400).
                setFaultFeedback(DurabilityFeedback.of(DurabilityFeedback.Type.ofPercentage(20))).
                addMaterials(new ItemStack(Items.br,2),
                        new ItemStack(Items.ingotMithril,2),
                        new ItemStack(Items.ENHANCE_STONE,1)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,3).
                setChanceOfFailure(30).
                setAxeDurabilityCost(3072).
                setHammerDurabilityCost(4096).
                setTimeReq(3000).
                setFaultFeedback(DurabilityFeedback.of(DurabilityFeedback.Type.ofPercentage(20))).
                addMaterials(new ItemStack(Items.br,2),
                        new ItemStack(Items.ingotMithril,2),
                        new ItemStack(Items.ENHANCE_STONE,2)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,4).
                setChanceOfFailure(40).
                setAxeDurabilityCost(4096).
                setHammerDurabilityCost(4096).
                setTimeReq(3600).
                setFaultFeedback(DowngradeFeedback.of(1)).
                addMaterials(new ItemStack(Items.br,2),
                        new ItemStack(Items.ingotAdamantium,2),
                        new ItemStack(Items.ENHANCE_STONE,2)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,5).
                setChanceOfFailure(50).
                setAxeDurabilityCost(4096).
                setHammerDurabilityCost(6144).
                setTimeReq(4800).
                setFaultFeedback(DowngradeFeedback.of(1)).
                addMaterials(new ItemStack(Items.br,1),
                        new ItemStack(Items.ingotAdamantium,2),
                        new ItemStack(Items.ENHANCE_STONE,2)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,6).
                setChanceOfFailure(60).
                setAxeDurabilityCost(6144).
                setHammerDurabilityCost(6144).
                setTimeReq(5400).
                setFaultFeedback(DowngradeFeedback.of(2)).
                addMaterials(new ItemStack(Items.br,2),
                        new ItemStack(Items.ingotAdamantium,2),
                        new ItemStack(Items.ENHANCE_STONE,2),
                        new ItemStack(Items.bp,8)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,7).
                setChanceOfFailure(70).
                setAxeDurabilityCost(6144).
                setHammerDurabilityCost(8192).
                setTimeReq(6000).
                setFaultFeedback(DowngradeFeedback.of(2)).
                addMaterials(new ItemStack(Items.br,2),
                        new ItemStack(Items.ingotAdamantium,4),
                        new ItemStack(Items.ENHANCE_STONE,2),
                        new ItemStack(Items.bC,8),
                        new ItemStack(Items.bS,1,1)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,7).
                setChanceOfFailure(70).
                setAxeDurabilityCost(8192).
                setHammerDurabilityCost(8192).
                setTimeReq(6000).
                setFaultFeedback(DowngradeFeedback.of(2)).
                addMaterials(new ItemStack(Items.br,2),
                        new ItemStack(Items.ingotAdamantium,2),
                        new ItemStack(Items.ENHANCE_STONE,2),
                        new ItemStack(Items.bS,2,2)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,8).
                setChanceOfFailure(80).
                setAxeDurabilityCost(8192).
                setHammerDurabilityCost(10240).
                setTimeReq(6600).
                setFaultFeedback(DowngradeFeedback.of(3)).
                addMaterials(new ItemStack(Items.br,4),
                        new ItemStack(Items.ingotAdamantium,2),
                        new ItemStack(Items.ENHANCE_STONE,2),
                        new ItemStack(Items.bS,2,0)).
                build(regiseterer);
        ForgingRecipe.Builder.of(Materials.vibranium,9).
                setChanceOfFailure(90).
                setAxeDurabilityCost(8192).
                setHammerDurabilityCost(10240).
                setTimeReq(7200).
                setFaultFeedback(DowngradeFeedback.of(4)).
                addMaterials(new ItemStack(Items.br,4),
                        new ItemStack(Items.ingotAdamantium,2),
                        new ItemStack(Items.ENHANCE_STONE,2),
                        new ItemStack(Items.bS,4,4),
                        new ItemStack(Items.BLAZE_COAL,2)).
                build(regiseterer);
    }

    public static class RecipeKey{
        private final Material toolItem;
        private final int levelToUpgrade;

        public RecipeKey(Material toolItem, int levelToUpgrade) {
            this.toolItem = toolItem;
            this.levelToUpgrade = levelToUpgrade;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RecipeKey key = (RecipeKey) o;
            return levelToUpgrade == key.levelToUpgrade && Objects.equal(toolItem, key.toolItem);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(toolItem, levelToUpgrade);
        }
    }
}
