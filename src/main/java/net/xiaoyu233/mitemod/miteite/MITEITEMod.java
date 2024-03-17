package net.xiaoyu233.mitemod.miteite;

import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.AbstractMod;
import net.xiaoyu233.fml.classloading.Mod;
import net.xiaoyu233.fml.config.ConfigRegistry;
import net.xiaoyu233.fml.config.InjectionConfig;
import net.xiaoyu233.mitemod.miteite.events.EventListeners;
import net.xiaoyu233.mitemod.miteite.util.Configs;
import net.xiaoyu233.mitemod.miteite.util.Constant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

@Mod
public class MITEITEMod extends AbstractMod implements ModInitializer {
    private transient final ConfigRegistry configRegistry = new ConfigRegistry(Configs.ROOT,Configs.CONFIG_FILE);
    @Override
    public void preInit() {

    }

    @Nullable
    @Override
    public ConfigRegistry getConfigRegistry() {
        return configRegistry;
    }

    @Nonnull
    @Override
    public InjectionConfig getInjectionConfig() {
        return null;
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
    public void onInitialize() {
        EventListeners.registerAllEvents();
    }

    @Override
    public Optional<ConfigRegistry> createConfig() {
        return Optional.of(configRegistry);
    }
}
