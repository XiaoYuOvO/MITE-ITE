package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityExperienceOrb;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityExperienceOrb.class)
public class EntityExperienceOrbTrans {
    //Improve
    public static int a(int par0) {
        if (par0 >= 256000) return 256000;
        if (par0 >= 128000) return 128000;
        if (par0 >= 64000) return 64000;
        if (par0 >= 32000) return 32000;
        if (par0 >= 16000) return 16000;
        if (par0 >= 8000) return 8000;
        if (par0 >= 4000) return 4000;
        if (par0 >= 2477) return 2477;
        if (par0 >= 1237) return 1237;
        if (par0 >= 617) return 617;
        if (par0 >= 307) return 307;
        if (par0 >= 149) return 149;
        if (par0 >= 73) return 73;
        if (par0 >= 37) return 37;
        if (par0 >= 17) return 17;
        if (par0 >= 7) return 7;
        if (par0 >= 3) return 3;
        return 1;
    }
}
