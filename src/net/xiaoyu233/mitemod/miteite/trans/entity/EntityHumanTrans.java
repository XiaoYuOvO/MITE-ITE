package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.achievement.Achievements;
import net.xiaoyu233.mitemod.miteite.inventory.container.ForgingTableSlots;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.Materials;
import net.xiaoyu233.mitemod.miteite.item.enchantment.Enchantments;
import net.xiaoyu233.mitemod.miteite.network.CPacketSyncItems;
import net.xiaoyu233.mitemod.miteite.network.SPacketCraftingBoost;
import net.xiaoyu233.mitemod.miteite.network.SPacketOverlayMessage;
import net.xiaoyu233.mitemod.miteite.util.BlockPos;
import net.xiaoyu233.mitemod.miteite.util.Config;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraft.EntityHuman.y_offset_on_client_and_eye_height_on_server;
import static net.xiaoyu233.mitemod.miteite.util.Config.ConfigEntry.*;
import static net.xiaoyu233.mitemod.miteite.util.ReflectHelper.dyCast;

@Transform(EntityHuman.class)
public abstract class EntityHumanTrans extends EntityLiving implements ICommandListener {

    @Link
    public PlayerInventory bn = new PlayerInventory(dyCast(this));
    @Link
    private final InventoryEnderChest a = new InventoryEnderChest();
    @Link
    public Container bo;
    @Link
    public Container bp;
    @Link
    protected FoodMetaData bq;
    @Link
    protected int br;
    @Link
    public float bs;
    @Link
    public float bt;
    @Link
    protected final String bu;
    @Link
    public int bv;
    @Link
    public ChunkCoordinates bed_location;
    @Link
    public EnumConsciousState conscious_state;
    @Link
    private ChunkCoordinates c;
    @Link
    private boolean d;
    @Link
    public PlayerAbilities bG;
    @Link
    public int bJ;
    @Link
    protected ItemStack f;
    @Link
    protected int g;
    @Link
    protected float bK;
    @Link
    protected float bL;
    @Link
    public float vision_dimming;
    @Link
    public int countdown_to_mark_all_nearby_chunks_for_render_update;
    @Link
    public boolean collided_with_gelatinous_cube;
    @Link
    List tentative_bounding_boxes;
    @Link
    public HashMap stats;
    @Link
    public long block_placement_tick;
    private int underworldRandomTeleportTime;
    private int underworldDebuffTime;
    private int netherDebuffTime;
    private int inRainCounter;
    private int emergencyCooldown;
    private final Map<Entity,Integer> attackCountMap = new HashMap<>();
    private volatile boolean waitForItemSync;
    private float craftingBoostFactor;
    private int craftingBoostTimer;
    private BlockPos currentEffectedBeaconPos;

    @Marker
    public void sendPacket(Packet packet) {}
    public EntityHumanTrans(World par1World, String par2Str) {
        super(par1World);
        this.conscious_state = EnumConsciousState.fully_awake;
        this.bG = new PlayerAbilities(dyCast(this));
        this.bK = 0.1F;
        this.bL = 0.02F;
        this.countdown_to_mark_all_nearby_chunks_for_render_update = 20;
        this.tentative_bounding_boxes = new ArrayList();
        this.stats = new HashMap();
        this.block_placement_tick = -1L;
        this.bu = par2Str;
        this.bo = new ContainerPlayer(dyCast(this));
        this.bp = this.bo;
        this.N = y_offset_on_client_and_eye_height_on_server;
        ChunkCoordinates var3 = par1World.K();
        this.b((double)var3.a + 0.5D, var3.b + 1, (double)var3.c + 0.5D, 0.0F, 0.0F);
        this.bq = new FoodMetaData(dyCast(this));
    }

    @Override
    @Marker
    public void c(int i, ItemStack itemStack) {

    }

    public float getCraftingBoostFactor() {
        return craftingBoostFactor;
    }

