package net.xiaoyu233.mitemod.miteite.trans.entity;

import com.google.common.collect.Lists;
import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.List;

@Transform(EntityWolf.class)
public class EntityWolfTrans extends EntityTameableAnimal {
    private static final List<Item> MEATS = Lists.newArrayList(Item.as,Item.bk,Item.bm,Item.lambchopRaw);
    @Marker
    public EntityWolfTrans(World par1World) {
        super(par1World);
    }

    @Marker
    protected boolean isHostileToPlayers() {
        return false;
    }

    @Override
    protected EntityHuman findPlayerToAttack(float max_distance) {
        if (MITEITEMod.CONFIG.get(Config.ConfigEntry.WOLVES_ATTACK_PLAYERS_WITH_MEAT)){
            EntityHuman var1 = super.findPlayerToAttack(64.0F);
            if (var1 != null){
                //Not tamed
                if (!this.bT()){
                    return this.hasMeatInHotbar(var1) ? var1 : null;
                }
                return super.findPlayerToAttack(max_distance);
            }
            return null;
        }
        return super.findPlayerToAttack(max_distance);
    }

    private boolean hasMeatInHotbar(EntityHuman par1EntityPlayer) {
        if (this.isDecoy()) {
            return false;
        } if (MEATS.contains(par1EntityPlayer.getHeldItemStack().b())) {
            return true;
        } else {
            int num_meats = 0;

            for (Item meat : MEATS) {
                num_meats += par1EntityPlayer.bn.getHotbarSlotContainItem(meat);
            }
            return num_meats > 0 && this.ab.nextInt(2000) < num_meats;
        }
    }

    @Override
    @Marker
    protected int getTamingOutcome(EntityHuman entityHuman) {
        return 0;
    }

    @Override
    @Marker
    public EntityAgeable a(EntityAgeable var1) {
        return null;
    }
}
