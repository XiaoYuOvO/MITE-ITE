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
        return entry.getFrom(this);
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

    @SuppressWarnings("StaticInitializerReferencesSubClass")
    public static class ConfigEntry<T> {
        private static final List<ConfigEntry<?>> VALUES = new ArrayList<>();
        public static final ConfigEntry<Boolean> UNDERWORLD_RANDOM_TELEPORT = new ConfigEntry<>("underworld-random-teleport",true);
        public static final ConfigEntry<Double> UNDERWORLD_RANDOM_TELEPORT_TIME = new ConfigEntry<>("underworld-random-teleport-time",36000d);
        public static final ConfigEntry<Boolean> NETHERRACK_DAMAGE = new ConfigEntry<>("netherrack-damage",true);
        public static final ConfigEntry<Boolean> BONE_LORD_TWEAK = new ConfigEntry<>("bone-lord-tweak",true);
        public static final IntConfigEntry HEALTH_BAR_X_OFFSET = new IntConfigEntry("health_bar_x_offset",0);
        public static final IntConfigEntry HEALTH_BAR_Y_OFFSET = new IntConfigEntry("health_bar_y_offset",0);
        public static final ConfigEntry<Double> BLOOD_MOON_MAX_HOSTILE_FRACTION = new ConfigEntry<>("blood_moon_max_hostile_fraction",128d);
        public static final IntConfigEntry GHAST_SPAWN_LIMIT_DAY = new IntConfigEntry("ghast_spawn_limit_day",96);
        public static final IntConfigEntry ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY = new IntConfigEntry("ancient_bone_lord_spawn_limit_day",192);
        public static final ConfigEntry<Double> MOB_MAX_SPAWN_COUNT_INCREASE_PER_DAY = new ConfigEntry<>("mob_max_spawn_count_increase_per_day", 1d);
        public static final IntConfigEntry IRON_TO_MITHRIL_COOK_TIME = new IntConfigEntry("iron_to_mithril_cook_time",6000);
        public static final IntConfigEntry MITHRIL_TO_ADAMANTIUM_COOK_TIME = new IntConfigEntry("mithril_to_adamantium_cook_time",9000);
        public static final ConfigEntry<Boolean> OVERWORLD_ADAMANTITE_ORE = new ConfigEntry<>("overworld_adamantite_ore",true);
        public static final IntConfigEntry QUARTZ_MAX_EXP_LEVEL = new IntConfigEntry("quartz_max_exp_level",80);
        public static final ConfigEntry<Double> IN_WALL_DAMAGE_FOR_PLAYER = new ConfigEntry<>("in_wall_damage_for_player",10.0d);
        public static final ConfigEntry<Boolean> SKELETON_TRIPLE_SHOT = new ConfigEntry<>("skeleton_triple_shot",true);
        public static final ConfigEntry<Boolean> BAT_POISON_ATTACK = new ConfigEntry<>("bat_poison_attack",true);
        public static final ConfigEntry<Boolean> MOB_SPAWNER_SPEED_UP_WITH_PLAYERS = new ConfigEntry<>("mob_spawner_speed_up_with_players",true);
        public static final ConfigEntry<Boolean> UNDERWORLD_DEBUFF = new ConfigEntry<>("underworld_debuff",true);
        public static final IntConfigEntry UNDERWORLD_DEBUFF_PERIOD1 = new IntConfigEntry("underworld_debuff_period1",72000);
        public static final IntConfigEntry UNDERWORLD_DEBUFF_PERIOD2 = new IntConfigEntry("underworld_debuff_period2", 108000);
        public static final IntConfigEntry UNDERWORLD_DEBUFF_PERIOD3 = new IntConfigEntry("underworld_debuff_period3", 132000);
        public static final ConfigEntry<Boolean> STEPPED_MOB_DAMAGE = new ConfigEntry<>("stepped_mob_damage", true);
        public static final ConfigEntry<Boolean> INFERNAL_CREEPER_BOOST = new ConfigEntry<>("infernal_creeper_boost", true);
        public static final ConfigEntry<Boolean> ZOMBIE_PIGMAN_USE_BOW = new ConfigEntry<>("zombie_pigman_use_bow", true);
        public static final ConfigEntry<Double> EXTEND_ENCHANTMENT_BOOST_PER_LVL = new ConfigEntry<>("extend_enchantment_boost_per_lvl",0.5d);
        public static final IntConfigEntry CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL = new IntConfigEntry("crit_enchantment_chance_boost_per_lvl",2);
        public static final ConfigEntry<Double> CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL = new ConfigEntry<>("crit_enchantment_damage_boost_per_lvl",0.4d);
        public final String cfgName;
        public final T defaultValue;
        final BiLazyValue<Config,T> getter ;
        ConfigEntry(String cfgName,T defaultValue){
            this.cfgName = cfgName;
            this.defaultValue = defaultValue;
            this.getter = this.createGetter();
            VALUES.add(this);
        }

        public T getFrom(Config config){
            return this.getter.get(config);
        }

        BiLazyValue<Config,T> createGetter() {
            return new BiLazyValue<>((config -> config.get(this.cfgName)));
        }

        public static List<ConfigEntry<?>> values() {
            return VALUES;
        }
    }

    public static class IntConfigEntry extends ConfigEntry<Integer>{

        IntConfigEntry(String cfgName, Integer defaultValue) {
            super(cfgName, defaultValue);
        }

        @Override
        BiLazyValue<Config, Integer> createGetter() {
            return new BiLazyValue<>((config -> config.getInt(this.cfgName)));
        }
    }
}
