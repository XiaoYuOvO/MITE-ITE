package net.xiaoyu233.mitemod.miteite.util;

import net.xiaoyu233.fml.config.ConfigCategory;
import net.xiaoyu233.fml.config.ConfigEntry;
import net.xiaoyu233.fml.util.FieldReference;

import java.io.File;

public class Configs {
    private static final File CONFIG_FILE = new File("mite-ite-cfg.json");
    private static final ConfigCategory ROOT = new ConfigCategory("root").
            addEntry(ConfigCategory.of("GameMechanics").
                    addEntry(ConfigEntry.of("in_wall_damage_for_player", GameMechanics.IN_WALL_DAMAGE_FOR_PLAYER).withComment("玩家墙内窒息伤害(小数)")).
                    addEntry(ConfigEntry.of("stepped_mob_damage_factor",GameMechanics.STEPPED_MOB_DAMAGE_FACTOR).withComment("渐进伤害每次增幅(x100%)(小数)")).
                    addEntry(ConfigEntry.of("fishing_xp_scale",GameMechanics.FISHING_XP_SCALE)).
                    addEntry(ConfigEntry.of("in_rain_debuff_time",GameMechanics.IN_RAIN_DEBUFF_TIME)).
                    addEntry(ConfigEntry.of("first_day_longer_day_time",GameMechanics.FIRST_DAY_LONGER_DAY_TIME)).
                    addEntry(ConfigCategory.of("MobSpawning").
                            addEntry(ConfigEntry.of("blood_moon_max_hostile_fraction",GameMechanics.MobSpawning.BLOOD_MOON_MAX_HOSTILE_FRACTION)).
                            addEntry(ConfigEntry.of("ghast_spawn_limit_day",GameMechanics.MobSpawning.GHAST_SPAWN_LIMIT_DAY)).
                            addEntry(ConfigEntry.of("ancient_bone_lord_spawn_limit_day",GameMechanics.MobSpawning.ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY)).
                            addEntry(ConfigEntry.of("mob_max_spawn_count_increase_per_day",GameMechanics.MobSpawning.MOB_MAX_SPAWN_COUNT_INCREASE_PER_DAY))).
                    addEntry(ConfigCategory.of("Underworld").
                            addEntry(ConfigEntry.of("underworld_random_teleport",GameMechanics.Underworld.UNDERWORLD_RANDOM_TELEPORT)).
                            addEntry(ConfigEntry.of("underworld_random_teleport_time",GameMechanics.Underworld.UNDERWORLD_RANDOM_TELEPORT_TIME)).
                            addEntry(ConfigEntry.of("underworld_debuff",GameMechanics.Underworld.UNDERWORLD_DEBUFF)).
                            addEntry(ConfigEntry.of("underworld_debuff_period1",GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD1)).
                            addEntry(ConfigEntry.of("underworld_debuff_period2",GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD2)).
                            addEntry(ConfigEntry.of("underworld_debuff_period3",GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD3))).
                    addEntry(ConfigCategory.of("Nether").
                            addEntry(ConfigEntry.of("netherrack_damage",GameMechanics.Nether.NETHERRACK_DAMAGE)).
                            addEntry(ConfigEntry.of("nether_debuff",GameMechanics.Nether.NETHER_DEBUFF)).
                            addEntry(ConfigEntry.of("nether_debuff_time",GameMechanics.Nether.NETHER_DEBUFF_TIME)))).
            addEntry(ConfigCategory.of("Item").
                    addEntry(ConfigEntry.of("quartz_max_exp_level",Item.QUARTZ_MAX_EXP_LEVEL)).
                    addEntry(ConfigEntry.of("diamond_exp",Item.DIAMOND_EXP)).
                    addEntry(ConfigCategory.of("Recipes").
                            addEntry(ConfigEntry.of("IRON_TO_MITHRIL_COOK_TIME",Item.Recipes.IRON_TO_MITHRIL_COOK_TIME)).
                            addEntry(ConfigEntry.of("MITHRIL_TO_ADAMANTIUM_COOK_TIME",Item.Recipes.MITHRIL_TO_ADAMANTIUM_COOK_TIME)).
                            addEntry(ConfigEntry.of("MITHRIL_BLOCK_COUNT_TO_ADAMANTIUM",Item.Recipes.MITHRIL_BLOCK_COUNT_TO_ADAMANTIUM))).
                    addEntry(ConfigCategory.of("Enchantment").
                            addEntry(ConfigEntry.of("crit_enchantment_chance_boost_per_lvl",Item.Enchantment.CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL)).
                            addEntry(ConfigEntry.of("crit_enchantment_damage_boost_per_lvl",Item.Enchantment.CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL)).
                            addEntry(ConfigEntry.of("emergency_cooldown",Item.Enchantment.EMERGENCY_COOLDOWN)).
                            addEntry(ConfigEntry.of("all_protection_v_defence_fraction",Item.Enchantment.ALL_PROTECTION_V_DEFENCE_FRACTION)))).
            addEntry(ConfigCategory.of("Block").
                    addEntry(ConfigEntry.of("mob_spawner_speed_up_with_players",Block.MOB_SPAWNER_SPEED_UP_WITH_PLAYERS))).
            addEntry(ConfigCategory.of("Entities").
                    addEntry(ConfigEntry.of("bone_lord_tweak",Entities.BONE_LORD_TWEAK)).
                    addEntry(ConfigEntry.of("skeleton_triple_shot",Entities.SKELETON_TRIPLE_SHOT)).
                    addEntry(ConfigEntry.of("compressed_skeleton",Entities.COMPRESSED_SKELETON)).
                    addEntry(ConfigEntry.of("skeleton_force_melee_attack",Entities.SKELETON_FORCE_MELEE_ATTACK)).
                    addEntry(ConfigEntry.of("compressed_skeleton_expand_count",Entities.COMPRESSED_SKELETON_EXPAND_COUNT)).
                    addEntry(ConfigEntry.of("bat_poison_attack",Entities.BAT_POISON_ATTACK)).
                    addEntry(ConfigEntry.of("infernal_creeper_boost",Entities.INFERNAL_CREEPER_BOOST)).
                    addEntry(ConfigEntry.of("zombie_pigman_use_bow",Entities.ZOMBIE_PIGMAN_USE_BOW)).
                    addEntry(ConfigEntry.of("zombie_pigman_boost_chance",Entities.ZOMBIE_PIGMAN_BOOST_CHANCE)).
                    addEntry(ConfigEntry.of("ghast_overworld_boost",Entities.GHAST_OVERWORLD_BOOST)).
                    addEntry(ConfigEntry.of("wolves_attack_players_with_meat",Entities.WOLVES_ATTACK_PLAYERS_WITH_MEAT)).
                    addEntry(ConfigEntry.of("can_boost_iron_golem",Entities.CAN_BOOST_IRON_GOLEM)).
                    addEntry(ConfigEntry.of("zombie_defense",Entities.ZOMBIE_DEFENSE)).
                    addEntry(ConfigEntry.of("giant_zombie_spawn_zombie_cooldown",Entities.GIANT_ZOMBIE_SPAWN_ZOMBIE_COOLDOWN)).
                    addEntry(ConfigCategory.of("Animals").
                            addEntry(ConfigEntry.of("animal_ill_to_death_time",Entities.Animals.ANIMAL_ILL_TO_DEATH_TIME)).
                            addEntry(ConfigEntry.of("breed_xp_chicken",Entities.Animals.BREED_XP_CHICKEN)).
                            addEntry(ConfigEntry.of("breed_xp_sheep",Entities.Animals.BREED_XP_SHEEP)).
                            addEntry(ConfigEntry.of("breed_xp_pig",Entities.Animals.BREED_XP_PIG)).
                            addEntry(ConfigEntry.of("breed_xp_cow",Entities.Animals.BREED_XP_COW)))).
            addEntry(ConfigCategory.of("WorldGen").
                    addEntry(ConfigCategory.of("Overworld").
                            addEntry(ConfigEntry.of("overworld_adamantite_ore", WorldGen.Overworld.OVERWORLD_ADAMANTITE_ORE)).
                            addEntry(ConfigEntry.of("copper_frequency_overworld",WorldGen.Overworld.COPPER_FREQUENCY_OVERWORLD)).
                            addEntry(ConfigEntry.of("silver_frequency_overworld",WorldGen.Overworld.SILVER_FREQUENCY_OVERWORLD)).
                            addEntry(ConfigEntry.of("iron_frequency_overworld",WorldGen.Overworld.IRON_FREQUENCY_OVERWORLD)).
                            addEntry(ConfigEntry.of("gold_frequency_overworld",WorldGen.Overworld.GOLD_FREQUENCY_OVERWORLD)).
                            addEntry(ConfigEntry.of("mithril_frequency_overworld",WorldGen.Overworld.MITHRIL_FREQUENCY_OVERWORLD)).
                            addEntry(ConfigEntry.of("lapis_frequency_overworld",WorldGen.Overworld.LAPIS_FREQUENCY_OVERWORLD)).
                            addEntry(ConfigEntry.of("diamond_frequency_overworld",WorldGen.Overworld.DIAMOND_FREQUENCY_OVERWORLD)).
                            addEntry(ConfigEntry.of("adamantium_frequency_overworld",WorldGen.Overworld.ADAMANTIUM_FREQUENCY_OVERWORLD))).
                    addEntry(ConfigCategory.of("Underworld").
                            addEntry(ConfigEntry.of("iron_frequency_underworld",WorldGen.Underworld.IRON_FREQUENCY_UNDERWORLD)).
                            addEntry(ConfigEntry.of("silver_frequency_underworld",WorldGen.Underworld.SILVER_FREQUENCY_UNDERWORLD)).
                            addEntry(ConfigEntry.of("copper_frequency_underworld",WorldGen.Underworld.COPPER_FREQUENCY_UNDERWORLD)).
                            addEntry(ConfigEntry.of("gold_frequency_underworld",WorldGen.Underworld.GOLD_FREQUENCY_UNDERWORLD)).
                            addEntry(ConfigEntry.of("mithril_frequency_underworld",WorldGen.Underworld.MITHRIL_FREQUENCY_UNDERWORLD)).
                            addEntry(ConfigEntry.of("lapis_frequency_underworld",WorldGen.Underworld.LAPIS_FREQUENCY_UNDERWORLD)).
                            addEntry(ConfigEntry.of("diamond_frequency_underworld",WorldGen.Underworld.DIAMOND_FREQUENCY_UNDERWORLD)).
                            addEntry(ConfigEntry.of("adamantium_frequency_underworld",WorldGen.Underworld.ADAMANTIUM_FREQUENCY_UNDERWORLD)))).
            addEntry(ConfigCategory.of("Client").
                    addEntry(ConfigCategory.of("Gui").
                            addEntry(ConfigEntry.of("health_bar_x_offset",Client.Gui.HEALTH_BAR_X_OFFSET)).
                            addEntry(ConfigEntry.of("health_bar_y_offset",Client.Gui.HEALTH_BAR_Y_OFFSET)))).
            addEntry(ConfigCategory.of("Misc").
                    addEntry(ConfigEntry.of("log_players_interact_with_portal",Misc.LOG_PLAYERS_INTERACT_WITH_PORTAL)));

