package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

import java.util.List;

@Transform(EntityPigZombie.class)
public class EntityZombiePigmanTrans extends EntityZombie {
    @Link
    protected static IAttribute bp;
    private int DATA_OBJ_ID_IS_BOOSTED;
    private int effectCooldown;

    @Marker
    public EntityZombiePigmanTrans(World par1World) {
        super(par1World);
    }

    protected void az() {
        super.az();
        int day = this.getWorld() != null ? Math.max(this.getWorld().getDayOfWorld() - 64, 0) : 0;
        this.setEntityAttribute(GenericAttributes.a, 50.0D + day / 12D);
        this.setEntityAttribute(GenericAttributes.b, 40.0D);
        this.setEntityAttribute(GenericAttributes.d, 0.25F);
        this.setEntityAttribute(GenericAttributes.e, 8.0D + day / 48D);
        this.setEntityAttribute(EntityZombie.bp, this.ab.nextDouble() * (double) 0.1F);
    }

    protected void a() {
        super.a();
        this.DATA_OBJ_ID_IS_BOOSTED = this.ah.addObject(this.ah.getNextAvailableId(), (byte) 0);
    }

    public GroupDataEntity a(GroupDataEntity par1EntityLivingData) {
        super.a(par1EntityLivingData);
        this.setVillager(false, 0);
        this.ah.b(DATA_OBJ_ID_IS_BOOSTED, (byte) ((this.ab.nextInt(10) < 1) ? 1 : 0));
        return par1EntityLivingData;
    }

    public void c() {
        super.c();
        if (this.getWorld().isWorldServer() && this.ah.a(DATA_OBJ_ID_IS_BOOSTED) > 0) {
            if (effectCooldown <= 0) {
                int day = MinecraftServer.F().getOverworld().getWorld().getDayOfWorld();
                if (day > 128) {
                    List<EntityPigZombie> nearbyZombie = this.q.a(EntityPigZombie.class, this.E.b(16.0D, 8.0D, 16.0D));
                    for (EntityPigZombie entityPigZombie : nearbyZombie) {
                        entityPigZombie.c(new MobEffect(1, 40 + day / 32 * 10, this.aD().nextInt(Math.max(((day - 96) / 96), 1)), false));
                        entityPigZombie.c(new MobEffect(5, day / 32 * 10, this.aD().nextInt(Math.max(((day - 128) / 96), 1)), false));
                    }

                    entityFX(EnumEntityFX.smoke_and_steam);
                    effectCooldown = 30;
                }
            } else {
                effectCooldown--;
            }
        }
    }
}
