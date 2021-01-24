package net.xiaoyu233.mitemod.miteite.util;

import net.xiaoyu233.fml.config.JsonConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Config extends JsonConfig {
    public Config(File configFile) {
        super(configFile);
    }

    public <T> T get(ConfigEntry<T> entry){
        return super.get(entry.cfgName);
    }

    public void saveDefault() {
        for (ConfigEntry<?> value : ConfigEntry.values()) {
            this.set(value.cfgName,value.defaultValue);
        }
        this.save();
    }

    @Override
    public void load() {
        super.load();
        this.fixMissingCfg();
    }

    private void fixMissingCfg(){
        boolean modified = false;
        for (ConfigEntry<?> value : ConfigEntry.values()) {
            if (!this.has(value.cfgName)){
                this.set(value.cfgName,value.defaultValue);
                modified = true;
            }
        }
        if (modified){
            this.save();
        }
    }

    public static class ConfigEntry<T> {
        private static final List<ConfigEntry<?>> VALUES = new ArrayList<>();
        public static final ConfigEntry<Boolean> UNDERWORLD_RANDOM_TELEPORT = new ConfigEntry<>("underworld-random-teleport",false);
        public static final ConfigEntry<Double> UNDERWORLD_RANDOM_TELEPORT_TIME = new ConfigEntry<>("underworld-random-teleport-time",36000d);
        public static final ConfigEntry<Boolean> NETHERRACK_DAMAGE = new ConfigEntry<>("netherrack-damage",true);
        public static final ConfigEntry<Boolean> BONE_LORD_TWEAK = new ConfigEntry<>("bone-lord-tweak",true);
        public static final ConfigEntry<Integer> HEALTH_BAR_X_OFFSET = new ConfigEntry<>("health_bar_x_offset",0);
        public static final ConfigEntry<Integer> HEALTH_BAR_Y_OFFSET = new ConfigEntry<>("health_bar_y_offset",0);
        public static final ConfigEntry<Double> BLOOD_MOON_MAX_HOSTILE_FRACTION = new ConfigEntry<>("blood_moon_max_hostile_fraction",128d);
        public final String cfgName;
        public final T defaultValue;
        private final BiLazyValue<Config,T> getter ;
        ConfigEntry(String cfgName,T defaultValue){
            this.cfgName = cfgName;
            this.defaultValue = defaultValue;
            this.getter = new BiLazyValue<>((config -> config.get(this.cfgName)));
            VALUES.add(this);
        }

        public T getFrom(Config config){
            return this.getter.get(config);
        }

        public static List<ConfigEntry<?>> values() {
            return VALUES;
        }
    }
}
