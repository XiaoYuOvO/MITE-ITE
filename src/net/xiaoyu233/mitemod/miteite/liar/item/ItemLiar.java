package net.xiaoyu233.mitemod.miteite.liar.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.item.ItemModifierTypes;
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

    public void addExpForTool(ItemStack stack, EntityHuman player, int exp) {
        stack.fixNBT();
        NBTTagCompound tagCompound = stack.e;
        if (tagCompound != null) {
            if (tagCompound.b("tool_exp")) {
                tagCompound.a("tool_exp", tagCompound.e("tool_exp") + exp);
                if (tagCompound.b("tool_level")) {
                    int currentLevel = tagCompound.e("tool_level");
                    int nextLevelExpReq = this.getExpReqForLevel(currentLevel + 1, this.isWeapon(stack.b()));
                    if (tagCompound.e("tool_exp") >= nextLevelExpReq) {
                        tagCompound.a("tool_level", currentLevel + 1);
                        tagCompound.a("tool_exp", 0);
                        if (!player.q.I) {
                            player.a(ChatMessage.e(
                                    "你的" + stack.getMITEStyleDisplayName() + "已升级,当前等级:" + (currentLevel + 1)).a(
                                    EnumChatFormat.d));
                        }
                        if (!tagCompound.b("modifiers")) {
                            tagCompound.a("modifiers", new NBTTagCompound());
                        }
                        this.onItemLevelUp(tagCompound,player,stack);
                    }
                }
            }
        }else {
            NBTTagCompound compound = new NBTTagCompound();
            compound.a("tool_exp",0);
            compound.a("tool_level",0);
            stack.e = compound;
        }
    }

    public int addModifierLevelFor(NBTTagCompound modifiers, ItemModifierTypes<?> modifierType){
        int effectLevel = modifiers.e(modifierType.getNbtName()) + 1;
        modifiers.a(modifierType.getNbtName(), effectLevel);
        return effectLevel;
    }

    public void onItemLevelUp(NBTTagCompound tagCompound,EntityHuman player,ItemStack stack){}

    public int getExpReqForLevel(int i, boolean weapon){
        return 0;
    }

    public boolean isWeapon(Item b){
        return false;
    }

    public Multimap<String,AttributeModifier> getAttrModifiers(ItemStack stack) {
        return HashMultimap.create();
    }

    public float getMeleeDamageBonus(ItemStack stack){
        return 0.0F;
    }
    public float getStrVsBlock(Block block, int metadata,ItemStack itemStack,EntityHuman user) {
        return 0.0F;
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

    public boolean hasExpAndLevel(){
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
