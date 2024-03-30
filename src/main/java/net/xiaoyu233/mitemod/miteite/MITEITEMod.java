package net.xiaoyu233.mitemod.miteite;

import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.config.ConfigRegistry;
import net.xiaoyu233.mitemod.miteite.events.EventListeners;
import net.xiaoyu233.mitemod.miteite.util.Configs;

import java.util.Optional;

@SuppressWarnings("unused")
public class MITEITEMod implements ModInitializer {
    private final ConfigRegistry configRegistry = new ConfigRegistry(Configs.ROOT, Configs.CONFIG_FILE);
    public void onInitialize() {
        EventListeners.registerAllEvents();
    }

    public Optional<ConfigRegistry> createConfig() {
        return Optional.of(this.configRegistry);
    }
}
