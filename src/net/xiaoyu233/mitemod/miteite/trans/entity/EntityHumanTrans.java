package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.util.Config;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;

import java.util.List;
import java.util.Random;

import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.dyCast;

@Transform(EntityHuman.class)
public abstract class EntityHumanTrans extends EntityLiving implements ICommandListener {

    @Link
    public PlayerInventory bn;
    @Link
    public float vision_dimming;
    @Link
    protected int br;
    @Link
    public float bs;
    @Link
    public float bt;
    @Link
    public PlayerAbilities bG;
    @Link
    protected float bL;
    @Link
    public boolean collided_with_gelatinous_cube;
    public int bJ;
    public ChunkCoordinates bed_location;
    private ChunkCoordinates c;
    private boolean d;
    protected FoodMetaData bq;
    private InventoryEnderChest a;
    private int underworldRandomTeleportTime;
    private boolean is_runegate_teleporting;


    @Marker
    public EntityHumanTrans(World par1World) {
        super(par1World);
    }

    @Override
    @Marker
    public void c(int i, ItemStack itemStack) {

    }

    @Marker
    public int bw() {return 0;}

    @Marker
    public void wakeUpPlayer(boolean get_out_of_bed, Entity entity_to_look_at) {}

    public void a(NBTTagCompound par1NBTTagCompound) {
        this.bJ = par1NBTTagCompound.e("experience");
        if (par1NBTTagCompound.b("XpTotal")) {
            this.bJ = par1NBTTagCompound.e("XpTotal");
        }

        super.a(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.m("Inventory");
        this.bn.b(var2);
        this.bn.c = par1NBTTagCompound.e("SelectedItemSlot");
        this.c(par1NBTTagCompound.e("Score"));
        if (this.inBed()) {
            this.bed_location = new ChunkCoordinates(MathHelper.c(this.u), MathHelper.c(this.v), MathHelper.c(this.w));
            this.wakeUpPlayer(true, null);
        }

        if (par1NBTTagCompound.b("SpawnX") && par1NBTTagCompound.b("SpawnY") && par1NBTTagCompound.b("SpawnZ")) {
            this.c = new ChunkCoordinates(par1NBTTagCompound.e("SpawnX"), par1NBTTagCompound.e("SpawnY"), par1NBTTagCompound.e("SpawnZ"));
            this.d = par1NBTTagCompound.n("SpawnForced");
        }

        this.bq.a(par1NBTTagCompound);
        this.bG.b(par1NBTTagCompound);
        if (par1NBTTagCompound.b("EnderItems")) {
            NBTTagList var3 = par1NBTTagCompound.m("EnderItems");
            this.a.a(var3);
        }
        this.underworldRandomTeleportTime = par1NBTTagCompound.e("UnderWorldTeleportTime");
        this.vision_dimming = par1NBTTagCompound.g("vision_dimming");
    }

    public void b(NBTTagCompound par1NBTTagCompound) {
        par1NBTTagCompound.a("experience", this.bJ);
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.a("Inventory", this.bn.a(new NBTTagList()));
        par1NBTTagCompound.a("SelectedItemSlot", this.bn.c);
        par1NBTTagCompound.a("Score", this.bw());
        if (this.c != null) {
            par1NBTTagCompound.a("SpawnX", this.c.a);
            par1NBTTagCompound.a("SpawnY", this.c.b);
            par1NBTTagCompound.a("SpawnZ", this.c.c);
            par1NBTTagCompound.a("SpawnForced", this.d);
        }

        this.bq.b(par1NBTTagCompound);
        this.bG.a(par1NBTTagCompound);
        par1NBTTagCompound.a("EnderItems", this.a.h());
        par1NBTTagCompound.a("UnderWorldTeleportTime",this.underworldRandomTeleportTime);
        par1NBTTagCompound.a("vision_dimming", this.vision_dimming);
    }

    public void c() {
        if (this.q.I) {
            if (this.vision_dimming < 0.01F) {
                this.vision_dimming = 0.0F;
            } else if (this.vision_dimming > 2.0F) {
                this.vision_dimming = 2.0F;
            } else {
                this.vision_dimming -= 0.01F;
            }
        }

        if (this.br > 0) {
            --this.br;
        }

        if (this.q.r == 0 && this.aN() < this.aT() && this.q.O().b("naturalRegeneration") && this.ac % 20 * 12 == 0) {
            this.f(1.0F);
        }

        this.bn.k();
        this.bs = this.bt;
        super.c();
        AttributeInstance var1 = this.a(GenericAttributes.d);
        if (!this.q.I) {
            var1.a(this.bG.b());
        }

        this.aR = this.bL;
        if (this.ai()) {
            this.aR = (float)((double)this.aR + (double)this.bL * 0.3D);
        }

        if (!this.q.I ){
            if (this.q.isUnderworld() && Config.ConfigEntry.UNDERWORLD_RANDOM_TELEPORT.getFrom(MITEITEMod.CONFIG)) {
                double randomTeleportTime = Config.ConfigEntry.UNDERWORLD_RANDOM_TELEPORT_TIME.getFrom(MITEITEMod.CONFIG);
                this.underworldRandomTeleportTime++;
                double timeToTeleport = randomTeleportTime - this.underworldRandomTeleportTime;
                if (timeToTeleport == 1200){
                    this.a(ChatMessage.e("---你将于一分钟后被随机传送,请做好准备!!!---").a(EnumChatFormat.o));
                }
                if (timeToTeleport <= 200 && this.underworldRandomTeleportTime % 20 == 0){
                    this.a(ChatMessage.e("!!!你将于"+ timeToTeleport / 20 +"秒后被随机传送!!!").a(EnumChatFormat.m));
                }
                if (this.underworldRandomTeleportTime > randomTeleportTime) {
                    if (ReflectHelper.dyCast(EntityHuman.class,this) instanceof EntityPlayer){
                        this.initiateRunegateTeleport(this.q.getAsWorldServer(),this.getBlockPosX(),this.getBlockPosY(),this.getBlockPosZ(), (ReflectHelper.dyCast(this)),false);
                    }
                    this.underworldRandomTeleportTime = 0;
                }
            }else if (this.underworldRandomTeleportTime > 0){
                this.underworldRandomTeleportTime--;
            }
        }

        if (!this.hasFoodEnergy()) {
            this.aR *= 0.75F;
        }

        this.aR *= this.getSpeedBoostOrSlowDownFactor();
        this.i((float)var1.e());
        float var2 = MathHelper.a(this.x * this.x + this.z * this.z);
        float var3 = (float)Math.atan(-this.y * 0.20000000298023224D) * 15.0F;
        if (var2 > 0.1F) {
            var2 = 0.1F;
        }

        if (!this.F || this.aN() <= 0.0F) {
            var2 = 0.0F;
        }

        if (this.F || this.aN() <= 0.0F) {
            var3 = 0.0F;
        }

        this.bt += (var2 - this.bt) * 0.4F;
        this.aK += (var3 - this.aK) * 0.8F;
        if (this.aN() > 0.0F) {
            AxisAlignedBB var4 = null;
            if (this.o != null && !this.o.M) {
                var4 = this.E.a(this.o.E).b(1.0D, 0.0D, 1.0D);
            } else {
                var4 = this.E.b(1.0D, 0.5D, 1.0D);
            }

            this.collided_with_gelatinous_cube = false;
            List var5 = this.q.b(this, var4);
            if (var5 != null) {
                for(int var6 = 0; var6 < var5.size(); ++var6) {
                    Entity var7 = (Entity)var5.get(var6);
                    if (!var7.M) {
                        this.r(var7);
                    }
                }
            }
        }

    }

    private void initiateRunegateTeleport(WorldServer world, int x, int y, int z, EntityPlayer player, boolean is_portal_to_world_spawn) {
        player.is_runegate_teleporting = true;
        player.runegate_destination_coords = is_portal_to_world_spawn ? new int[]{world.getSpawnX(), world.getTopSolidOrLiquidBlockMITE(world.getSpawnX(), world.getSpawnZ(), false) + 1, world.getSpawnZ()} : this.getRunegateDestinationCoords(world, x, y, z);
        player.a.b(new Packet85SimpleSignal(EnumSignal.runegate_start));
        player.prevent_runegate_achievement = is_portal_to_world_spawn;
    }

    public int[] getRunegateDestinationCoords(WorldServer world, int x, int y, int z) {
        int seed = this.ab.nextInt();
        BlockRunestone block_runestone = Block.runestoneMithril;
        int chunk_z;
        int runegate_domain_radius;
        if (seed == 0) {
            x = 0;
            z = 0;
        } else {
            Random random = new Random(seed);

            for(chunk_z = 0; chunk_z < 4; ++chunk_z) {
                runegate_domain_radius = world.getRunegateDomainRadius(block_runestone == Block.runestoneAdamantium ? Material.adamantium : Material.mithril);
                x = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius;

                for(z = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius; block_runestone == Block.runestoneAdamantium && WorldServer.getDistanceFromDeltas(x, z) < (double)(runegate_domain_radius / 2); z = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius) {
                    x = random.nextInt(runegate_domain_radius * 2) - runegate_domain_radius;
                }

                if (world.a(x, z) != BiomeBase.b) {
                    break;
                }
            }
        }

        int chunk_x = x >> 4;
        chunk_z = z >> 4;

        for(runegate_domain_radius = -1; runegate_domain_radius <= 1; ++runegate_domain_radius) {
            for(int dz = -1; dz <= 1; ++dz) {
                world.L().d(chunk_x + runegate_domain_radius, chunk_z + dz);
            }
        }

        if (world.isTheNether()) {
            y = 0;

            while(true) {
                ++y;
                if (y >= 254) {
                    break;
                }

                if (world.isAirOrPassableBlock(x, y, z, false)) {
                    ++y;
                    if (world.isAirOrPassableBlock(x, y, z, false) && !world.isAirOrPassableBlock(x, y - 2, z, false) && !world.isLavaBlock(x, y - 2, z) && !world.isLavaBlock(x, y - 1, z)) {
                        return new int[]{x, y - 1, z};
                    }
                }
            }
        } else if (world.isUnderworld()) {
            y = 254;

            while(true) {
                --y;
                if (y <= 0) {
                    break;
                }

                if (BlockPortal.isGoodSpotForPlayerToAppearAt(world, x, y, z)) {
                    return new int[]{x, y, z};
                }
            }
        } else {
            y = 256;

            while(true) {
                --y;
                if (y <= 0) {
                    break;
                }

                if (world.isAirOrPassableBlock(x, y, z, false)) {
                    --y;
                    if (world.isAirOrPassableBlock(x, y, z, false)) {
                        while(y > 0 && world.isAirOrPassableBlock(x, y - 1, z, false)) {
                            --y;
                        }

                        if (y == 0) {
                            y = 64;
                        }

                        return new int[]{x, y, z};
                    }
                }
            }
        }

        if (!world.isAirOrPassableBlock(x, 64, z, true)) {
            world.i(x, 64, z);
        }

        if (!world.isAirOrPassableBlock(x, 65, z, true)) {
            world.i(x, 65, z);
        }

        return new int[]{x, 64, z};
    }

    @Marker
    private void r(Entity par1Entity) {}

    @Override
    @Marker
    public ItemStack n(int i) {
        return null;
    }

    @Override
    @Marker
    public ItemStack getHeldItemStack() {
        return null;
    }

    @Override
    @Marker
    public void setHeldItemStack(ItemStack itemStack) {

    }

    @Override
    @Marker
    public ItemStack[] ae() {
        return new ItemStack[0];
    }

    @Override
    @Marker
    public boolean setWornItem(int i, ItemStack itemStack) {
        return false;
    }

    @Override
    @Marker
    public boolean isWearing(ItemStack itemStack) {
        return false;
    }

    @Override
    @Marker
    public ItemStack[] getWornItems() {
        return new ItemStack[0];
    }

    public float getCurrentPlayerStrVsBlock(int x, int y, int z, boolean apply_held_item) {
        Block block = Block.s[super.q.a(x, y, z)];
        if (block == null) {
            return 0.0F;
        } else {
            float block_hardness = super.q.getBlockHardness(x, y, z);
            if (block_hardness == 0.0F) {
                return 1.0F;
            } else {
                float min_str_vs_block = -3.4028235E38F;
                Item held_item = super.getHeldItem();
                float str_vs_block;
                if (block.isPortable(super.q, this, x, y, z)) {
                    str_vs_block = min_str_vs_block = 4.0F * block_hardness;
                } else {
                    int metadata;
                    if (apply_held_item && held_item != null) {
                        metadata = super.q.h(x, y, z);
                        str_vs_block = held_item.getStrVsBlock(block, metadata,getHeldItemStack(),dyCast(this));
                        if (str_vs_block < 1.0F) {
                            return this.getCurrentPlayerStrVsBlock(x, y, z, false);
                        }

                        int var4 = EnchantmentManager.c(this);
                        if (var4 > 0) {
                            float var6 = (float)(var4 * var4 + 1);
                            str_vs_block += var6;
                        }
                    } else {
                        metadata = super.q.h(x, y, z);
                        if (block.cU.requiresTool(block, metadata)) {
                            str_vs_block = 0.0F;
                        } else {
                            str_vs_block = 1.0F;
                        }
                    }
                }

                if (block == Block.ab) {
                    boolean decrease_strength = true;
                    if (apply_held_item && held_item != null && held_item.isTool() && held_item.getAsTool().isEffectiveAgainstBlock(block, 0)) {
                        decrease_strength = false;
                    }

                    if (decrease_strength) {
                        str_vs_block *= 0.2F;
                    }
                }

                if (super.a(MobEffectList.e)) {
                    str_vs_block *= 1.0F + (float)(super.b(MobEffectList.e).c() + 1) * 0.2F;
                }

                if (super.a(MobEffectList.f)) {
                    str_vs_block *= 1.0F - (float)(super.b(MobEffectList.f).c() + 1) * 0.2F;
                }

                if (super.a(Material.h) && !EnchantmentManager.h(dyCast(this))) {
                    str_vs_block /= 5.0F;
                }

                if (!super.F) {
                    str_vs_block /= 5.0F;
                }

                if (!this.hasFoodEnergy()) {
                    str_vs_block /= 5.0F;
                }

                str_vs_block *= 1.0F + this.getLevelModifier(EnumLevelBonus.HARVESTING);
                return Math.max(str_vs_block, min_str_vs_block);
            }
        }
    }

    @Marker
    public boolean a(Material h) {
        return false;
    }

    @Marker
    private float getLevelModifier(EnumLevelBonus harvesting) {
        return 0;
    }

    @Marker
    private boolean hasFoodEnergy() {
        return false;
    }

    public static int getHealthLimit(int level) {
        return Math.max(Math.min(6 + level / 5 * 2, 40), 6);
    }

    @Override
    public boolean knockBack(Entity attacker, float amount) {
        ItemStack[] itemStacks = this.ae();
        for (int i = 0, itemStacksLength = itemStacks.length; i < itemStacksLength; i++) {
            ItemStack stack = itemStacks[i];
            if (stack!=null) {
                amount *= Math.max(1 - ArmorModifierTypes.STEADY_MODIFIER.getModifierValue(stack.e),0);
            }
        }
        return super.knockBack(attacker, amount);
    }

    private void checkForArmorAchievements() {
        boolean wearing_leather = false;
        boolean wearing_full_suit_plate = true;
        boolean wearing_full_suit_adamantium_plate = true;
        boolean wearing_full_suit_vibranium_plate = true;
        for(int i = 0; i < 4; ++i) {
            if (this.bn.b[i] != null && this.bn.b[i].b() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor)this.bn.b[i].b();
                Material material = armor.getArmorMaterial();
                if (material == Material.leather) {
                    wearing_leather = true;
                }

                if (material != Material.copper && material != Material.silver && material != Material.gold && material != Material.f && material != Material.mithril && material != Material.adamantium && material != Material.ancient_metal) {
                    wearing_full_suit_plate = false;
                }

                if (material != Material.adamantium) {
                    wearing_full_suit_adamantium_plate = false;
                }
                if (material != Materials.vibranium){
                    wearing_full_suit_vibranium_plate = false;
                }
            } else {
                wearing_full_suit_plate = false;
                wearing_full_suit_adamantium_plate = false;
                wearing_full_suit_vibranium_plate = false;
            }
        }

        if (wearing_leather) {
            this.a(AchievementList.wearLeather);
        }

        if (wearing_full_suit_plate) {
            this.a(AchievementList.wearAllPlateArmor);
        }

        if (wearing_full_suit_adamantium_plate) {
            this.a(AchievementList.wearAllAdamantiumPlateArmor);
        }

        if (wearing_full_suit_vibranium_plate){
            this.a(Achievements.wearAllVibraniumPlateArmor);
        }

    }

    public void a(TileEntityBeacon par1TileEntityBeacon) {
    }

    @Marker
    private void a(Statistic statistic) {

    }
}
