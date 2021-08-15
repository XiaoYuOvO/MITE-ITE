package net.xiaoyu233.mitemod.miteite.trans.item.recipe;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RecipesArmor.class)
public abstract class RecipesArmorTrans {
    @Shadow
    private CraftingManager crafting_manager;

    @Shadow
    protected abstract void addBootsRecipe(Item boots, Item component);

    @Shadow
    protected abstract void addCuirassRecipe(Item cuirass, Item component);

    @Shadow
    protected abstract void addHelmetRecipe(Item helmet, Item component);

    @Shadow
    protected abstract void addLeggingsRecipe(Item leggings, Item component);

    @Overwrite
    public void addRecipes(CraftingManager par1CraftingManager) {
        this.crafting_manager = par1CraftingManager;
        Item[] components = new Item[]{Item.leather, Item.chainCopper, Item.chainSilver, Item.chainGold, Item.chainRustedIron, Item.chainIron, Item.chainMithril, Item.chainAdamantium, Item.chainAncientMetal, Item.ingotCopper, Item.ingotSilver, Item.ingotGold, Item.ingotIron, Item.ingotAncientMetal};

        for (Item component : components) {
            if (component.getMaterials().size() != 1) {
                Minecraft.setErrorMessage("addRecipes: armor components can have only 1 material (" + component + ")");
            }

            this.addHelmetRecipe(ItemArmor.getMatchingArmor(ItemHelmet.class, component.getExclusiveMaterial(), component instanceof ItemChain), component);
            this.addCuirassRecipe(ItemArmor.getMatchingArmor(ItemCuirass.class, component.getExclusiveMaterial(), component instanceof ItemChain), component);
            this.addLeggingsRecipe(ItemArmor.getMatchingArmor(ItemLeggings.class, component.getExclusiveMaterial(), component instanceof ItemChain), component);
            this.addBootsRecipe(ItemArmor.getMatchingArmor(ItemBoots.class, component.getExclusiveMaterial(), component instanceof ItemChain), component);
        }

        ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.helmetAdamantium),true,
                "AHA",
                "A A",
                'A',Item.ingotAdamantium,
                'H',Item.helmetAncientMetal);
        ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.plateAdamantium),true,
                "A A",
                "APA",
                "AAA",
                'A',Item.ingotAdamantium,
                'P',Item.plateAncientMetal);
        ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.legsAdamantium),true,
                "ALA",
                "A A",
                "A A",
                'A',Item.ingotAdamantium,
                'L',Item.legsAncientMetal);
        ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.bootsAdamantium),true,
                "B A",
                "A A",
                'A',Item.ingotAdamantium,
                'B',Item.bootsAncientMetal);


        //Iron -> Mithril
        ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.helmetMithril),true,
                "AAA",
                "AHA",
                'A',Item.ingotMithril,
                'H',Item.helmetIron).func_92100_c();
        ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.plateMithril),true,
                "APA",
                "AAA",
                "AAA",
                'A',Item.ingotMithril,
                'P',Item.plateIron).func_92100_c();
        ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.legsMithril),true,
                "AAA",
                "ALA",
                "A A",
                'A',Item.ingotMithril,
                'L',Item.legsIron).func_92100_c();
        ((CraftingManagerInvoker)this.crafting_manager).addRecipeP(new ItemStack(Item.bootsMithril),true,
                "ABA",
                "A A",
                'A',Item.ingotMithril,
                'B',Item.bootsIron).func_92100_c();
    }
}