    public void setCraftingBoostFactor(float craftingBoostFactor,@Nullable BlockPos currentEffectedBeaconPos) {
        if (!this.q.I) {
            float result = 0;
            if (currentEffectedBeaconPos != null){
                if (currentEffectedBeaconPos.equals(this.currentEffectedBeaconPos)){
                    result = craftingBoostFactor;
                }else {
                    result= Math.max(craftingBoostFactor, this.craftingBoostFactor);
                    if (result != this.craftingBoostFactor) {
                        this.currentEffectedBeaconPos = currentEffectedBeaconPos;
                    }
                }
            }
            if (result != this.craftingBoostFactor) {
                this.craftingBoostFactor = result;
                this.sendPacket(new SPacketCraftingBoost(this.craftingBoostFactor));
            }
        }else {
            this.craftingBoostFactor = craftingBoostFactor;
        }
    }

    public void setCraftingBoostTimer(int craftingBoostTimer) {
        this.craftingBoostTimer = craftingBoostTimer;
    }

    public void syncItemsAndWait(){
        this.sendPacket(new CPacketSyncItems());
        this.waitForItemSync = true;
        while (this.waitForItemSync){ }
    }

    public void itemsSynced(){
        this.waitForItemSync = false;
    }

    public void displayGUIChestForMinecartEntity(EntityMinecartChest par1IInventory) {

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
        this.underworldDebuffTime = par1NBTTagCompound.e("UnderWorldDebuffTime");
        this.netherDebuffTime = par1NBTTagCompound.e("NetherDebuffTime");
        this.inRainCounter = par1NBTTagCompound.e("InRainCounter");
        this.emergencyCooldown = par1NBTTagCompound.e("EmergencyCooldown");
        this.vision_dimming = par1NBTTagCompound.g("vision_dimming");
        if (par1NBTTagCompound.b("AttackCountMap")){
            NBTTagList attackCountMap = par1NBTTagCompound.m("AttackCountMap");
            int count = attackCountMap.c();
            for (int i = 0; i < count; i++) {
                NBTTagCompound a = ((NBTTagCompound) attackCountMap.b(i));
                Entity attacker = this.getWorldServer().a(a.e("Attacker"));
                if (attacker != null){
                    this.attackCountMap.put(attacker,a.e("Count"));
                }
            }
        }
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
        par1NBTTagCompound.a("UnderWorldDebuffTime",this.underworldDebuffTime);
        par1NBTTagCompound.a("NetherDebuffTime",this.netherDebuffTime);
        par1NBTTagCompound.a("InRainCounter",this.inRainCounter);
        par1NBTTagCompound.a("EmergencyCooldown",this.emergencyCooldown);
        par1NBTTagCompound.a("vision_dimming", this.vision_dimming);
        NBTTagList nbtTagList = new NBTTagList();
        for (Map.Entry<Entity, Integer> entityIntegerEntry : this.attackCountMap.entrySet()) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.a("Attacker",entityIntegerEntry.getKey().k);
            compound.a("Count",entityIntegerEntry.getValue());
            nbtTagList.a(compound);
        }
        par1NBTTagCompound.a("AttackCountMap",nbtTagList);
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