    public static void loadConfigs(){
        ROOT.readFromFile(CONFIG_FILE);
    }

    public static class Block{

        public static final FieldReference<Boolean> MOB_SPAWNER_SPEED_UP_WITH_PLAYERS = new FieldReference<>(true);
    }

    public static class Client{

        public static class Gui{
            public static final FieldReference<Integer> HEALTH_BAR_X_OFFSET = new FieldReference<>(0);
            public static final FieldReference<Integer> HEALTH_BAR_Y_OFFSET = new FieldReference<>(0);
        }
    }

    public static class Entities{

        public static final FieldReference<Boolean> BAT_POISON_ATTACK = new FieldReference<>(true);
        public static final FieldReference<Boolean> BONE_LORD_TWEAK = new FieldReference<>(true);
        public static final FieldReference<Boolean> CAN_BOOST_IRON_GOLEM = new FieldReference<>(true);
        public static final FieldReference<Boolean> COMPRESSED_SKELETON = new FieldReference<>(true);
        public static final FieldReference<Integer> COMPRESSED_SKELETON_EXPAND_COUNT = new FieldReference<>(4);
        public static final FieldReference<Double> GHAST_OVERWORLD_BOOST = new FieldReference<>(1.25);
        public static final FieldReference<Integer> GIANT_ZOMBIE_SPAWN_ZOMBIE_COOLDOWN = new FieldReference<>(1200);
        public static final FieldReference<Boolean> INFERNAL_CREEPER_BOOST = new FieldReference<>(true);
        public static final FieldReference<Boolean> SKELETON_FORCE_MELEE_ATTACK = new FieldReference<>(true);
        public static final FieldReference<Boolean> SKELETON_TRIPLE_SHOT = new FieldReference<>(true);
        public static final FieldReference<Integer> VILLAGER_WOOL_TO_EMERALD_SHARD_COUNT = new FieldReference<>(4);
        public static final FieldReference<Integer> VILLAGER_WOOL_TO_EMERALD_SHARD_SHARD_COUNT = new FieldReference<>(1);
        public static final FieldReference<Boolean> WOLVES_ATTACK_PLAYERS_WITH_MEAT = new FieldReference<>(true);
        public static final FieldReference<Boolean> ZOMBIE_DEFENSE = new FieldReference<>(true);
        public static final FieldReference<Double> ZOMBIE_PIGMAN_BOOST_CHANCE = new FieldReference<>(0.025);
        public static final FieldReference<Boolean> ZOMBIE_PIGMAN_USE_BOW = new FieldReference<>(true);

