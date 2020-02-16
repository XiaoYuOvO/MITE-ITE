package net.xiaoyu233.mitemod.miteite.liar.util;

import net.minecraft.Item;
import net.minecraft.ItemArmor;

@NoTrans
public class Constant {
    public static final String MITE_ITE_VERSION = "0.1.0b";
    public static final ItemArmor[] HELMETS = new ItemArmor[]{Item.X, Item.helmetChainCopper,Item.helmetCopper,Item.helmetRustedIron,Item.helmetChainIron,Item.af,Item.helmetChainAncientMetal,Item.helmetAncientMetal,Item.helmetChainMithril,Item.helmetMithril,Item.helmetAdamantium};
    public static final ItemArmor[] CHESTPLATES = new ItemArmor[]{Item.Y,Item.plateChainCopper,Item.plateCopper,Item.plateRustedIron,Item.plateChainIron,Item.ag,Item.plateChainAncientMetal,Item.plateAncientMetal,Item.plateChainMithril,Item.plateMithril,Item.plateAdamantium};
    public static final ItemArmor[] LEGGINGS = new ItemArmor[]{Item.Z,Item.legsChainCopper,Item.legsCopper,Item.legsRustedIron,Item.legsChainIron,Item.ah,Item.legsChainAncientMetal,Item.legsAncientMetal,Item.legsChainMithril,Item.legsMithril,Item.legsAdamantium};
    public static final ItemArmor[] BOOTS = new ItemArmor[]{Item.aa,Item.bootsChainCopper,Item.bootsCopper,Item.bootsRustedIron,Item.bootsChainIron,Item.ai,Item.bootsChainAncientMetal,Item.bootsAncientMetal,Item.bootsChainMithril,Item.bootsMithril,Item.bootsAdamantium};
    public static final ItemArmor[][] ARMORS = new ItemArmor[][]{HELMETS,CHESTPLATES,LEGGINGS,BOOTS};
}