        if (!this.q.I) {
            if (this.craftingBoostTimer > 0) {
                this.craftingBoostTimer--;
            } else if (this.craftingBoostTimer == 0) {
                this.craftingBoostFactor = 0;
                this.sendPacket(new SPacketCraftingBoost(this.craftingBoostTimer));
                this.craftingBoostTimer = -1;
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
            if (this.isInRain()) {
                if (this.inRainCounter < MITEITEMod.CONFIG.get(Config.ConfigEntry.IN_RAIN_DEBUFF_TIME)) {
                    this.inRainCounter++;
                } else {
                    //Weakness
                    this.c(new MobEffect(18, 180 * 20, 0));
                }
            } else if (this.inRainCounter > 0){
                this.inRainCounter--;
            }
            if (this.q.isUnderworld()) {
                if (Config.ConfigEntry.UNDERWORLD_DEBUFF.getFrom(MITEITEMod.CONFIG)){
                    this.underworldDebuffTime++;
                    int period1 = Config.ConfigEntry.UNDERWORLD_DEBUFF_PERIOD1.getFrom(MITEITEMod.CONFIG);
                    int period2 = Config.ConfigEntry.UNDERWORLD_DEBUFF_PERIOD2.getFrom(MITEITEMod.CONFIG);
                    int period3 = Config.ConfigEntry.UNDERWORLD_DEBUFF_PERIOD3.getFrom(MITEITEMod.CONFIG);
                    if (underworldDebuffTime > period1 && underworldDebuffTime < period2){
                        if (underworldDebuffTime == period1+1){
                            this.sendPacket(new SPacketOverlayMessage("§l---你在地底世界中感到有些疲惫---",EnumChatFormat.h.rgb,400));
                        }
                        this.c(new MobEffect(2, 60 * 20, 0));

                    }else if (underworldDebuffTime > period2 && underworldDebuffTime < period3){
                        if (underworldDebuffTime == period2+1){
                            this.sendPacket(new SPacketOverlayMessage("§l---你在地底世界中感到更加疲惫---",EnumChatFormat.o.rgb,400));
                        }
                        this.c(new MobEffect(2, 120 * 20, 1));

                    }else if (underworldDebuffTime > period3){
                        if (underworldDebuffTime == period3 + 1) {
                            this.sendPacket(new SPacketOverlayMessage("§l§n---你在地底世界中感到非常疲惫---",EnumChatFormat.e.rgb,400));
                        }
                        this.c(new MobEffect(2, 180 * 20, 2));
                    }
                }
                if (Config.ConfigEntry.UNDERWORLD_RANDOM_TELEPORT.getFrom(MITEITEMod.CONFIG)) {
                    double randomTeleportTime = Config.ConfigEntry.UNDERWORLD_RANDOM_TELEPORT_TIME.getFrom(MITEITEMod.CONFIG);
                    this.underworldRandomTeleportTime++;
                    double timeToTeleport = randomTeleportTime - this.underworldRandomTeleportTime;
                    if (timeToTeleport == 1200) {
                        this.sendPacket(new SPacketOverlayMessage("§l---你将于一分钟后被随机传送,请做好准备!!!---",EnumChatFormat.o.rgb,400));
                    }
                    if (timeToTeleport == 6000) {
                        this.sendPacket(new SPacketOverlayMessage("§l---你将于五分钟后被随机传送,请做好准备!!!---",EnumChatFormat.d.rgb,400));
                    }
                    if (timeToTeleport <= 200 && this.underworldRandomTeleportTime % 20 == 0) {
                        this.sendPacket(new SPacketOverlayMessage("§l§n!!!你将于" + (int) timeToTeleport / 20 + "秒后被随机传送!!!",EnumChatFormat.m.rgb,200));
                    }
                    if (this.underworldRandomTeleportTime > randomTeleportTime) {
                        if (ReflectHelper.dyCast(EntityHuman.class, this) instanceof EntityPlayer) {
                            this.initiateRunegateTeleport(this.q.getAsWorldServer(), this.getBlockPosX(), this.getBlockPosY(), this.getBlockPosZ(), (ReflectHelper.dyCast(this)));
                        }
                        this.underworldRandomTeleportTime = 0;
                    }
                } else if (this.underworldRandomTeleportTime > 0) {
                    this.underworldRandomTeleportTime--;
                }
            } else {
                if (this.q.isTheNether()){
                    if (MITEITEMod.CONFIG.get(NETHER_DEBUFF)){
                        int debuff_time = MITEITEMod.CONFIG.get(NETHER_DEBUFF_TIME);
                        if (this.netherDebuffTime > debuff_time){
                            this.c(new MobEffect(4, 180 * 20, 1));
                        }else if (this.netherDebuffTime == debuff_time){
                            this.sendPacket(new SPacketOverlayMessage("§n---你在下界中感到疲惫---",EnumChatFormat.e.rgb,200));
                        }
                    }
                }else {
                    if (this.netherDebuffTime > 0){
                        this.netherDebuffTime--;
                    }else if (netherDebuffTime == 1){
                        this.sendPacket(new SPacketOverlayMessage("§n---你已从地狱的疲惫中恢复---",EnumChatFormat.c.rgb,200));
                    }
                }
                if (this.underworldRandomTeleportTime > 0) {
                    this.underworldRandomTeleportTime--;
                }
                if (this.underworldDebuffTime > 0){
                    this.underworldDebuffTime--;
                }else if (this.underworldDebuffTime == 1){
                    this.sendPacket(new SPacketOverlayMessage("§n---你已从地底世界的疲惫中恢复---",EnumChatFormat.c.rgb,200));
                }
            }
            if (this.emergencyCooldown > 0){
                this.emergencyCooldown--;
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
                for (Object value : var5) {
                    Entity var7 = (Entity) value;
                    if (!var7.M) {
                        this.r(var7);
                    }
                }
            }
        }

    }


