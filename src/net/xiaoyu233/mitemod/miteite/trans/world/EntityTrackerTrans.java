package net.xiaoyu233.mitemod.miteite.trans.world;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.entity.EntityRideMarker;

import java.util.Iterator;
import java.util.Set;

@Transform(EntityTracker.class)
public class EntityTrackerTrans {
    @Link
    private Set b;
    @Marker
    public void a(Entity par1Entity, int par2, int par3, boolean par4){}
    @Marker
    public void a(Entity par1Entity, int par2, int par3){}
    public void a(Entity par1Entity) {
        if (par1Entity instanceof EntityPlayer) {
            this.a(par1Entity, 512, 2);
            EntityPlayer var2 = (EntityPlayer)par1Entity;
            Iterator var3 = this.b.iterator();

            while(var3.hasNext()) {
                EntityTrackerEntry var4 = (EntityTrackerEntry)var3.next();
                if (var4.a != var2) {
                    var4.b(var2);
                }
            }
        } else if (par1Entity instanceof EntityFishingHook) {
            this.a(par1Entity, 64, 5, true);
        } else if (par1Entity instanceof EntityArrow) {
            this.a(par1Entity, 64, 20, false);
        } else if (par1Entity instanceof EntitySmallFireball) {
            this.a(par1Entity, 64, 10, false);
        } else if (par1Entity instanceof EntityFireball) {
            this.a(par1Entity, 64, 10, false);
        } else if (par1Entity instanceof EntitySnowball) {
            this.a(par1Entity, 64, 10, true);
        } else if (par1Entity instanceof EntityEnderPearl) {
            this.a(par1Entity, 64, 10, true);
        } else if (par1Entity instanceof EntityEnderSignal) {
            this.a(par1Entity, 64, 4, true);
        } else if (par1Entity instanceof EntityEgg) {
            this.a(par1Entity, 64, 10, true);
        } else if (par1Entity instanceof EntityBrick) {
            this.a(par1Entity, 64, 10, true);
        } else if (par1Entity instanceof EntityWeb) {
            this.a(par1Entity, 64, 10, true);
        } else if (par1Entity instanceof EntityGelatinousSphere) {
            this.a(par1Entity, 64, 10, true);
        } else if (par1Entity instanceof EntityPotion) {
            this.a(par1Entity, 64, 10, true);
        } else if (par1Entity instanceof EntityThrownExpBottle) {
            this.a(par1Entity, 64, 10, true);
        } else if (par1Entity instanceof EntityFireworks) {
            this.a(par1Entity, 64, 10, true);
        } else if (par1Entity instanceof EntityItem) {
            this.a(par1Entity, 64, 20, true);
        } else if (par1Entity instanceof EntityMinecartAbstract) {
            this.a(par1Entity, 80, 3, true);
        } else if (par1Entity instanceof EntityBoat) {
            this.a(par1Entity, 80, 3, true);
        } else if (par1Entity instanceof EntitySquid) {
            this.a(par1Entity, 64, 3, true);
        } else if (par1Entity instanceof EntityWither) {
            this.a(par1Entity, 80, 3, false);
        } else if (par1Entity instanceof EntityBat) {
            this.a(par1Entity, 80, 3, false);
        } else if (par1Entity instanceof IAnimal) {
            this.a(par1Entity, 80, 3, true);
        } else if (par1Entity instanceof EntityEnderDragon) {
            this.a(par1Entity, 160, 3, true);
        } else if (par1Entity instanceof EntityTNTPrimed) {
            this.a(par1Entity, 160, 10, true);
        } else if (par1Entity instanceof EntityFallingBlock) {
            this.a(par1Entity, 160, 20, true);
        } else if (par1Entity instanceof EntityHanging) {
            this.a(par1Entity, 160, Integer.MAX_VALUE, false);
        } else if (par1Entity instanceof EntityExperienceOrb) {
            this.a(par1Entity, 160, 20, true);
        } else if (par1Entity instanceof EntityEnderCrystal) {
            this.a(par1Entity, 256, Integer.MAX_VALUE, false);
        }else if (par1Entity instanceof EntityRideMarker){
            this.a(par1Entity, 80,3,true);
        }

    }
}
