package net.xiaoyu233.mitemod.miteite;

import net.xiaoyu233.fml.AbstractMod;
import net.xiaoyu233.fml.asm.annotations.Mod;
import net.xiaoyu233.mitemod.miteite.events.EventListeners;
import net.xiaoyu233.mitemod.miteite.util.Constant;

@Mod
public class MITEITEMod extends AbstractMod {
    @Override
    public void preInit() {

    }

    @Override
    public void postInit() {
        EventListeners.registerAllEvents();
    }

    @Override
    public String modId() {
        return "MITE-ITE";
    }

    @Override
    public int modVerNum() {
        return Constant.MITE_ITE_VER_NUM;
    }

    @Override
    public String modVerStr() {
        return Constant.MITE_ITE_VERSION;
    }

    @Override
    public String transformPkg() {
        return "net.xiaoyu233.mitemod.miteite.trans";
    }
}
