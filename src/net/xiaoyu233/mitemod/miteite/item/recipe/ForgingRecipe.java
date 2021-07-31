package net.xiaoyu233.mitemod.miteite.item.recipe;

import com.google.common.collect.Lists;
import net.minecraft.EnumQuality;
import net.minecraft.ItemStack;
import net.minecraft.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ForgingRecipe {
    private final List<IFaultFeedback> faultFeedback;
    private final List<ItemStack> materialsToUpgrade;
    private final Material material;
    private final int levelToUpgrade;
    private final int timeReq;
    private final int hammerDurabilityCost;
    private final int axeDurabilityCost;
    private final ForgingTableLevel forgingTableLevelReq;
    private final EnumQuality qualityReward;
    //Out of 100
    private final int chanceOfFailure;

    public ForgingRecipe(List<IFaultFeedback> faultFeedback, List<ItemStack> materialsToUpgrade, Material material, int levelToUpgrade, int timeReq, int hammerDurabilityCost, int axeDurabilityCost, int chanceOfFailure, ForgingTableLevel forgingTableLevelReq, EnumQuality qualityReward) {
        this.faultFeedback = faultFeedback;
        this.materialsToUpgrade = materialsToUpgrade;
        this.material = material;
        this.levelToUpgrade = levelToUpgrade;
        this.timeReq = timeReq;
        this.hammerDurabilityCost = hammerDurabilityCost;
        this.axeDurabilityCost = axeDurabilityCost;
        this.chanceOfFailure = chanceOfFailure;
        this.forgingTableLevelReq = forgingTableLevelReq;
        this.qualityReward = qualityReward;
    }

    public EnumQuality getQualityReward() {
        return qualityReward;
    }

    public int getAxeDurabilityCost() {
        return axeDurabilityCost;
    }

    public int getHammerDurabilityCost() {
        return hammerDurabilityCost;
    }

    public int getLevelToUpgrade() {
        return levelToUpgrade;
    }

    public int getTimeReq() {
        return timeReq;
    }

    public Material getMaterial() {
        return material;
    }

    public List<ItemStack> getMaterialsToUpgrade() {
        return materialsToUpgrade;
    }

    public List<IFaultFeedback> getFaultFeedback() {
        return faultFeedback;
    }

    public int getChanceOfFailure() {
        return chanceOfFailure;
    }

    public ForgingTableLevel getForgingTableLevelReq() {
        return forgingTableLevelReq;
    }

    public static class Builder{
        private List<IFaultFeedback> faultFeedback = new ArrayList<>();
        private final List<ItemStack> materialsToUpgrade = new ArrayList<>(5);
        private final Material material;
        private final ForgingTableLevel forgingTableLevelReq;
        private final int levelToUpgrade;
        private int timeReq;
        private int hammerDurabilityCost;
        private int axeDurabilityCost;
        private int chanceOfFailure;
        private EnumQuality qualityReward = null;

        private Builder(Material material, int levelToUpgrade,ForgingTableLevel forgingTableLevelReq) {
            this.material = material;
            this.forgingTableLevelReq = forgingTableLevelReq;
            this.levelToUpgrade = levelToUpgrade;
        }

        public static Builder of(Material material, int levelToUpgrade,ForgingTableLevel forgingTableLevelReq){
            return new Builder(material, levelToUpgrade,forgingTableLevelReq);
        }

        public Builder setTimeReq(int timeReq){
            this.timeReq = timeReq;
            return this;
        }

        public Builder setQualityReward(EnumQuality qualityReward) {
            this.qualityReward = qualityReward;
            return this;
        }

        public Builder setAxeDurabilityCost(int axeDurabilityCost) {
            this.axeDurabilityCost = axeDurabilityCost;
            return this;
        }

        public Builder setHammerDurabilityCost(int hammerDurabilityCost) {
            this.hammerDurabilityCost = hammerDurabilityCost;
            return this;
        }

        public Builder addMaterials(ItemStack... itemStacks){
            this.materialsToUpgrade.addAll(Lists.newArrayList(itemStacks));
            if (this.materialsToUpgrade.size() > 5){
                throw new IllegalArgumentException("Total materials count must not bigger than 5");
            }
            return this;
        }

        public Builder addFaultFeedback(IFaultFeedback faultFeedback) {
            this.faultFeedback.add(faultFeedback);
            return this;
        }

        public Builder setChanceOfFailure(int chanceOfFailure) {
            this.chanceOfFailure = chanceOfFailure;
            return this;
        }

        public void build(Consumer<ForgingRecipe> registerer){
            registerer.accept(new ForgingRecipe(faultFeedback, materialsToUpgrade, material,levelToUpgrade,timeReq,hammerDurabilityCost,axeDurabilityCost,chanceOfFailure, forgingTableLevelReq, qualityReward));
        }
    }

}

