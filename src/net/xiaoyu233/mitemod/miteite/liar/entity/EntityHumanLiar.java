package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.*;
import net.minecraft.server.MinecraftServer;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityHuman.class)
public class EntityHumanLiar{

    public static int getHealthLimit(int level) {
        return Math.max(Math.min(6 + level / 5 * 2, 40), 6);
    }
}
