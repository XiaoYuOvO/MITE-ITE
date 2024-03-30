package net.xiaoyu233.mitemod.miteite.util;

import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.Item;
import net.minecraft.ItemArmor;
import net.minecraft.ResourceLocation;
import net.xiaoyu233.fml.FishModLoader;
import net.xiaoyu233.fml.util.IdUtil;
import net.xiaoyu233.mitemod.miteite.item.Items;

public class Constant {
    public static final double[] ENHANCE_FACTORS;
    public static final ResourceLocation icons_ite = new ResourceLocation("textures/gui/icons_ite.png");
    public static final String MITE_ITE_VERSION = FishModLoader.getModContainer("mite_ite").map(ModContainer::getMetadata).map(ModMetadata::getVersion).map(Version::getFriendlyString).orElse("v?.?.?");
    public static final int CONFIG_VERSION = 1;
    public static final ResourceLocation RES_VIBRANIUM_SINGLE = new ResourceLocation("textures/entity/chest/vibranium_single.png");
    public static ItemArmor[] HELMETS = null;
    public static ItemArmor[] CHESTPLATES = null;
    public static ItemArmor[] LEGGINGS = null;
    public static ItemArmor[] BOOTS = null;
    public static Item[] SWORDS = null;
    public static ItemArmor[][] ARMORS = null;

    static {
        ENHANCE_FACTORS = new double[Short.MAX_VALUE];
        for (int i = 0, enhance_factorsLength = ENHANCE_FACTORS.length; i < enhance_factorsLength; i++) {
            ENHANCE_FACTORS[i] = Math.pow(1.028,i*2.28) - 1;
        }
    }

    public static int getNextItemID() {
        return IdUtil.getNextItemID();
    }

    public static void initItemArray() {
        HELMETS = new ItemArmor[]{Item.helmetLeather, Item.helmetChainCopper, Item.helmetCopper, Item.helmetRustedIron, Item.helmetChainIron, Item.helmetIron, Item.helmetChainAncientMetal, Item.helmetAncientMetal, Item.helmetChainMithril, Item.helmetMithril, Item.helmetAdamantium, Items.VIBRANIUM_HELMET};
        CHESTPLATES = new ItemArmor[]{Item.plateLeather, Item.plateChainCopper, Item.plateCopper, Item.plateRustedIron, Item.plateChainIron, Item.plateIron, Item.plateChainAncientMetal, Item.plateAncientMetal, Item.plateChainMithril, Item.plateMithril, Item.plateAdamantium, Items.VIBRANIUM_CHESTPLATE};
        LEGGINGS = new ItemArmor[]{Item.legsLeather, Item.legsChainCopper, Item.legsCopper, Item.legsRustedIron, Item.legsChainIron, Item.legsIron, Item.legsChainAncientMetal, Item.legsAncientMetal, Item.legsChainMithril, Item.legsMithril, Item.legsAdamantium, Items.VIBRANIUM_LEGGINGS};
        BOOTS = new ItemArmor[]{Item.bootsLeather, Item.bootsChainCopper, Item.bootsCopper, Item.bootsRustedIron, Item.bootsChainIron, Item.bootsIron, Item.bootsChainAncientMetal, Item.bootsAncientMetal, Item.bootsChainMithril, Item.bootsMithril, Item.bootsAdamantium, Items.VIBRANIUM_BOOTS};
        ARMORS = new ItemArmor[][]{HELMETS, CHESTPLATES, LEGGINGS, BOOTS};
        SWORDS = new Item[]{Item.swordRustedIron, Item.swordIron,Item.swordAncientMetal,Item.swordMithril,Item.swordAdamantium,Items.VIBRANIUM_SWORD};
    }

}
