package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.EntityTypes;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityTypes.class)
public class EntityTypesLiar {
    @Stealing
    public static void a(Class par0Class, String par1Str, int par2) {}
}
