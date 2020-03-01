package net.xiaoyu233.mitemod.miteite.liar.item;

import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.Items;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

import static net.minecraft.Item.*;
import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.dyCast;

@Deceive(Item.class)
public class ItemLiar{
    @Liar
    private int cv;

    public int getHeatLevel(ItemStack item_stack) {
        if (dyCast(this) == Items.BLAZE_COAL){
            return 5;
        }else if (dyCast(this) == bq) {
            return 4;
        } else {
            return this.getBurnTime(item_stack) > 0 ? 1 : 0;
        }
    }

    public int getCookTime(){
        if (this.cv == Block.cE.cF){
            return 2000;
        }else if (this.isBlock()){
            return 200 * (getAsItemBlock().getBlock().getMinHarvestLevel(-1) + 1);
        }else{
            return 200;
        }
    }

    @Stealing
    public boolean isBlock() {
//        return dyCast(this instanceof ItemBlock;
        return false;
    }

    @Stealing
    public ItemBlock getAsItemBlock() {
        return null;
    }

    @Stealing
    private int getBurnTime(ItemStack item_stack) {
        return 0;
    }

    public Item getRepairItem() {
        Material material_for_repairs = this.getMaterialForRepairs();
        if (material_for_repairs == Material.copper) {
            return copperNugget;
        } else if (material_for_repairs == Material.silver) {
            return silverNugget;
        } else if (material_for_repairs == Material.gold) {
            return bs;
        } else if (material_for_repairs != Material.f && material_for_repairs != Material.rusted_iron) {
            if (material_for_repairs == Material.mithril) {
                return mithrilNugget;
            } else if (material_for_repairs == Material.adamantium) {
                return adamantiumNugget;
            } else {
                if (material_for_repairs == Material.ancient_metal) {
                    return ancientMetalNugget;
                }else if (material_for_repairs == Materials.vibranium){
                    return Items.VIBRANIUM_NUGGET;
                }else {
                    return null;
                }
            }
        } else {
            return ironNugget;
        }
    }

    @Stealing
    private Material getMaterialForRepairs() {
        return null;
    }

    @Liar
    private static Item[] g;

    public static int getNextItemID() {
        return Constant.nextItemID++;
    }

    public String getResourceLocationPrefix() {
        return "";
    }

    public void setResourceLocation(String location){
        this.d(location);
    }

    @Stealing
    public Item d(String location) {
        return null;
    }

    public void setCreativeTable(CreativeModeTab table){
        this.a(table);
    }

    @Stealing
    public Item a(CreativeModeTab table){
        return null;
    }

    public void setMaxStackSize(int maxStackSize){
        this.d(maxStackSize);
    }

    @Stealing
    public Item d(int maxStackSize) {
        return null;
    }
}
