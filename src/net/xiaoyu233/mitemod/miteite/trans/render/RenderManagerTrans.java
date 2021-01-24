package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.entity.EntityAncientDragon;
import net.xiaoyu233.mitemod.miteite.render.entity.RenderAncientDragon;

import java.util.HashMap;
import java.util.Map;

import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.dyCast;

@Transform(bgl.class)
public class RenderManagerTrans {
    @Link
    private final Map q = new HashMap();

    private RenderManagerTrans() {
        this.q.put(EntityCaveSpider.class, new bgd());
        this.q.put(EntitySpider.class, new bhp());
        this.q.put(EntityPig.class, new bhi(new bbr(), new bbr(0.5F), 0.7F));
        this.q.put(EntitySheep.class, new bhk(new bbv(), new bbu(), 0.7F));
        this.q.put(EntityCow.class, new bgf(new bbf(), 0.7F));
        this.q.put(EntityMushroomCow.class, new bhf(new bbf(), 0.7F));
        this.q.put(EntityWolf.class, new bhy(new bcl(), new bcl(), 0.5F));
        this.q.put(EntityChicken.class, new bge(new bbe(), 0.3F));
        this.q.put(EntityOcelot.class, new bhg(new bbq(), 0.4F));
        this.q.put(EntitySilverfish.class, new bhl());
        this.q.put(EntityCreeper.class, new bgg());
        this.q.put(EntityEnderman.class, new bgk());
        this.q.put(EntitySnowman.class, new bho());
        this.q.put(EntitySkeleton.class, new bhm());
        this.q.put(EntityWitch.class, new bhv());
        this.q.put(EntityBlaze.class, new bgb());
        this.q.put(EntityZombie.class, new bhz());
        this.q.put(EntityGhoul.class, new RenderGhoul());
        this.q.put(EntityWight.class, new RenderWight());
        this.q.put(EntityInvisibleStalker.class, new RenderInvisibleStalker());
        this.q.put(EntityDemonSpider.class, new RenderDemonSpider());
        this.q.put(EntityHellhound.class, new RenderHellhound(new bcl(), new bcl(), 0.5F));
        this.q.put(EntityDireWolf.class, new RenderDireWolf(new bcl(), new bcl(), 0.5F, 1.1F));
        this.q.put(EntityWoodSpider.class, new RenderWoodSpider(0.6F));
        this.q.put(EntityInfernalCreeper.class, new RenderInfernalCreeper());
        this.q.put(EntityShadow.class, new RenderShadow());
        this.q.put(EntityFireElemental.class, new RenderFireElemental());
        this.q.put(EntityBlackWidowSpider.class, new RenderBlackWidowSpider(0.6F));
        this.q.put(EntityRevenant.class, new bhz());
        this.q.put(EntityEarthElemental.class, new RenderEarthElemental());
        this.q.put(EntityJelly.class, new RenderGelatinousCube(new ModelGelatinousCube(16), new ModelGelatinousCube(0), 0.25F));
        this.q.put(EntityBlob.class, new RenderGelatinousCube(new ModelGelatinousCube(16), new ModelGelatinousCube(0), 0.25F));
        this.q.put(EntityOoze.class, new RenderGelatinousCube(new ModelGelatinousCube(16), new ModelGelatinousCube(0), 0.25F));
        this.q.put(EntityPudding.class, new RenderGelatinousCube(new ModelGelatinousCube(16), new ModelGelatinousCube(0), 0.25F));
        this.q.put(EntityVampireBat.class, new bga());
        this.q.put(EntityGiantVampireBat.class, new bga());
        this.q.put(EntityLongdead.class, new bhm());
        this.q.put(EntityLongdeadGuardian.class, new bhm());
        this.q.put(EntityNightwing.class, new bga());
        this.q.put(EntityNetherspawn.class, new bhl());
        this.q.put(EntityCopperspine.class, new bhl());
        this.q.put(EntityHoarySilverfish.class, new bhl());
        this.q.put(EntityClayGolem.class, new RenderEarthElemental());
        this.q.put(EntityBoneLord.class, new bhm());
        this.q.put(EntityAncientBoneLord.class, new bhm());
        this.q.put(EntityPhaseSpider.class, new RenderPhaseSpider());
        this.q.put(EntitySlime.class, new RenderGelatinousCube(new ModelGelatinousCube(16), new ModelGelatinousCube(0), 0.25F));
        this.q.put(EntityMagmaCube.class, new bgy());
        this.q.put(EntityHuman.class, new bhj());
        this.q.put(EntityGiantZombie.class, new bgs(new bcm(), 0.5F, 6.0F));
        this.q.put(EntityGhast.class, new bgr());
        this.q.put(EntitySquid.class, new bhq(new bce(), 0.7F));
        this.q.put(EntityVillager.class, new bhu());
        this.q.put(EntityIronGolem.class, new bht());
        this.q.put(EntityBat.class, new bga());
        this.q.put(EntityEnderDragon.class, new bgj());
        this.q.put(EntityEnderCrystal.class, new bgi());
        this.q.put(EntityWither.class, new bhw());
        this.q.put(Entity.class, new bgh());
        this.q.put(EntityPainting.class, new bhh());
        this.q.put(EntityItemFrame.class, new bgv());
        this.q.put(EntityLeash.class, new bgz());
        this.q.put(EntityArrow.class, new bfz());
        this.q.put(EntitySnowball.class, new bgx(Item.aF));
        this.q.put(EntityEnderPearl.class, new bgx(Item.bp));
        this.q.put(EntityEnderSignal.class, new bgx(Item.bC));
        this.q.put(EntityEgg.class, new bgx(Item.aR));
        this.q.put(EntityBrick.class, new bgx(null));
        this.q.put(EntityGelatinousSphere.class, new bgx(Item.aO));
        this.q.put(EntityWeb.class, new bgx(Item.thrownWeb));
        this.q.put(EntityPotion.class, new bgx(Item.bu, 16384));
        this.q.put(EntityThrownExpBottle.class, new bgx(Item.bF));
        this.q.put(EntityFireworks.class, new bgx(Item.bW));
        this.q.put(EntityLargeFirebal.class, new bgp(2.0F));
        this.q.put(EntitySmallFireball.class, new bgp(0.5F));
        this.q.put(EntityWitherSkull.class, new bhx());
        this.q.put(EntityItem.class, new bgw());
        this.q.put(EntityExperienceOrb.class, new bgn());
        this.q.put(EntityTNTPrimed.class, new bhs());
        this.q.put(EntityFallingBlock.class, new bgo());
        this.q.put(EntityMinecartTNT.class, new bhr());
        this.q.put(EntityMinecartMobSpawner.class, new bhd());
        this.q.put(EntityMinecartAbstract.class, new bhc());
        this.q.put(EntityBoat.class, new bgc());
        this.q.put(EntityFishingHook.class, new bgq());
        this.q.put(EntityHorse.class, new bgt(new bbp(), 0.75F));
        this.q.put(EntityLightning.class, new bha());
        this.q.put(EntityAncientDragon.class,new RenderAncientDragon());
//        this.q.put(EntityRideMarker.class,new RenderAncientDragon());

        for (Object o : this.q.values()) {
            bgm var2 = (bgm) o;
            var2.a(dyCast(bgl.class, this));
        }

    }

}
