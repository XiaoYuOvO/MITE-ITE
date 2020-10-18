[中文版](https://github.com/XiaoYuOvO/MITE-ITE/blob/master/ChangeLogCN.md)
# B0.3.12-1 ChangeLog
* Fixed the problem that players can use dev commands without permission

---

# B0.3.12 ChangeLog
## Mobs
* Make bone lord and the ancient bone lord's health,damage,xp drops doubled **_(Can be disabled in config< name:`bone-lord-tweak`>)_**
* Make players will get 1 damage per second when they are standing netherrack in the nether **_(can be disabled in config< name:`netherrack-damage`>)_**
* Ghost will shoot three fireballs for a time
## Game mechanism
* Players will be teleported randomly if they stay for a time in underworld **_(can be modified<name:> or disable< name:`underworld-random-teleport-time`> in config)_**

_**Config file: `mite-ite-cfg.json`**_
 
---
# B0.3.11 ChangeLog
## Mobs
* After 128 days, pigmen in the nether have a 10% chance to spawn a special zombie pigman (with smoke effect prompt), which will add power and speed effects to the zombie pigmen in the surrounding 8 blocks (level and duration increase with the number of days)
* After 128 days, the monster has a 1/4 probability to obtain a speed effect, and a 1/5 probability to obtain a power effect (the higher the probability in the later stage, the higher the effect level)
* After 196 days, the skeleton has a 1/2 probability of using the fire bow
---
# B0.3.10 ChangeLog
## Mobs
* Blaze's max health changed to 40 and attack damage changed to 6
* Ghast's max health changed to 15,and it's fireball's explosion radio changed to 2(it was 1),the speed of shooting fireball changed to 1 fireball per 2.5s
* Wither skeleton's basic max health changed to 24(plus 1 per 16 days after 64 day),and it has a ancient metal sword now
* Zombie pigman's basic health changed to 50 and add 1 per 12 days after 64 day,it's basic attack damage added to 8 now(plus 1 per 48 days after 64 day)
## Others
* Players can sit on stairs now
---
# B0.3.9,B0.3.8,B0.3.7 ChangeLog
## Items
* Add vibranium bow(give 75% additional arrow speed)
* Make weapons and armors have levels,and they can get or strengthen a modifier when they level up(All current modifiers is shown below)
* Only vibranium items can have powerful enchantment
## Mobs
* Increased the health amount and attack damage of some mobs(Ancient bone lord,long dead,revenant,zombie,zombie pigman)
* Creeper's explosion radius will increase with the day
* Ancient bone lord,bone lord,revenant will have better armors in late period
* Make ender dragon stronger:
    + If you dont hit its head,you can only deal 20% damage
    + Increased the damage of it hit player(Now is 20)
    + Its max health is 500 now
    + It will always fly in the sky if its heal is lower than 150,and it will heal itself 2HP per 1.5s
    + The distance of ender crystal healing ender dragon is 48 blocks now
    + The dragon only take 5HP of damage when the nearby crystal exploded
    + Ender crystal will heal the dragon 2HP per 0.5s
* Skeleton will have better weapon(iron sword or iron dagger)

### _List of available modifiers_

   Name | Effect | Can use at
    --------------------------------|---------------------------------------------------------|---------------------
      Efficiency Modifier           | Make your tool mines faster                             | Tools
      Aquadynamic Modifier          | Make your tool mines faster when you're in water or rain| Tools
      Durability Modifier           | Make your item drops durability more slower             | Tools Weapons Armors
      Damage Modifier               | Make your weapon deals more damage                      | Weapons
      Slowdown Modifier             | Make your weapon slow down the entity that you hit      | Weapons
      Projectile Protection Modifier| Make you take less damage of projectiles                | Armors
      Explosion Protection Modifier | Make you take less damage of explosion                  | Armors
      Fire Protection Modifier      | Make you take less damage of fire                       | Armors
      Protection Modifier           | Make you take less damage                               | Armors
      Steady Modifier               | Make you not easy to be knockbacked                     | Armors
      Blessed Modifier              | Make you take less damage from undead mobs              | Armors
      
---
# B0.3.6 ChangeLog
## Items
* Add vibranium weapons and tools(they are better that adamantium's things,all the vibranium tools need use obsidian stick to craft) 
* Add vibranium anvil
* Add vibranium armors' and vibranium anvil's achievements
* Manure can be used as bonemeal to heal blighted crops
* Added blaze coal,and its heat value is 5 and burning time is 6800
## Blocks
* Add vibranium furnace(max heat level is 5)
* Add vibranium workbench
* The better the fuel in the furnace be,the faster the items burn
## Mobs
* Decreased the damage and health amount of most mobs
* Add a new variant of earth elemental,Wood Elemental,it only can be attacked by axe,battle axe,lava and fire
* Increase zombie pigman's damage and heal amount
* Creeper,Spider,Zombie,Skeleton's health and damage amount will increase as the day count increase(Skeleton will increase its knock back distance)
* As the number of days increases, the monsters will have better equipments (the highest level of armors is Adamantium, and may have enchantments)\
    _**(their own equipment will not drop, so don't think about getting rich with this)**_
## Recipes
* Make coal block can burn into diamond chunk(4 diamond chunks can make a diamond) in the heat level of 5(use blaze coal)
## Others
* Add vibranium's achievements
* Now if someone destroyed others' strong box the server will tell all the players
## Enchantments
* Increase Enchantment Looting,Fortune,Protection,Thorns's max level
* Now the enchantment sharpness,undead killer and Amputation killer can be compatible with each other in vibranium weapons
* Vibranium weapons and tools can use 15800 xp to enchant at most(use 4 layers of bookshelves and diamond enchantment table),and it can have 4 enchantments at most
* ## Weather
* Blood moon will occurs per 16 days after the blue moon
* Blue moon only occurs at 128 day
#### _And some little changes_

---
# B0.1.0 ChangeLog
## Entities
* Increased the health amount and attack damage of most mobs
* Make bone lord,ancient bone lord,longdead,longdead guardian,revenant and zombie have more powerful enchanted armors\
    (A chance 20% to have enchanted armor at each slot,every 64 days increase 10%,\
    enchantments of the armors also increase with the number of days)
* After 32 days, every other day, the mobs will have a speed effect and a 20% chance to get a strength effect at night
* Mobs won't drop their own armors unless they have picked up other armors or weapons\
    But they won't drop the item that has a low durability(less than quarter) 
* Mobs will have more powerful armors,Except for specific monsters\
    all monsters have a 20% chance to get equipment in each equipment slot\
    After 64 days, normal monster's equipments may be enchanted\
    (Every monster will have armors after 365 days,and the tier of armors will increase with the number of days)
* Skeleton's bow will have 5 level of enchantment arrow knockback
* Player can have 40 max health by level up

[B0.3.12 ChangeLog]: #b0312-changelog

