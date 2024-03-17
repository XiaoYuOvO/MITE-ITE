package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.EntityLivingBase;

public interface ITEWitch {
    EntityLivingBase getSummon_wolf_target();
    void setSummon_wolf_target(EntityLivingBase summon_wolf_target);
    int getSummon_wolf_countdown();
    void setSummon_wolf_countdown(int summon_wolf_countdown);
    boolean isHas_summoned_wolves();
    void setHas_summoned_wolves(boolean has_summoned_wolves);
    int getCurse_random_seed();
    int summonWolvesITE();
}
