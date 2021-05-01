package net.xiaoyu233.mitemod.miteite;

import net.xiaoyu233.fml.AbstractMod;
import net.xiaoyu233.fml.FishModLoader;
import net.xiaoyu233.fml.classloading.Mod;
import net.xiaoyu233.fml.config.InjectionConfig;
import net.xiaoyu233.fml.util.ModInfo;
import net.xiaoyu233.mitemod.miteite.events.EventListeners;
import net.xiaoyu233.mitemod.miteite.trans.MinecraftTrans;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import org.spongepowered.asm.mixin.MixinEnvironment;

import javax.annotation.Nonnull;
import javax.swing.*;

@Mod
public class MITEITEMod extends AbstractMod {
    private static final int MOD_LOADER_MIN_VER_NUM = 100;
    private static final String MOD_LOADER_MIN_VER_STRING = "v1.0.0";
    @Override
    public void preInit() {

    }

    @Nonnull
    @Override
    public InjectionConfig getInjectionConfig() {
        return InjectionConfig.Builder.of("MITE-ITE", MinecraftTrans.class.getPackage(), MixinEnvironment.Phase.INIT).setRequired().build();
    }

    @Override
    public void postInit() {
        ModInfo modLoader = FishModLoader.getModsMap().get("FishModLoader");
        int modLoaderNum = modLoader.getModVerNum();
        if (modLoaderNum < MOD_LOADER_MIN_VER_NUM){
            JFrame diaFrame = new JFrame();
            diaFrame.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(diaFrame,"MITE-ITE加载错误: 模组加载器版本过低\nFishModLoader模组加载器需要至少" + MOD_LOADER_MIN_VER_STRING + "版本 \n当前版本:" + modLoader.getModVerStr(),"错误", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        EventListeners.registerAllEvents();
        Configs.loadConfigs();
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
}