        public static class Animals{
            public static final FieldReference<Integer> ANIMAL_ILL_TO_DEATH_TIME = new FieldReference<>(144000);
            public static final FieldReference<Integer> BREED_XP_CHICKEN = new FieldReference<>(3);
            public static final FieldReference<Integer> BREED_XP_COW = new FieldReference<>(25);
            public static final FieldReference<Integer> BREED_XP_PIG = new FieldReference<>(15);
            public static final FieldReference<Integer> BREED_XP_SHEEP = new FieldReference<>(5);
        }
    }

    public static class GameMechanics{
        public static final FieldReference<Boolean> FIRST_DAY_LONGER_DAY_TIME = new FieldReference<>(true);
        public static final FieldReference<Double> FISHING_XP_SCALE = new FieldReference<>(3d);
        public static final FieldReference<Integer> IN_RAIN_DEBUFF_TIME = new FieldReference<>(3600);
        public static final FieldReference<Double> IN_WALL_DAMAGE_FOR_PLAYER = new FieldReference<>(10.0d);
        public static final FieldReference<Double> STEPPED_MOB_DAMAGE_FACTOR = new FieldReference<>(0.1);

        public static class MobSpawning{
            public static final FieldReference<Integer> ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY = new FieldReference<>(192);
            public static final FieldReference<Double> BLOOD_MOON_MAX_HOSTILE_FRACTION = new FieldReference<>(128d);
            public static final FieldReference<Integer> GHAST_SPAWN_LIMIT_DAY = new FieldReference<>(128);
            public static final FieldReference<Double> MOB_MAX_SPAWN_COUNT_INCREASE_PER_DAY = new FieldReference<>(1d);
        }

