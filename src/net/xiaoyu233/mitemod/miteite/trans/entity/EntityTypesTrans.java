package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.entity.EntityAncientDragon;
import net.xiaoyu233.mitemod.miteite.entity.EntityRideMarker;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombieLord;
import net.xiaoyu233.mitemod.miteite.entity.EntityZombiePigmanLord;

import java.util.*;

@Transform(EntityTypes.class)
public class EntityTypesTrans {
    @Link
    private static final Map b = new HashMap();
    @Link
    private static final Map c = new HashMap();
    @Link
    private static final Map d = new HashMap();
    @Link
    private static final Map e = new HashMap();
    @Link
    private static final Map f = new HashMap();
    @Link
    public static HashMap a = new LinkedHashMap();
    @Link
    public static List entries = new ArrayList();

    @Marker
    public static void a(Class par0Class, String par1Str, int par2) {}
    @Marker
    public static Entity a(int par0, World par1World){
        return null;
    }

    @Marker
    private static void a(Class par0Class, String par1Str, int par2, int par3, int par4){}
    static {
        a(EntityItem.class, "Item", 1);
        a(EntityExperienceOrb.class, "XPOrb", 2);
        a(EntityLeash.class, "LeashKnot", 8);
        a(EntityPainting.class, "Painting", 9);
        a(EntityArrow.class, "Arrow", 10);
        a(EntitySnowball.class, "Snowball", 11);
        a(EntityLargeFirebal.class, "Fireball", 12);
        a(EntitySmallFireball.class, "SmallFireball", 13);
        a(EntityEnderPearl.class, "ThrownEnderpearl", 14);
        a(EntityEnderSignal.class, "EyeOfEnderSignal", 15);
        a(EntityPotion.class, "ThrownPotion", 16);
        a(EntityThrownExpBottle.class, "ThrownExpBottle", 17);
        a(EntityItemFrame.class, "ItemFrame", 18);
        a(EntityWitherSkull.class, "WitherSkull", 19);
        a(EntityTNTPrimed.class, "PrimedTnt", 20);
        a(EntityFallingBlock.class, "FallingSand", 21);
        a(EntityFireworks.class, "FireworksRocketEntity", 22);
        a(EntityBoat.class, "Boat", 41);
        a(EntityMinecartRideable.class, "MinecartRideable", 42);
        a(EntityMinecartChest.class, "MinecartChest", 43);
        a(EntityMinecartFurnace.class, "MinecartFurnace", 44);
        a(EntityMinecartTNT.class, "MinecartTNT", 45);
        a(EntityMinecartHopper.class, "MinecartHopper", 46);
        a(EntityMinecartMobSpawner.class, "MinecartSpawner", 47);
        a(EntityInsentient.class, "Mob", 48);
        a(EntityMonster.class, "Monster", 49);
        a(EntityCreeper.class, "Creeper", 50, 894731, 0);
        a(EntitySkeleton.class, "Skeleton", 51, 12698049, 4802889);
        a(EntitySpider.class, "Spider", 52, 3419431, 11013646);
        a(EntityGiantZombie.class, "Giant", 53);
        a(EntityZombie.class, "Zombie", 54, 44975, 7969893);
        a(EntitySlime.class, "Slime", 55, 5349438, 8306542);
        a(EntityGhast.class, "Ghast", 56, 16382457, 12369084);
        a(EntityPigZombie.class, "PigZombie", 57, 15373203, 5009705);
        a(EntityEnderman.class, "Enderman", 58, 1447446, 0);
        a(EntityCaveSpider.class, "CaveSpider", 59, 803406, 11013646);
        a(EntitySilverfish.class, "Silverfish", 60, 7237230, 3158064);
        a(EntityBlaze.class, "Blaze", 61, 16167425, 16775294);
        a(EntityMagmaCube.class, "LavaSlime", 62, 3407872, 16579584);
        a(EntityEnderDragon.class, "EnderDragon", 63);
        a(EntityWither.class, "WitherBoss", 64);
        a(EntityBat.class, "Bat", 65, 4996656, 986895);
        a(EntityWitch.class, "Witch", 66, 3407872, 5349438);
        a(EntityPig.class, "Pig", 90, 15771042, 14377823);
        a(EntitySheep.class, "Sheep", 91, 15198183, 16758197);
        a(EntityCow.class, "Cow", 92, 4470310, 10592673);
        a(EntityChicken.class, "Chicken", 93, 10592673, 16711680);
        a(EntitySquid.class, "Squid", 94, 2243405, 7375001);
        a(EntityWolf.class, "Wolf", 95, 14144467, 13545366);
        a(EntityMushroomCow.class, "MushroomCow", 96, 10489616, 12040119);
        a(EntitySnowman.class, "SnowMan", 97);
        a(EntityOcelot.class, "Ozelot", 98, 15720061, 5653556);
        a(EntityIronGolem.class, "VillagerGolem", 99);
        a(EntityHorse.class, "EntityHorse", 100, 12623485, 15656192);
        int id = 512;
        int var1 = id + 1;
        a(EntityGhoul.class, "Ghoul", id, 6127744, 5199946);
        a(EntityWight.class, "Wight", var1++, 5789784, 16777215);
        a(EntityInvisibleStalker.class, "InvisibleStalker", var1++, 8947848, 7829367);
        a(EntityDemonSpider.class, "DemonSpider", var1++, 4066304, 11013646);
        a(EntityHellhound.class, "Hellhound", var1++, 1513239, 14954030);
        a(EntityDireWolf.class, "DireWolf", var1++, 9802643, 6770494);
        a(EntityWoodSpider.class, "WoodSpider", var1++, 4733734, 11013646);
        a(EntityInfernalCreeper.class, "InfernalCreeper", var1++, 11599885, 0);
        a(EntityShadow.class, "Shadow", var1++, 592137, 2894892);
        a(EntityFireElemental.class, "FireElemental", var1++, 11484928, 14259731);
        a(EntityBlackWidowSpider.class, "BlackWidowSpider", var1++, 1513239, 11013646);
        a(EntityRevenant.class, "Revenant", var1++, 44975, 7969893);
        a(EntityEarthElemental.class, "EarthElemental", var1++, 5658198, 10066329);
        a(EntityJelly.class, "Jelly", var1++, 9924660, 12097379);
        a(EntityBlob.class, "Blob", var1++, 10430241, 12474193);
        a(EntityOoze.class, "Ooze", var1++, 7237230, 9868950);
        a(EntityPudding.class, "Pudding", var1++, 1314564, 2762010);
        a(EntityVampireBat.class, "VampireBat", var1++, 4996656, 5900553);
        a(EntityGiantVampireBat.class, "GiantVampireBat", var1++, 4996656, 5900553);
        a(EntityLongdead.class, "Longdead", var1++, 13422277, 7699821);
        a(EntityLongdeadGuardian.class, "LongdeadGuardian", var1++, 13422277, 7699821);
        a(EntityNightwing.class, "Nightwing", var1++, 592137, 2894892);
        a(EntityNetherspawn.class, "Netherspawn", var1++, 8464671, 5444623);
        a(EntityCopperspine.class, "Copperspine", var1++, 10049792, 6434048);
        a(EntityHoarySilverfish.class, "HoarySilverfish", var1++, 6647137, 2567971);
        a(EntityClayGolem.class, "ClayGolem", var1++, 10856889, 10133675);
        a(EntityBoneLord.class, "BoneLord", var1++, 12698049, 4802889);
        a(EntityAncientBoneLord.class, "AncientBoneLord", var1++, 13422277, 7699821);
        a(EntityPhaseSpider.class, "PhaseSpider", var1++, 1922130, 512600);
        a(EntityVillager.class, "Villager", 120, 5651507, 12422002);
        a(EntityEnderCrystal.class, "EnderCrystal", 200);
        a(EntityAncientDragon.class, "EntityTest", 201);
        a(EntityRideMarker.class, "EntityMarker", 202);
        a(EntityZombieLord.class,"EntityZombieLord",203);
        a(EntityZombiePigmanLord.class,"EntityZombiePigmanLord",204);
    }
}