    public EntityDamageResult attackEntityFrom(Damage damage) {
        if (this.ac < 1000 && Damage.wasCausedByPlayer(damage) && this.isWithinTournamentSafeZone()) {
            return null;
        } else if (this.bG.a && !damage.canHarmInCreative()) {
            return null;
        } else {
            if (this.inBed()) {
                this.wakeUpPlayer(true, damage.getResponsibleEntityP());
            }

            if (damage.isExplosion()) {
                damage.scaleAmount(1.5F);
            }
            double factor = MITEITEMod.CONFIG.get(STEPPED_MOB_DAMAGE_FACTOR);
            if (factor != 0){
                Entity responsibleEntity = damage.getSource().getResponsibleEntity();
                if (responsibleEntity != null){
                    if (this.attackCountMap.containsKey(responsibleEntity)) {
                        this.attackCountMap.put(responsibleEntity, this.attackCountMap.get(responsibleEntity) + 1);
                        damage.scaleAmount(1 + this.attackCountMap.get(responsibleEntity) * (float)factor);
                    }else {
                        this.attackCountMap.put(responsibleEntity, 1);
                    }
                }
            }
            EntityDamageResult entityDamageResult = super.attackEntityFrom(damage);
            if (this.emergencyCooldown <= 0 && (this.getHealthFraction() <= 0.3 ) && !entityDamageResult.entityWasDestroyed()){
                for (ItemStack wornItem : this.getWornItems()) {
                    if (wornItem != null && wornItem.hasEnchantment(Enchantments.EMERGENCY,false)){
                        this.activeEmergency();
                        break;
                    }
                }
            }
            return entityDamageResult;
        }
    }

    private void activeEmergency(){
        this.c(new MobEffect(11,3 * 20,1));
        this.entityFX(EnumEntityFX.smoke_and_steam);
        this.makeSound("fireworks.largeBlast",2,0.75f);
        this.makeSound("random.anvil_land",0.4f,0.4f);
        this.emergencyCooldown = MITEITEMod.CONFIG.get(EMERGENCY_COOLDOWN);
    }

    @Override
    protected void checkForAfterDamage(Damage damage, EntityDamageResult result) {
        if (this.emergencyCooldown <= 0 && (result.entityWasDestroyed())){
            for (ItemStack wornItem : this.getWornItems()) {
                if (wornItem != null && wornItem.hasEnchantment(Enchantments.EMERGENCY,false)){
                    if (result.entityWasDestroyed()){
                        result.setEntity_was_destroyed(false);
                        this.setHealth(this.aT() * 0.2f,true,this.getHealFX());
                    }
                    this.activeEmergency();
                    break;
                }
            }
        }
    }

