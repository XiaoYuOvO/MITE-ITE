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
                    addEntry(ConfigEntry.of("stepped_mob_damage_factor",GameMechanics.STEPPED_MOB_DAMAGE_PROGRESS_BASE).withComment("渐进伤害每次增幅点数基础值(小数)")).
                    addEntry(ConfigEntry.of("stepped_mob_damage_progress_max",GameMechanics.STEPPED_MOB_DAMAGE_PROGRESS_MAX).withComment("渐进伤害增幅最大值(小数)")).
                    addEntry(ConfigEntry.of("stepped_mob_damage_progress_increase_day",GameMechanics.STEPPED_MOB_DAMAGE_PROGRESS_INCREASE_DAY).withComment("渐进伤害每次增幅每增加一点所需天数(整数)")).
                    addEntry(ConfigEntry.of("stepped_player_damage_progress",GameMechanics.STEPPED_PLAYER_BASE_DAMAGE_MAX).withComment("玩家对怪物渐进伤害(不计附魔)最大值")).
                    addEntry(ConfigEntry.of("stepped_player_damage_increase_per_lvl",GameMechanics.STEPPED_PLAYER_DAMAGE_INCREASE_PER_LVL).withComment("玩家对怪物渐进伤害每级加成")).
                    addEntry(ConfigEntry.of("fishing_xp",GameMechanics.FISHING_XP_SCALE).withComment("钓鱼经验(整数)")).
                    addEntry(ConfigEntry.of("in_rain_debuff_time",GameMechanics.IN_RAIN_DEBUFF_TIME).withComment("雨中获得负面效果所需时长(整数)")).
                    addEntry(ConfigEntry.of("first_day_longer_day_time",GameMechanics.FIRST_DAY_LONGER_DAY_TIME).withComment("加长第一天时间(开关)")).
                    addEntry(ConfigEntry.of("player_defense_cooldown",GameMechanics.PLAYER_DEFENSE_COOLDOWN).withComment("玩家格挡冷却")).
                    addEntry(ConfigEntry.of("player_defence_max_time",GameMechanics.PLAYER_DEFENCE_MAX_TIME).withComment("玩家格挡最大时间")).
                    addEntry(ConfigEntry.of("player_disarm_player",GameMechanics.PLAYER_DISARM_PLAYER).withComment("玩家缴械玩家")).
                    addEntry(ConfigCategory.of("MobSpawning").
                            addEntry(ConfigEntry.of("blood_moon_max_hostile_fraction",GameMechanics.MobSpawning.BLOOD_MOON_MAX_HOSTILE_FRACTION).withComment("血月最大刷怪数量系数(小数)")).
                            addEntry(ConfigEntry.of("ghast_spawn_limit_day",GameMechanics.MobSpawning.GHAST_SPAWN_LIMIT_DAY).withComment("主世界生成恶魂所需天数(整数)")).
                            addEntry(ConfigEntry.of("annihilation_skeleton_spawn_in_light",GameMechanics.MobSpawning.ANNIHILATION_SKELETON_SPAWN_IN_LIGHT).withComment("主世界湮灭骷髅在光照出生成")).
                            addEntry(ConfigEntry.of("ancient_bone_lord_spawn_limit_day",GameMechanics.MobSpawning.ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY).withComment("主世界生成远古骷髅公爵所需天数(整数)")).
                            addEntry(ConfigEntry.of("wandering_witch_spawn_chance_overworld",GameMechanics.MobSpawning.WANDERING_WITCH_SPAWN_CHANCE_OVERWORLD).withComment("主世界流浪女巫生成概率百分比(整数)")).
                            addEntry(ConfigEntry.of("wandering_witch_spawn_limit_day_overworld",GameMechanics.MobSpawning.WANDERING_WITCH_SPAWN_LIMIT_DAY_OVERWORLD).withComment("主世界流浪女巫生成天数限制(整数)")).
                            addEntry(ConfigEntry.of("wandering_witch_spawn_limit_day_other",GameMechanics.MobSpawning.WANDERING_WITCH_SPAWN_LIMIT_DAY_OTHER).withComment("地狱及地底世界流浪女巫生成天数限制(整数)")).
                            addEntry(ConfigEntry.of("wandering_witch_spawn_chance_underworld",GameMechanics.MobSpawning.WANDERING_WITCH_SPAWN_CHANCE_UNDERWORLD).withComment("地底世界流浪女巫生成概率百分比(整数)")).
                            addEntry(ConfigEntry.of("mob_max_spawn_count_increase_per_day",GameMechanics.MobSpawning.MOB_MAX_SPAWN_COUNT_INCREASE_PER_DAY).withComment("每天刷怪数量上限增量(整数)"))).
                    addEntry(ConfigCategory.of("Underworld").
                            addEntry(ConfigEntry.of("underworld_random_teleport",GameMechanics.Underworld.UNDERWORLD_RANDOM_TELEPORT).withComment("地底世界随机传送(开关)")).
                            addEntry(ConfigEntry.of("underworld_random_teleport_time_new",GameMechanics.Underworld.UNDERWORLD_RANDOM_TELEPORT_TIME).withComment("地底世界随机传送时间(整数)")).
                            addEntry(ConfigEntry.of("underworld_debuff",GameMechanics.Underworld.UNDERWORLD_DEBUFF).withComment("地底世界负面效果(开关)")).
                            addEntry(ConfigEntry.of("underworld_debuff_period1",GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD1).withComment("地底世界负面效果第一阶段触发时间(整数)")).
                            addEntry(ConfigEntry.of("underworld_debuff_period2",GameMechanics.Underworld.UNDERWORLD_DEBUFF_PERIOD2).withComment("地底世界负面效果第二阶段触发时间(整数)"))).
                    addEntry(ConfigCategory.of("Nether").
                            addEntry(ConfigEntry.of("netherrack_damage",GameMechanics.Nether.NETHERRACK_DAMAGE).withComment("地狱烫脚(开关)")).
                            addEntry(ConfigEntry.of("netherrack_damage_limit_day",GameMechanics.Nether.NETHERRACK_DAMAGE_LIMIT_DAY).withComment("地狱烫脚所需天数(整数)")).
                            addEntry(ConfigEntry.of("nether_debuff",GameMechanics.Nether.NETHER_DEBUFF).withComment("地狱负面效果(开关)")).
                            addEntry(ConfigEntry.of("nether_debuff_time",GameMechanics.Nether.NETHER_DEBUFF_TIME).withComment("地狱负面效果触发时间")))).
            addEntry(ConfigCategory.of("Item").
                    addEntry(ConfigEntry.of("quartz_max_exp_level",Item.QUARTZ_MAX_EXP_LEVEL).withComment("石英可提供经验最大等级")).
                    addEntry(ConfigEntry.of("diamond_exp",Item.DIAMOND_EXP).withComment("钻石经验")).
                    addEntry(ConfigEntry.of("emerald_exp",Item.EMERALD_EXP).withComment("绿宝石经验")).
                    addEntry(ConfigEntry.of("quartz_exp",Item.QUARTZ_EXP).withComment("石英经验")).
                    addEntry(ConfigEntry.of("golden_apple_eat_time",Item.GOLDEN_APPLE_EAT_TIME).withComment("金苹果使用耗时")).
                    addEntry(ConfigEntry.of("enchanted_golden_apple_eat_time",Item.ENCHANTED_GOLDEN_APPLE_EAT_TIME).withComment("附魔金苹果使用耗时")).
                    addEntry(ConfigCategory.of("Recipes").
                            addEntry(ConfigEntry.of("iron_to_mithril_cook_time",Item.Recipes.IRON_TO_MITHRIL_COOK_TIME).withComment("铁烧制为秘银耗时")).
                            addEntry(ConfigEntry.of("iron_block_count_to_mithril",Item.Recipes.IRON_BLOCK_COUNT_TO_MITHRIL).withComment("铁烧制为秘银所需铁块数量")).
                            addEntry(ConfigEntry.of("mithril_to_adamantium_cook_time",Item.Recipes.MITHRIL_TO_ADAMANTIUM_COOK_TIME).withComment("秘银烧制为艾德曼耗时")).
                            addEntry(ConfigEntry.of("mithril_block_count_to_adamantium",Item.Recipes.MITHRIL_BLOCK_COUNT_TO_ADAMANTIUM).withComment("秘银烧制为艾德曼所需秘银块数量"))).
                    addEntry(ConfigCategory.of("Enchantment").
                            addEntry(ConfigEntry.of("crit_enchantment_chance_boost_per_lvl",Item.Enchantment.CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL).withComment("暴击附魔每级暴击几率")).
                            addEntry(ConfigEntry.of("crit_enchantment_damage_boost_per_lvl",Item.Enchantment.CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL).withComment("暴击附魔每级额外伤害")).
                            addEntry(ConfigEntry.of("emergency_cooldown_",Item.Enchantment.EMERGENCY_COOLDOWN).withComment("紧急守备冷却")).
                            addEntry(ConfigEntry.of("emergency_cooldown_reduce_every_armor",Item.Enchantment.EMERGENCY_COOLDOWN_REDUCE_EVERY_ARMOR).withComment("紧急守备每件装备冷却减免百分比")).
                            addEntry(ConfigEntry.of("emergency_health_recover_amount",Item.Enchantment.EMERGENCY_HEALTH_RECOVER_AMOUNT).withComment("紧急守备触发后恢复至的血量")).
                            addEntry(ConfigEntry.of("conqueror_damage_boost_per_lvl",Item.Enchantment.CONQUEROR_DAMAGE_BOOST_PER_LVL).withComment("征服者每级额外伤害")).
                            addEntry(ConfigEntry.of("beheading_chance_boost_per_lvl",Item.Enchantment.BEHEADING_CHANCE_BOOST_PER_LVL).withComment("斩首每级头颅额外掉落几率")).
                            addEntry(ConfigEntry.of("all_protection_v_defence_fraction",Item.Enchantment.ALL_PROTECTION_V_DEFENCE_FRACTION).withComment("全套保护5伤害减免")))).
            addEntry(ConfigCategory.of("Block").
                    addEntry(ConfigEntry.of("mob_spawner_speed_up_with_players",Block.MOB_SPAWNER_SPEED_UP_WITH_PLAYERS).withComment("刷怪笼在旁边有玩家时加速生怪"))).
            addEntry(ConfigCategory.of("Entities").
                    addEntry(ConfigEntry.of("bone_lord_tweak",Entities.BONE_LORD_TWEAK).withComment("骷髅领主增强")).
                    addEntry(ConfigEntry.of("skeleton_triple_shot",Entities.SKELETON_TRIPLE_SHOT).withComment("三发骷髅")).
                    addEntry(ConfigEntry.of("compressed_skeleton",Entities.COMPRESSED_SKELETON).withComment("分裂骷髅")).
                    addEntry(ConfigEntry.of("compressed_skeleton_crack_stand_time",Entities.COMPRESSED_SKELETON_CRACK_STAND_TIME).withComment("分裂骷髅分裂后停滞时间(tick)")).
                    addEntry(ConfigEntry.of("compressed_skeleton_expand_count",Entities.COMPRESSED_SKELETON_EXPAND_COUNT).withComment("分裂骷髅分裂数量")).
                    addEntry(ConfigEntry.of("skeleton_force_melee_attack",Entities.SKELETON_FORCE_MELEE_ATTACK).withComment("强制骷髅近战")).
                    addEntry(ConfigEntry.of("bone_lord_and_longdead_change_weapon_chance",Entities.BONE_LORD_AND_LONGDEAD_CHANGE_WEAPON_CHANCE).withComment("古尸和骷髅领主会切换武器概率")).
                    addEntry(ConfigEntry.of("bat_poison_attack",Entities.BAT_POISON_ATTACK).withComment("吸血蝙蝠攻击使玩家中毒")).
                    addEntry(ConfigEntry.of("infernal_creeper_boost",Entities.INFERNAL_CREEPER_BOOST).withComment("增强地狱爬行者")).
                    addEntry(ConfigEntry.of("zombie_pigman_use_bow",Entities.ZOMBIE_PIGMAN_USE_BOW).withComment("猪人带弓")).
                    addEntry(ConfigEntry.of("zombie_pigman_boost_chance",Entities.ZOMBIE_PIGMAN_BOOST_CHANCE).withComment("精英猪人生成概率")).
                    addEntry(ConfigEntry.of("zombie_pigman_attack_animails",Entities.ZOMBIE_PIGMAN_ATTACK_ANIMAILS).withComment("僵尸猪人攻击动物")).
                    addEntry(ConfigEntry.of("ghast_overworld_boost",Entities.GHAST_OVERWORLD_BOOST).withComment("主世界恶魂火球爆炸范围增强倍数")).
                    addEntry(ConfigEntry.of("wolves_attack_players_with_meat",Entities.WOLVES_ATTACK_PLAYERS_WITH_MEAT).withComment("狼攻击快捷栏有肉的玩家")).
                    addEntry(ConfigEntry.of("villager_wool_to_emerald_shard_count",Entities.VILLAGER_WOOL_TO_EMERALD_SHARD_COUNT).withComment("村民用羊毛换绿宝石碎片-羊毛所需数量")).
                    addEntry(ConfigEntry.of("villager_wool_to_emerald_shard_shard_count",Entities.VILLAGER_WOOL_TO_EMERALD_SHARD_SHARD_COUNT).withComment("村民用羊毛换绿宝石碎片-绿宝石碎片获得数量")).
                    addEntry(ConfigEntry.of("can_boost_iron_golem",Entities.CAN_BOOST_IRON_GOLEM).withComment("可以强化铁傀儡")).
                    addEntry(ConfigEntry.of("mob_defense",Entities.MOB_DEFENSE).withComment("怪物格挡")).
                    addEntry(ConfigEntry.of("mob_disarm_when_defence",Entities.MOB_DISARM_WHEN_DEFENCE).withComment("怪物格挡时若有缴械附魔有几率缴械")).
                    addEntry(ConfigEntry.of("mob_disarm_when_defence",Entities.MOB_DISARM_WHEN_ATTACK).withComment("怪物攻击时若有缴械附魔有几率缴械")).
                    addEntry(ConfigEntry.of("mob_attack_cause_fire",Entities.MOB_ATTACK_CAUSE_FIRE).withComment("怪物有几率在手持装备攻击时使目标着火")).
                    addEntry(ConfigEntry.of("annihilation_skeleton_despawn_time",Entities.ANNIHILATION_SKELETON_DESPAWN_TIME).withComment("湮灭骷髅被攻击后消失用时")).
                    addEntry(ConfigEntry.of("ender_dragon_attack_wither_chance",Entities.ENDER_DRAGON_HURT_WITHER_CHANCE).withComment("末影龙受攻击后给予玩家凋零几率")).
                    addEntry(ConfigEntry.of("ender_dragon_immune_to_arrow_health_percent",Entities.ENDER_DRAGON_IMMUNE_TO_ARROW_HEALTH_PERCENT).withComment("末影龙免疫弓箭血量百分比")).
                    addEntry(ConfigEntry.of("ender_dragon_attack_slowness",Entities.ENDER_DRAGON_ATTACK_SLOWNESS).withComment("末影龙攻击玩家给予玩家缓慢效果")).
                    addEntry(ConfigEntry.of("nether_mod_attack_fire_chance",Entities.NETHER_MOD_ATTACK_FIRE_CHANCE).withComment("地狱生物攻击火焰附加概率")).
                    addEntry(ConfigEntry.of("slime_attack_damage_multiplier",Entities.SLIME_ATTACK_DAMAGE_MULTIPLIER).withComment("绿色史莱姆伤害倍数")).
                    addEntry(ConfigEntry.of("slime_attack_damage_bonus",Entities.SLIME_ATTACK_DAMAGE_BONUS).withComment("绿色史莱姆伤害加成")).
                    addEntry(ConfigEntry.of("giant_zombie_spawn_zombie_cooldown",Entities.GIANT_ZOMBIE_SPAWN_ZOMBIE_COOLDOWN).withComment("巨型僵尸生成僵尸间隔")).
                    addEntry(ConfigCategory.of("Animals").
                            addEntry(ConfigEntry.of("animal_ill_to_death_time",Entities.Animals.ANIMAL_ILL_TO_DEATH_TIME).withComment("生物生病死亡时间")).
                            addEntry(ConfigEntry.of("breed_xp_chicken",Entities.Animals.BREED_XP_CHICKEN).withComment("鸡繁殖获得经验")).
                            addEntry(ConfigEntry.of("breed_xp_sheep",Entities.Animals.BREED_XP_SHEEP).withComment("羊繁殖获得经验")).
                            addEntry(ConfigEntry.of("breed_xp_pig",Entities.Animals.BREED_XP_PIG).withComment("猪繁殖获得经验")).
                            addEntry(ConfigEntry.of("breed_xp_cow",Entities.Animals.BREED_XP_COW).withComment("牛繁殖获得经验")))).
            addEntry(ConfigCategory.of("WorldGen").
                    addEntry(ConfigCategory.of("Overworld").
                            addEntry(ConfigEntry.of("overworld_adamantite_ore", WorldGen.Overworld.OVERWORLD_ADAMANTITE_ORE).withComment("主世界生成艾德曼")).
                            addEntry(ConfigEntry.of("copper_frequency_overworld",WorldGen.Overworld.COPPER_FREQUENCY_OVERWORLD).withComment("主世界铜矿生成频率")).
                            addEntry(ConfigEntry.of("silver_frequency_overworld",WorldGen.Overworld.SILVER_FREQUENCY_OVERWORLD).withComment("主世界银矿生成频率")).
                            addEntry(ConfigEntry.of("iron_frequency_overworld",WorldGen.Overworld.IRON_FREQUENCY_OVERWORLD).withComment("主世界铁矿生成频率")).
                            addEntry(ConfigEntry.of("gold_frequency_overworld",WorldGen.Overworld.GOLD_FREQUENCY_OVERWORLD).withComment("主世界金矿生成频率")).
                            addEntry(ConfigEntry.of("mithril_frequency_overworld",WorldGen.Overworld.MITHRIL_FREQUENCY_OVERWORLD).withComment("主世界秘银矿生成频率")).
                            addEntry(ConfigEntry.of("lapis_frequency_overworld",WorldGen.Overworld.LAPIS_FREQUENCY_OVERWORLD).withComment("主世界青金石矿生成频率")).
                            addEntry(ConfigEntry.of("diamond_frequency_overworld",WorldGen.Overworld.DIAMOND_FREQUENCY_OVERWORLD).withComment("主世界钻石矿生成频率")).
                            addEntry(ConfigEntry.of("adamantium_frequency_overworld",WorldGen.Overworld.ADAMANTIUM_FREQUENCY_OVERWORLD).withComment("主世界艾德曼矿生成频率"))).
                    addEntry(ConfigCategory.of("Underworld").
                            addEntry(ConfigEntry.of("copper_frequency_underworld",WorldGen.Underworld.COPPER_FREQUENCY_UNDERWORLD).withComment("地底世界铜矿生成频率")).
                            addEntry(ConfigEntry.of("silver_frequency_underworld",WorldGen.Underworld.SILVER_FREQUENCY_UNDERWORLD).withComment("地底世界银矿生成频率")).
                            addEntry(ConfigEntry.of("iron_frequency_underworld",WorldGen.Underworld.IRON_FREQUENCY_UNDERWORLD).withComment("地底世界铁矿生成频率")).
                            addEntry(ConfigEntry.of("gold_frequency_underworld",WorldGen.Underworld.GOLD_FREQUENCY_UNDERWORLD).withComment("地底世界金矿生成频率")).
                            addEntry(ConfigEntry.of("mithril_frequency_underworld",WorldGen.Underworld.MITHRIL_FREQUENCY_UNDERWORLD).withComment("地底世界秘银矿生成频率")).
                            addEntry(ConfigEntry.of("lapis_frequency_underworld",WorldGen.Underworld.LAPIS_FREQUENCY_UNDERWORLD).withComment("地底世界青金石矿生成频率")).
                            addEntry(ConfigEntry.of("diamond_frequency_underworld",WorldGen.Underworld.DIAMOND_FREQUENCY_UNDERWORLD).withComment("地底世界钻石矿生成频率")).
                            addEntry(ConfigEntry.of("adamantium_frequency_underworld",WorldGen.Underworld.ADAMANTIUM_FREQUENCY_UNDERWORLD).withComment("地底世界艾德曼矿生成频率")).
                            addEntry(ConfigEntry.of("underworld_mantle_block_offset",WorldGen.Underworld.UNDERWORLD_MANTLE_BLOCK_OFFSET).withComment("地底世界地幔位置向上偏移"))).
                    addEntry(ConfigCategory.of("Nether").
                            addEntry(ConfigEntry.of("nether_adamantium_max_count_per_chunk",WorldGen.Nether.NETHER_ADAMANTIUM_MAX_COUNT_PER_CHUNK).withComment("地狱艾德曼每个区块最大生成数量")).
                            addEntry(ConfigEntry.of("nether_adamantium_max_count_per_vein",WorldGen.Nether.NETHER_ADAMANTIUM_MAX_COUNT_PER_VEIN).withComment("地狱艾德曼每个矿脉最大矿物生成数量")))).
            addEntry(ConfigCategory.of("Client").
                    addEntry(ConfigCategory.of("Gui").
                            addEntry(ConfigEntry.of("health_bar_x_offset",Client.Gui.HEALTH_BAR_X_OFFSET).withComment("营养条GUI横向偏移")).
                            addEntry(ConfigEntry.of("health_bar_y_offset",Client.Gui.HEALTH_BAR_Y_OFFSET).withComment("营养条GUI纵向偏移")))).
            addEntry(ConfigCategory.of("Misc").
                    addEntry(ConfigEntry.of("log_players_interact_with_portal",Misc.LOG_PLAYERS_INTERACT_WITH_PORTAL).withComment("玩家通过传送门时发送警告(开关)")));

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
        public static final FieldReference<Integer> COMPRESSED_SKELETON_CRACK_STAND_TIME = new FieldReference<>(20);
        public static final FieldReference<Float> BONE_LORD_AND_LONGDEAD_CHANGE_WEAPON_CHANCE = new FieldReference<>(0.25f);
        public static final FieldReference<Integer> VILLAGER_WOOL_TO_EMERALD_SHARD_COUNT = new FieldReference<>(4);
        public static final FieldReference<Integer> VILLAGER_WOOL_TO_EMERALD_SHARD_SHARD_COUNT = new FieldReference<>(1);
        public static final FieldReference<Boolean> WOLVES_ATTACK_PLAYERS_WITH_MEAT = new FieldReference<>(true);
        public static final FieldReference<Boolean> MOB_ATTACK_CAUSE_FIRE = new FieldReference<>(true);
        public static final FieldReference<Boolean> MOB_DEFENSE = new FieldReference<>(true);
        public static final FieldReference<Boolean> MOB_DISARM_WHEN_ATTACK = new FieldReference<>(true);
        public static final FieldReference<Boolean> MOB_DISARM_WHEN_DEFENCE = new FieldReference<>(true);
        public static final FieldReference<Double> ZOMBIE_PIGMAN_BOOST_CHANCE = new FieldReference<>(0.025);
        public static final FieldReference<Boolean> ZOMBIE_PIGMAN_USE_BOW = new FieldReference<>(true);
        public static final FieldReference<Boolean> ZOMBIE_PIGMAN_ATTACK_ANIMAILS = new FieldReference<>(false);
        public static final FieldReference<Integer> ANNIHILATION_SKELETON_DESPAWN_TIME = new FieldReference<>(3600);
        public static final FieldReference<Float> ENDER_DRAGON_HURT_WITHER_CHANCE = new FieldReference<>(0.2f);
        public static final FieldReference<Float> ENDER_DRAGON_IMMUNE_TO_ARROW_HEALTH_PERCENT = new FieldReference<>(0.3f);
        public static final FieldReference<Boolean> ENDER_DRAGON_ATTACK_SLOWNESS = new FieldReference<>(true);
        public static final FieldReference<Float> NETHER_MOD_ATTACK_FIRE_CHANCE = new FieldReference<>(0.5f);
        public static final FieldReference<Float> SLIME_ATTACK_DAMAGE_BONUS = new FieldReference<>(4f);
        public static final FieldReference<Integer> SLIME_ATTACK_DAMAGE_MULTIPLIER = new FieldReference<>(1);
        public static class Animals{
            public static final FieldReference<Integer> ANIMAL_ILL_TO_DEATH_TIME = new FieldReference<>(144000);
            public static final FieldReference<Integer> BREED_XP_CHICKEN = new FieldReference<>(8);
            public static final FieldReference<Integer> BREED_XP_COW = new FieldReference<>(25);
            public static final FieldReference<Integer> BREED_XP_PIG = new FieldReference<>(15);
            public static final FieldReference<Integer> BREED_XP_SHEEP = new FieldReference<>(10);
        }
    }

    public static class GameMechanics{
        public static final FieldReference<Boolean> FIRST_DAY_LONGER_DAY_TIME = new FieldReference<>(true);
        public static final FieldReference<Integer> FISHING_XP_SCALE = new FieldReference<>(10);
        public static final FieldReference<Integer> IN_RAIN_DEBUFF_TIME = new FieldReference<>(3600);
        public static final FieldReference<Double> IN_WALL_DAMAGE_FOR_PLAYER = new FieldReference<>(10.0d);
        public static final FieldReference<Integer> PLAYER_DEFENCE_MAX_TIME = new FieldReference<>(2 * 20);
        public static final FieldReference<Integer> PLAYER_DEFENSE_COOLDOWN = new FieldReference<>(20 * 8);
        public static final FieldReference<Boolean> PLAYER_DISARM_PLAYER = new FieldReference<>(true);
        public static final FieldReference<Double> STEPPED_MOB_DAMAGE_PROGRESS_BASE = new FieldReference<>(1d);
        public static final FieldReference<Integer> STEPPED_MOB_DAMAGE_PROGRESS_INCREASE_DAY = new FieldReference<>(24);
        public static final FieldReference<Double> STEPPED_MOB_DAMAGE_PROGRESS_MAX = new FieldReference<>(4d);
        public static final FieldReference<Double> STEPPED_PLAYER_BASE_DAMAGE_MAX = new FieldReference<>(2d);
        public static final FieldReference<Double> STEPPED_PLAYER_DAMAGE_INCREASE_PER_LVL = new FieldReference<>(0.1d);
        public static class MobSpawning{
            public static final FieldReference<Integer> ANCIENT_BONE_LORD_SPAWN_LIMIT_DAY = new FieldReference<>(192);
            public static final FieldReference<Double> BLOOD_MOON_MAX_HOSTILE_FRACTION = new FieldReference<>(128d);
            public static final FieldReference<Integer> GHAST_SPAWN_LIMIT_DAY = new FieldReference<>(128);
            public static final FieldReference<Boolean> ANNIHILATION_SKELETON_SPAWN_IN_LIGHT = new FieldReference<>(false);
            public static final FieldReference<Double> MOB_MAX_SPAWN_COUNT_INCREASE_PER_DAY = new FieldReference<>(1d);
            public static final FieldReference<Integer> WANDERING_WITCH_SPAWN_LIMIT_DAY_OTHER = new FieldReference<>(64);
            public static final FieldReference<Integer> WANDERING_WITCH_SPAWN_LIMIT_DAY_OVERWORLD = new FieldReference<>(64);
            public static final FieldReference<Integer> WANDERING_WITCH_SPAWN_CHANCE_OVERWORLD = new FieldReference<>(33);
            public static final FieldReference<Integer> WANDERING_WITCH_SPAWN_CHANCE_UNDERWORLD = new FieldReference<>(50);
        }

        public static class Nether{
            public static final FieldReference<Boolean> NETHERRACK_DAMAGE = new FieldReference<>(true);
            public static final FieldReference<Integer> NETHERRACK_DAMAGE_LIMIT_DAY = new FieldReference<>(96);
            public static final FieldReference<Boolean> NETHER_DEBUFF = new FieldReference<>(true);
            public static final FieldReference<Integer> NETHER_DEBUFF_TIME = new FieldReference<>(72000);
        }

        public static class Underworld{
            public static final FieldReference<Boolean> UNDERWORLD_DEBUFF = new FieldReference<>(true);
            public static final FieldReference<Integer> UNDERWORLD_DEBUFF_PERIOD1 = new FieldReference<>(72000);
            public static final FieldReference<Integer> UNDERWORLD_DEBUFF_PERIOD2 = new FieldReference<>(108000);
            public static final FieldReference<Boolean> UNDERWORLD_RANDOM_TELEPORT = new FieldReference<>(true);
            public static final FieldReference<Integer> UNDERWORLD_RANDOM_TELEPORT_TIME = new FieldReference<>(132000);
        }
    }

    public static class Item{
        public static final FieldReference<Integer> DIAMOND_EXP = new FieldReference<>(400);
        public static final FieldReference<Integer> EMERALD_EXP = new FieldReference<>(300);
        public static final FieldReference<Integer> ENCHANTED_GOLDEN_APPLE_EAT_TIME = new FieldReference<>(10);
        public static final FieldReference<Integer> QUARTZ_MAX_EXP_LEVEL = new FieldReference<>(55);
        public static final FieldReference<Integer> GOLDEN_APPLE_EAT_TIME = new FieldReference<>(20);
        public static final FieldReference<Integer> QUARTZ_EXP = new FieldReference<>(35);

        public static class Enchantment{

            public static final FieldReference<Double> ALL_PROTECTION_V_DEFENCE_FRACTION = new FieldReference<>(0.1d);
            public static final FieldReference<Integer> CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL = new FieldReference<>(2);
            public static final FieldReference<Double> CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL = new FieldReference<>(0.4);
            public static final FieldReference<Integer> EMERGENCY_COOLDOWN = new FieldReference<>(120000);
            public static final FieldReference<Double> EMERGENCY_COOLDOWN_REDUCE_EVERY_ARMOR = new FieldReference<>(0.2);
            public static final FieldReference<Integer> EMERGENCY_HEALTH_RECOVER_AMOUNT = new FieldReference<>(10);
            public static final FieldReference<Double> CONQUEROR_DAMAGE_BOOST_PER_LVL = new FieldReference<>(1d);
            public static final FieldReference<Float> BEHEADING_CHANCE_BOOST_PER_LVL = new FieldReference<>(0.05f);
        }

        public static class Recipes{
            public static final FieldReference<Integer> IRON_TO_MITHRIL_COOK_TIME = new FieldReference<>(6000);
            public static final FieldReference<Integer> IRON_BLOCK_COUNT_TO_MITHRIL = new FieldReference<>(2);
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
            public static final FieldReference<Integer> ADAMANTIUM_FREQUENCY_UNDERWORLD = new FieldReference<>(8);
            public static final FieldReference<Integer> COPPER_FREQUENCY_UNDERWORLD = new FieldReference<>(40);
            public static final FieldReference<Integer> DIAMOND_FREQUENCY_UNDERWORLD = new FieldReference<>(5);
            public static final FieldReference<Integer> GOLD_FREQUENCY_UNDERWORLD = new FieldReference<>(20);
            public static final FieldReference<Integer> IRON_FREQUENCY_UNDERWORLD = new FieldReference<>(60);
            public static final FieldReference<Integer> LAPIS_FREQUENCY_UNDERWORLD = new FieldReference<>(5);
            public static final FieldReference<Integer> MITHRIL_FREQUENCY_UNDERWORLD = new FieldReference<>(10);
            public static final FieldReference<Integer> SILVER_FREQUENCY_UNDERWORLD = new FieldReference<>(10);
            public static final FieldReference<Integer> UNDERWORLD_MANTLE_BLOCK_OFFSET = new FieldReference<>(75);
        }

        public static class Nether{
            public static final FieldReference<Integer> NETHER_ADAMANTIUM_MAX_COUNT_PER_CHUNK = new FieldReference<>(3);
            public static final FieldReference<Integer> NETHER_ADAMANTIUM_MAX_COUNT_PER_VEIN = new FieldReference<>(2);
        }
    }
}
