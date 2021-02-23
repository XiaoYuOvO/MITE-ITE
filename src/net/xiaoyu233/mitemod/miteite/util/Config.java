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
        public static final IntConfigEntry GHAST_SPAWN_LIMIT_DAY = new IntConfigEntry("ghast_spawn_limit_day",128);
        public static final IntConfigEntry ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY = new IntConfigEntry("ancient_bone_lord_spawn_limit_day",192);
        public static final ConfigEntry<Double> MOB_MAX_SPAWN_COUNT_INCREASE_PER_DAY = new ConfigEntry<>("mob_max_spawn_count_increase_per_day", 1d);
        public static final IntConfigEntry IRON_TO_MITHRIL_COOK_TIME = new IntConfigEntry("iron_to_mithril_cook_time",6000);
        public static final IntConfigEntry MITHRIL_TO_ADAMANTIUM_COOK_TIME = new IntConfigEntry("mithril_to_adamantium_cook_time",9000);
        public static final IntConfigEntry MITHRIL_BLOCK_COUNT_TO_ADAMANTIUM = new IntConfigEntry("mithril_block_count_to_adamantium",1);
        public static final ConfigEntry<Boolean> OVERWORLD_ADAMANTITE_ORE = new ConfigEntry<>("overworld_adamantite_ore",true);
        public static final IntConfigEntry QUARTZ_MAX_EXP_LEVEL = new IntConfigEntry("quartz_max_exp_level",55);
        public static final ConfigEntry<Double> IN_WALL_DAMAGE_FOR_PLAYER = new ConfigEntry<>("in_wall_damage_for_player",10.0d);
        public static final ConfigEntry<Boolean> SKELETON_TRIPLE_SHOT = new ConfigEntry<>("skeleton_triple_shot",true);
        public static final ConfigEntry<Boolean> BAT_POISON_ATTACK = new ConfigEntry<>("bat_poison_attack",true);
        public static final ConfigEntry<Boolean> MOB_SPAWNER_SPEED_UP_WITH_PLAYERS = new ConfigEntry<>("mob_spawner_speed_up_with_players",true);
        public static final ConfigEntry<Boolean> UNDERWORLD_DEBUFF = new ConfigEntry<>("underworld_debuff",true);
        public static final ConfigEntry<Boolean> NETHER_DEBUFF = new ConfigEntry<>("nether_debuff",true);
        public static final IntConfigEntry NETHER_DEBUFF_TIME = new IntConfigEntry("nether_debuff_time",72000);
        public static final IntConfigEntry UNDERWORLD_DEBUFF_PERIOD1 = new IntConfigEntry("underworld_debuff_period1",72000);
        public static final IntConfigEntry UNDERWORLD_DEBUFF_PERIOD2 = new IntConfigEntry("underworld_debuff_period2", 108000);
        public static final IntConfigEntry UNDERWORLD_DEBUFF_PERIOD3 = new IntConfigEntry("underworld_debuff_period3", 132000);
        public static final ConfigEntry<Double> STEPPED_MOB_DAMAGE_FACTOR = new ConfigEntry<>("stepped_mob_damage_factor", 0.1);
        public static final ConfigEntry<Boolean> INFERNAL_CREEPER_BOOST = new ConfigEntry<>("infernal_creeper_boost", true);
        public static final ConfigEntry<Boolean> ZOMBIE_PIGMAN_USE_BOW = new ConfigEntry<>("zombie_pigman_use_bow", true);
        public static final IntConfigEntry CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL = new IntConfigEntry("crit_enchantment_chance_boost_per_lvl",2);
        public static final ConfigEntry<Double> CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL = new ConfigEntry<>("crit_enchantment_damage_boost_per_lvl",0.4d);
        public static final IntConfigEntry IN_RAIN_DEBUFF_TIME = new IntConfigEntry("in_rain_debuff_time", 3600);
        public static final IntConfigEntry VILLAGER_WOOL_TO_EMERALD_SHARD_COUNT = new IntConfigEntry("villager_wool_to_emerald_shard_count", 4);
        public static final IntConfigEntry VILLAGER_WOOL_TO_EMERALD_SHARD_SHARD_COUNT = new IntConfigEntry("villager_wool_to_emerald_shard_shard_count", 1);
        public static final IntConfigEntry BREED_XP_CHICKEN = new IntConfigEntry("breed_xp_chicken", 3);
        public static final IntConfigEntry BREED_XP_SHEEP = new IntConfigEntry("breed_xp_sheep", 5);
        public static final IntConfigEntry BREED_XP_PIG = new IntConfigEntry("breed_xp_pig", 15);
        public static final IntConfigEntry BREED_XP_COW = new IntConfigEntry("breed_xp_cow", 25);
        public static final IntConfigEntry FISHING_XP_SCALE = new IntConfigEntry("fishing_xp_scale",3);
        public static final IntConfigEntry DIAMOND_EXP = new IntConfigEntry("diamond_exp",300);
        public static final ConfigEntry<Double> ALL_PROTECTION_V_DEFENCE_FRACTION = new ConfigEntry<>("all_protection_v_defence_fraction",0.1d);
        public static final ConfigEntry<Boolean> WOLVES_ATTACK_PLAYERS_WITH_MEAT = new ConfigEntry<>("wolves_attack_players_with_meat",true);
        public static final ConfigEntry<Boolean> FIRST_DAY_LONGER_DAY_TIME = new ConfigEntry<>("first_day_longer_day_time",true);
        public static final ConfigEntry<Boolean> CAN_BOOST_IRON_GOLEM = new ConfigEntry<>("can_boost_iron_golem",true);
        public static final ConfigEntry<Boolean> ZOMBIE_DEFENSE = new ConfigEntry<>("zombie_defense",true);
        public static final IntConfigEntry ANIMAL_ILL_TO_DEATH_TIME = new IntConfigEntry("animal_ill_to_death_time",144000);
        public static final ConfigEntry<Boolean> COMPRESSED_SKELETON = new ConfigEntry<>("compressed_skeleton",true);
        public static final IntConfigEntry COMPRESSED_SKELETON_EXPAND_COUNT = new IntConfigEntry("compressed_skeleton_expand_count",5);
        public static final IntConfigEntry EMERGENCY_COOLDOWN = new IntConfigEntry("emergency_cooldown",72000);
        //Ore gens overworld
        public static final IntConfigEntry COPPER_FREQUENCY_OVERWORLD = new IntConfigEntry("copper_frequency_overworld", 40);
        public static final IntConfigEntry SILVER_FREQUENCY_OVERWORLD = new IntConfigEntry("silver_frequency_overworld", 10);
        public static final IntConfigEntry IRON_FREQUENCY_OVERWORLD = new IntConfigEntry("iron_frequency_overworld", 40);
        public static final IntConfigEntry GOLD_FREQUENCY_OVERWORLD = new IntConfigEntry("gold_frequency_overworld", 20);
        public static final IntConfigEntry MITHRIL_FREQUENCY_OVERWORLD = new IntConfigEntry("mithril_frequency_overworld", 10);
        public static final IntConfigEntry LAPIS_FREQUENCY_OVERWORLD = new IntConfigEntry("lapis_frequency_overworld", 4);
        public static final IntConfigEntry DIAMOND_FREQUENCY_OVERWORLD = new IntConfigEntry("diamond_frequency_overworld", 5);
        public static final IntConfigEntry ADAMANTIUM_FREQUENCY_OVERWORLD = new IntConfigEntry("adamantium_frequency_overworld", 1);
        //Ore gens underworld
        public static final IntConfigEntry IRON_FREQUENCY_UNDERWORLD = new IntConfigEntry("iron_frequency_underworld", 60);
        public static final IntConfigEntry SILVER_FREQUENCY_UNDERWORLD = new IntConfigEntry("silver_frequency_underworld", 10);
        public static final IntConfigEntry COPPER_FREQUENCY_UNDERWORLD = new IntConfigEntry("copper_frequency_underworld", 40);
        public static final IntConfigEntry GOLD_FREQUENCY_UNDERWORLD = new IntConfigEntry("gold_frequency_underworld", 20);
        public static final IntConfigEntry MITHRIL_FREQUENCY_UNDERWORLD = new IntConfigEntry("mithril_frequency_underworld", 10);
        public static final IntConfigEntry LAPIS_FREQUENCY_UNDERWORLD = new IntConfigEntry("lapis_frequency_underworld", 5);
        public static final IntConfigEntry DIAMOND_FREQUENCY_UNDERWORLD = new IntConfigEntry("diamond_frequency_underworld", 5);
        public static final IntConfigEntry ADAMANTIUM_FREQUENCY_UNDERWORLD = new IntConfigEntry("adamantium_frequency_underworld", 5);
        public static final IntConfigEntry GIANT_ZOMBIE_SPAWN_ZOMBIE_COOLDOWN = new IntConfigEntry("giant_zombie_spawn_zombie_cooldown",1200);

        public static final ConfigEntry<Boolean> SKELETON_FORCE_MELEE_ATTACK = new ConfigEntry<>("skeleton_force_melee_attack", true);
        public static final ConfigEntry<Double> ZOMBIE_PIGMAN_BOOST_CHANCE = new ConfigEntry<>("zombie_pigman_boost_chance",0.025);
        public static final ConfigEntry<Double> GHAST_OVERWORLD_BOOST = new ConfigEntry<>("ghast_overworld_boost",1.25);
        public static final ConfigEntry<Boolean> LOG_PLAYERS_INTERACT_WITH_PORTAL = new ConfigEntry<>("log_players_interact_with_portal",true);
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