        public static class Nether{
            public static final FieldReference<Boolean> NETHERRACK_DAMAGE = new FieldReference<>(true);
            public static final FieldReference<Boolean> NETHER_DEBUFF = new FieldReference<>(true);
            public static final FieldReference<Integer> NETHER_DEBUFF_TIME = new FieldReference<>(72000);
        }

        public static class Underworld{
            public static final FieldReference<Boolean> UNDERWORLD_DEBUFF = new FieldReference<>(true);
            public static final FieldReference<Integer> UNDERWORLD_DEBUFF_PERIOD1 = new FieldReference<>(72000);
            public static final FieldReference<Integer> UNDERWORLD_DEBUFF_PERIOD2 = new FieldReference<>(108000);
            public static final FieldReference<Integer> UNDERWORLD_DEBUFF_PERIOD3 = new FieldReference<>(132000);
            public static final FieldReference<Boolean> UNDERWORLD_RANDOM_TELEPORT = new FieldReference<>(true);
            public static final FieldReference<Integer> UNDERWORLD_RANDOM_TELEPORT_TIME = new FieldReference<>(36000);
        }
    }

    public static class Item{

        public static final FieldReference<Integer> DIAMOND_EXP = new FieldReference<>(300);
        public static final FieldReference<Integer> QUARTZ_MAX_EXP_LEVEL = new FieldReference<>(55);