    public final float getReach(Block block, int metadata) {
        if (this.hasExtendedReach()) {
            return 5.0F;
        } else {
            float block_reach = 2.75F;
            ItemStack item_stack = this.getHeldItemStack();
            int enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.EXTEND, item_stack);
            return item_stack == null ? block_reach : block_reach + item_stack.b().getReachBonus(block, metadata) + enchantmentLevel * 0.25f;
        }
    }

    public float getReach(EnumEntityReachContext context, Entity entity) {
        if (this.hasExtendedReach()) {
            return 5.0F;
        } else {
            float elevation_difference = (float)(this.v - (double)this.N - (entity.v - (double)entity.N));
            float height_advantage;
            if (elevation_difference < -0.5F) {
                height_advantage = (elevation_difference + 0.5F) * 0.5F;
                if (height_advantage < -1.0F) {
                    height_advantage = -1.0F;
                }
            } else if (elevation_difference > 0.5F) {
                height_advantage = (elevation_difference - 0.5F) * 0.5F;
                if (height_advantage > 1.0F) {
                    height_advantage = 1.0F;
                }
            } else {
                height_advantage = 0.0F;
            }

            ItemStack item_stack = this.getHeldItemStack();
            if (context == EnumEntityReachContext.FOR_MELEE_ATTACK) {
                int enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.EXTEND, item_stack);
                return entity.adjustPlayerReachForAttacking(dyCast(this), 1.5F + height_advantage + (item_stack == null ? 0.0F : item_stack.b().getReachBonus())) + enchantmentLevel * 0.25f;
            } else if (context == EnumEntityReachContext.FOR_INTERACTION) {
                int enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.EXTEND, item_stack);
                return entity.adjustPlayerReachForInteraction(dyCast(this), 2.5F + height_advantage + (item_stack == null ? 0.0F : item_stack.b().getReachBonus(entity)) + enchantmentLevel * 0.25f);
            } else {
                Minecraft.setErrorMessage("getReach: invalid context");
                return 0.0F;
            }
        }
    }

    public void displayGUIForgingTable(int x, int y, int z, ForgingTableSlots slots){
    }

    @Marker
    protected abstract boolean hasExtendedReach();

    private void initiateRunegateTeleport(WorldServer world, int x, int y, int z, EntityPlayer player) {
        int[] runegate_destination_coords = this.getRunegateDestinationCoords(world, x, y, z);
        player.runegate_destination_coords = runegate_destination_coords;
        player.a.b(new Packet85SimpleSignal(EnumSignal.runegate_start));
        player.a(runegate_destination_coords[0], runegate_destination_coords[1], (double)runegate_destination_coords[2]);
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

    //AttackEntityAsMob
    public void q(Entity target) {
        if (!this.isImmuneByGrace()) {
            if (target.aq()) {
                boolean critical = this.willDeliverCriticalStrike();
                float critBouns = 0;
                if (EnchantmentManager.hasEnchantment(this.getHeldItemStack(), Enchantments.CRIT)){
                    int critLevel = EnchantmentManager.getEnchantmentLevel(Enchantments.CRIT, this.getHeldItemStack());
                    critical = this.ab.nextInt(10) < MITEITEMod.CONFIG.get(Config.ConfigEntry.CRIT_ENCHANTMENT_CHANCE_BOOST_PER_LVL) * critLevel;
                    if (critical){
                        critBouns = critLevel * MITEITEMod.CONFIG.get(Config.ConfigEntry.CRIT_ENCHANTMENT_DAMAGE_BOOST_PER_LVL).floatValue();
                    }
                }
                float damage = critBouns + this.calcRawMeleeDamageVs(target, critical, this.isSuspendedInLiquid());
                if (damage <= 0.0F) {
                    return;
                }

                int knockback = 0;
                if (target instanceof EntityLiving) {
                    knockback += EnchantmentManager.b(this, (EntityLiving)target);
                }

                if (this.ai()) {
                    ++knockback;
                }

                boolean was_set_on_fire = false;
                int fire_aspect = EnchantmentManager.a(this);
                if (target instanceof EntityLiving && fire_aspect > 0 && !target.af()) {
                    was_set_on_fire = true;
                    target.d(1);
                }

                if (this.onServer() && target instanceof EntityLiving) {
                    EntityLiving entity_living_base = (EntityLiving)target;
                    ItemStack item_stack_to_drop = entity_living_base.getHeldItemStack();
                    if (item_stack_to_drop != null && this.ab.nextFloat() < EnchantmentManager.getEnchantmentLevelFraction(Enchantment.disarming, this.getHeldItemStack()) && entity_living_base instanceof EntityInsentient) {
                        EntityInsentient entity_living = (EntityInsentient)entity_living_base;
                        if (entity_living.canBeDisarmed()){
                            entity_living.dropItemStack(item_stack_to_drop, entity_living.P / 2.0F);
                            entity_living.clearMatchingEquipmentSlot(item_stack_to_drop);
                            entity_living.ticks_disarmed = 40;
                        }
                    }
                }

                EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.a(this).setFireAspectP(fire_aspect > 0), damage));
                boolean target_was_harmed = result != null && result.entityWasNegativelyAffected();
                target.onMeleeAttacked(this, result);
                if (target_was_harmed) {
                    if (target instanceof EntityLiving) {
                        int stunning = EnchantmentManager.getStunModifier(this, (EntityLiving)target);
                        if ((double)stunning > Math.random() * 10.0D) {
                            ((EntityLiving)target).c(new MobEffect(MobEffectList.d.H, stunning * 50, stunning * 5));
                        }

                        this.heal((float)EnchantmentManager.getVampiricTransfer(this, (EntityLiving)target, damage), EnumEntityFX.vampiric_gain);
                    }

                    if (knockback > 0) {
                        target.g(-MathHelper.a(this.A * 3.1415927F / 180.0F) * (float)knockback * 0.5F, 0.1D, MathHelper.b(this.A * 3.1415927F / 180.0F) * (float)knockback * 0.5F);
                        this.x *= 0.6D;
                        this.z *= 0.6D;
                        this.c(false);
                    }

                    if (critical) {
                        this.b(target);
                    }

                    if (target instanceof EntityLiving && EnchantmentWeaponDamage.getDamageModifiers(this.getHeldItemStack(), (EntityLiving)target) > 0.0F) {
                        this.c(target);
                    }

                    if (damage >= 18.0F) {
                        this.a(AchievementList.E);
                    }

                    this.setLastAttackTarget(target);
                    if (target instanceof EntityLiving) {
                        if (this.q.I) {
                            System.out.println("EntityPlayer.attackTargetEntityWithCurrentItem() is calling EnchantmentThorns.func_92096_a() on client");
                            Minecraft.temp_debug = "player";
                        }

                        EnchantmentThorns.a(this, (EntityLiving)target, this.ab);
                    }
                }

                ItemStack held_item_stack = this.getHeldItemStack();
                Object var10 = target;
                if (target instanceof EntityComplexPart) {
                    IComplex var11 = ((EntityComplexPart)target).a;
                    if (var11 != null && var11 instanceof EntityLiving) {
                        var10 = var11;
                    }
                }

                if (target_was_harmed && held_item_stack != null && var10 instanceof EntityLiving) {
                    held_item_stack.a((EntityLiving)var10, dyCast(this));
                }

                if (target instanceof EntityLiving) {
                    this.a(StatisticList.w, Math.round(damage * 10.0F));
                    if (fire_aspect > 0 && target_was_harmed) {
                        target.d(fire_aspect * 4);
                    } else if (was_set_on_fire) {
                        target.B();
                    }
                }

                if (this.onServer()) {
                    this.addHungerServerSide(0.3F * EnchantmentManager.getEnduranceModifier(this));
                }
            }

        }
    }

    @Marker
    public void b(Entity par1Entity) {
    }

    @Marker
    public void a(Statistic par1StatBase, int par2) {
    }

    @Marker
    public void c(Entity par1Entity) {
    }

    @Marker
    public void addHungerServerSide(float hunger){}

    @Marker
    protected abstract float calcRawMeleeDamageVs(Entity target, boolean critical, boolean suspended_in_liquid);

    public boolean willDeliverCriticalStrike() {
        return this.T > 0.0F && !this.F && !this.e() && !this.H() && !this.a(MobEffectList.q) && this.o == null;
    }

    @Marker
    protected abstract boolean isImmuneByGrace();

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
