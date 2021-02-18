package net.xiaoyu233.mitemod.miteite.item.recipe;

import com.google.common.collect.Lists;
import net.minecraft.ItemStack;
import net.minecraft.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ForgingRecipe {
    private final IFaultFeedback faultFeedback;
    private final List<ItemStack> materialsToUpgrade;
    private final Material material;
    private final int levelToUpgrade;
    private final int timeReq;
    private final int hammerDurabilityCost;
    private final int axeDurabilityCost;
    //Out of 100
    private final int chanceOfFailure;

    public ForgingRecipe(IFaultFeedback faultFeedback, List<ItemStack> materialsToUpgrade, Material material, int levelToUpgrade, int timeReq, int hammerDurabilityCost, int axeDurabilityCost, int chanceOfFailure) {
        this.faultFeedback = faultFeedback;
        this.materialsToUpgrade = materialsToUpgrade;
        this.material = material;
        this.levelToUpgrade = levelToUpgrade;
        this.timeReq = timeReq;
        this.hammerDurabilityCost = hammerDurabilityCost;
        this.axeDurabilityCost = axeDurabilityCost;
        this.chanceOfFailure = chanceOfFailure;
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

    public IFaultFeedback getFaultFeedback() {
        return faultFeedback;
    }

    public int getChanceOfFailure() {
        return chanceOfFailure;
    }

    public static class Builder{
        private IFaultFeedback faultFeedback;
        private final List<ItemStack> materialsToUpgrade = new ArrayList<>(5);
        private final Material material;
        private final int levelToUpgrade;
        private int timeReq;
        private int hammerDurabilityCost;
        private int axeDurabilityCost;
        private int chanceOfFailure;

        private Builder(Material material, int levelToUpgrade) {
            this.material = material;
            this.levelToUpgrade = levelToUpgrade;
        }

        public static Builder of(Material material, int levelToUpgrade){
            return new Builder(material, levelToUpgrade);
        }

        public Builder setTimeReq(int timeReq){
            this.timeReq = timeReq;
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

        public Builder setFaultFeedback(IFaultFeedback faultFeedback) {
            this.faultFeedback = faultFeedback;
            return this;
        }

        public Builder setChanceOfFailure(int chanceOfFailure) {
            this.chanceOfFailure = chanceOfFailure;
            return this;
        }

        public void build(Consumer<ForgingRecipe> registerer){
            registerer.accept(new ForgingRecipe(faultFeedback, materialsToUpgrade, material,levelToUpgrade,timeReq,hammerDurabilityCost,axeDurabilityCost,chanceOfFailure));
        }
    }

}