        public static class Enchantment{

            public static final FieldReference<Double> ALL_PROTECTION_V_DEFENCE_FRACTION = new FieldReference<>(0.1d);
            public static final FieldReference<Integer> CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL = new FieldReference<>(2);
            public static final FieldReference<Double> CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL = new FieldReference<>(0.4);
            public static final FieldReference<Integer> EMERGENCY_COOLDOWN = new FieldReference<>(72000);
        }

        public static class Recipes{
            public static final FieldReference<Integer> IRON_TO_MITHRIL_COOK_TIME = new FieldReference<>(6000);
            public static final FieldReference<Integer> MITHRIL_BLOCK_COUNT_TO_ADAMANTIUM = new FieldReference<>(1);
            public static final FieldReference<Integer> MITHRIL_TO_ADAMANTIUM_COOK_TIME = new FieldReference<>(9000);
        }
    }

    public static class Misc{

        public static final FieldReference<Boolean> LOG_PLAYERS_INTERACT_WITH_PORTAL = new FieldReference<>(true);

    }

    public static class WorldGen{

        public static class Overworld{
            public static final FieldReference<Integer> ADAMANTIUM_FREQUENCY_OVERWORLD = new FieldReference<>(1);
            public static final FieldReference<Integer> COPPER_FREQUENCY_OVERWORLD = new FieldReference<>(40);
            public static final FieldReference<Integer> DIAMOND_FREQUENCY_OVERWORLD = new FieldReference<>(5);
            public static final FieldReference<Integer> GOLD_FREQUENCY_OVERWORLD = new FieldReference<>(20);
            public static final FieldReference<Integer> IRON_FREQUENCY_OVERWORLD = new FieldReference<>(40);
            public static final FieldReference<Integer> LAPIS_FREQUENCY_OVERWORLD = new FieldReference<>(4);
            public static final FieldReference<Integer> MITHRIL_FREQUENCY_OVERWORLD = new FieldReference<>(10);
            public static final FieldReference<Boolean> OVERWORLD_ADAMANTITE_ORE = new FieldReference<>(false);
            public static final FieldReference<Integer> SILVER_FREQUENCY_OVERWORLD = new FieldReference<>(10);
        }
        public static class Underworld{
            public static final FieldReference<Integer> ADAMANTIUM_FREQUENCY_UNDERWORLD = new FieldReference<>(5);
            public static final FieldReference<Integer> COPPER_FREQUENCY_UNDERWORLD = new FieldReference<>(40);
            public static final FieldReference<Integer> DIAMOND_FREQUENCY_UNDERWORLD = new FieldReference<>(5);
            public static final FieldReference<Integer> GOLD_FREQUENCY_UNDERWORLD = new FieldReference<>(20);
            public static final FieldReference<Integer> IRON_FREQUENCY_UNDERWORLD = new FieldReference<>(60);
            public static final FieldReference<Integer> LAPIS_FREQUENCY_UNDERWORLD = new FieldReference<>(5);
            public static final FieldReference<Integer> MITHRIL_FREQUENCY_UNDERWORLD = new FieldReference<>(10);
            public static final FieldReference<Integer> SILVER_FREQUENCY_UNDERWORLD = new FieldReference<>(10);
        }
    }
}
