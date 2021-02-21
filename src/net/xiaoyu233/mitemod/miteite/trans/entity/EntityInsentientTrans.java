package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;

import java.util.Iterator;
import java.util.List;

import static net.minecraft.EntityInsentient.getEquipmentPosition;

@Transform(EntityInsentient.class)
public class EntityInsentientTrans extends EntityLiving{
    @Link
    public boolean M;
    @Link
    public float[] e;
    @Link
    public boolean[] picked_up_a_held_item_array;
    @Link
    public boolean picked_up_a_held_item;
    @Link
    protected boolean came_from_spawner;
    @Link
    protected boolean came_from_spawn_block;
    @Link
    private boolean bs;
    @Link
    private boolean bt;
    @Link
    protected float f;
    @Link
    private Entity bu;
    @Link
    protected int g;
    @Link
    private boolean bv;
    @Link
    private Entity bw;
    @Link
    private NBTTagCompound bx;
    @Link
    public int food_or_repair_item_pickup_cooldown;
    @Link
    protected long spooked_until;
    @Link
    private boolean is_decoy;
    @Link
    private int spawn_block_x;
    @Link
    private int spawn_block_y;
    @Link
    private int spawn_block_z;
    @Link
    public int ticks_disarmed;
    @Link
    private String target_unique_id_string;
    @Link
    public EntityLiving AI_retarget;
    @Link
    public int increased_chance_of_spreading_fire_countdown;
    @Link
    public EntityItem target_entity_item;
    @Link
    public long last_tick_harmed_by_cactus;
    @Link
    private ItemStack[] br;

    @Marker
    public EntityInsentientTrans(World par1World) {
        super(par1World);
    }

    @Override
    public void onMeleeAttacked(EntityLiving attacker, EntityDamageResult result) {
        if (result!=null && attacker !=null) {
            if (result.entityLostHealth()) {
                ItemStack stack = attacker.getHeldItemStack();
                if (stack!= null) {
                    Item item = stack.b();
                    if (attacker instanceof EntityPlayer) {
                        if (item instanceof ItemSword || item instanceof ItemWarHammer || item instanceof ItemBattleAxe) {
                            if (!item.isMaxToolLevel(stack)){
                                item.addExpForTool(stack, ((EntityHuman) attacker), (int) Math.min(this.aT(), result.getAmountOfHealthLost()));
                            }
                            float slowMdfLvl = ToolModifierTypes.SLOWDOWN_MODIFIER.getModifierValue(stack.q());
                            if (result.entity instanceof EntityLiving && slowMdfLvl >= 1){
                                ((EntityLiving) result.entity).c(new MobEffect(2,(int) (slowMdfLvl * 20),(int) (slowMdfLvl / 2)));
                            }
                        }
                    }
                }
            }
        }
        super.onMeleeAttacked(attacker, result);
    }

    public boolean canBeDisarmed(){
        return true;
    }

    public boolean isCame_from_spawner() {
        return came_from_spawner;
    }

    public void tryPickUpItems() {
        if (!this.getWorld().I && this.bD() && !getM() && this.aN() > 0.0F && this.getWorld().O().b("mobGriefing") && this.getTicksExistedWithOffset() % 10 == 0) {
            List entity_items = this.getItemsWithinPickupDistance();
            Iterator iterator = entity_items.iterator();

            while(iterator.hasNext()) {
                EntityItem entity_item = (EntityItem)iterator.next();
                if (!entity_item.M && entity_item.d() != null) {
                    ItemStack item_stack_on_ground = entity_item.d();
                    Item item_on_ground = item_stack_on_ground.b();
                    if (!this.canNeverPickUpItem(item_on_ground) && (item_on_ground instanceof ItemTool || this.canWearItem(item_stack_on_ground) || this.getHeldItem() instanceof ItemBow && item_on_ground instanceof ItemBow)) {
                        int var5 = getEquipmentPosition(item_stack_on_ground);
                        if (var5 > -1) {
                            boolean pickup = true;
                            ItemStack current_item_stack = this.n(var5);
                            if (current_item_stack != null) {
                                Item current_item = current_item_stack.b();
                                if (var5 == 0) {
                                    if (current_item instanceof ItemBow) {
                                        if (!(item_on_ground instanceof ItemBow)) {
                                            pickup = false;
                                        } else if (current_item_stack.y()) {
                                            pickup = false;
                                        } else if (item_stack_on_ground.y()) {
                                            pickup = true;
                                        } else {
                                            pickup = item_stack_on_ground.k() < current_item_stack.k();
                                        }
                                    } else if (item_on_ground.isTool() && !current_item.isTool()) {
                                        pickup = true;
                                    } else if (item_on_ground.isTool() && current_item.isTool()) {
                                        ItemTool tool_on_ground = (ItemTool)item_on_ground;
                                        ItemTool current_tool = (ItemTool)current_item;
                                        if (tool_on_ground.getCombinedDamageVsEntity() == current_tool.getCombinedDamageVsEntity()) {
                                            if (current_item_stack.y()) {
                                                pickup = false;
                                            } else if (item_stack_on_ground.y() && !current_item_stack.y()) {
                                                pickup = true;
                                            } else {
                                                pickup = item_stack_on_ground.k() < current_item_stack.k() || item_stack_on_ground.p() && !current_item_stack.p();
                                            }
                                        } else {
                                            pickup = tool_on_ground.getCombinedDamageVsEntity() > current_tool.getCombinedDamageVsEntity();
                                        }
                                    } else {
                                        pickup = false;
                                    }
                                } else if (item_on_ground instanceof ItemArmor && !(current_item instanceof ItemArmor)) {
                                    pickup = true;
                                } else if (item_on_ground instanceof ItemArmor && current_item instanceof ItemArmor) {
                                    ItemArmor armor_on_ground = (ItemArmor)item_on_ground;
                                    ItemArmor current_armor = (ItemArmor)current_item;
                                    if (armor_on_ground.getMultipliedProtection(item_stack_on_ground) == current_armor.getMultipliedProtection(current_item_stack)) {
                                        if (item_stack_on_ground.y() && !current_item_stack.y()) {
                                            pickup = true;
                                        } else {
                                            pickup = item_stack_on_ground.k() < current_item_stack.k() || item_stack_on_ground.p() && !current_item_stack.p();
                                        }
                                    } else {
                                        pickup = armor_on_ground.getMultipliedProtection(item_stack_on_ground) > current_armor.getMultipliedProtection(current_item_stack);
                                    }
                                } else {
                                    pickup = false;
                                }
                            }

                            if (pickup) {
                                this.a("random.pop", 0.2F, ((this.aD().nextFloat() - this.aD().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                                if (current_item_stack != null && this.aD().nextFloat() < this.getE()[var5]) {
                                    this.dropItemStack(current_item_stack, 0.0F);
                                }

                                boolean set_dead;
                                if (item_stack_on_ground.b == 1) {
                                    this.c(var5, item_stack_on_ground);
                                    set_dead = true;
                                } else {
                                    this.c(var5, item_stack_on_ground.m().setStackSize(1));
                                    --item_stack_on_ground.b;
                                    set_dead = false;
                                }

                                getE()[var5] = 2.0F;
                                this.picked_up_a_held_item = true;
                                picked_up_a_held_item_array[var5] = true;
                                if (set_dead) {
                                    this.a(entity_item, 1);
                                    entity_item.x();
                                }

                                break;
                            }
                        }
                    }
                }
            }

        }
    }

//    @Override
//    //Read
//    public void a(NBTTagCompound par1NBTTagCompound) {
//        super.a(par1NBTTagCompound);

//    }
//
//    @Override
//    //Write
//    public void b(NBTTagCompound par1NBTTagCompound) {
//        super.b(par1NBTTagCompound);

//    }

    @Marker
    public String bA() {
        return "";
    }

    @Marker
    public boolean bC() {
        return false;
    }

    public void b(NBTTagCompound par1NBTTagCompound) {
        super.b(par1NBTTagCompound);
        byte[] picked_up_held_items = new byte[this.picked_up_a_held_item_array.length];
        boolean[] pickedUpAHeldItemArray = this.picked_up_a_held_item_array;
        for (int i = 0; i < pickedUpAHeldItemArray.length; i++) {
            if (pickedUpAHeldItemArray[i]) {
                picked_up_held_items[i] = 1;
            }
        }
        par1NBTTagCompound.a("picked_up_held_items",picked_up_held_items);
        par1NBTTagCompound.a("CanPickUpLoot", this.bD());
        par1NBTTagCompound.a("PersistenceRequired", this.bt);
        NBTTagCompound var4;
        NBTTagList var2;
        int var3;
        if (this.hasEquipment()) {
            var2 = new NBTTagList();

            for(var3 = 0; var3 < this.br.length; ++var3) {
                var4 = new NBTTagCompound();
                if (this.br[var3] != null) {
                    this.br[var3].b(var4);
                }

                var2.a(var4);
            }

            par1NBTTagCompound.a("Equipment", var2);
        }

        var2 = new NBTTagList();

        for(var3 = 0; var3 < this.e.length; ++var3) {
            var2.a(new NBTTagFloat(var3 + "", this.e[var3]));
        }

        par1NBTTagCompound.a("DropChances", var2);
        par1NBTTagCompound.a("CustomName", this.bA());
        par1NBTTagCompound.a("CustomNameVisible", this.bC());
        par1NBTTagCompound.a("Leashed", this.bv);
        if (this.bw != null) {
            var4 = new NBTTagCompound("Leash");
            if (this.bw instanceof EntityLiving) {
                var4.a("UUIDMost", this.bw.aw().getMostSignificantBits());
                var4.a("UUIDLeast", this.bw.aw().getLeastSignificantBits());
            } else if (this.bw instanceof EntityHanging) {
                EntityHanging var5 = (EntityHanging)this.bw;
                var4.a("X", var5.b);
                var4.a("Y", var5.c);
                var4.a("Z", var5.d);
            }

            par1NBTTagCompound.a("Leash", var4);
        }

        par1NBTTagCompound.a("picked_up_a_held_item", this.picked_up_a_held_item);
        par1NBTTagCompound.a("spooked_until", this.spooked_until);
        if (this.is_decoy) {
            par1NBTTagCompound.a("is_decoy", this.is_decoy);
        }

        if (this.came_from_spawner) {
            par1NBTTagCompound.a("came_from_spawner", true);
        }

        if (this.came_from_spawn_block) {
            par1NBTTagCompound.a("came_from_spawn_block", true);
            par1NBTTagCompound.a("spawn_block_x", this.spawn_block_x);
            par1NBTTagCompound.a("spawn_block_y", this.spawn_block_y);
            par1NBTTagCompound.a("spawn_block_z", this.spawn_block_z);
        }

        if (this.ticks_disarmed > 0) {
            par1NBTTagCompound.a("ticks_disarmed", this.ticks_disarmed);
        }

        EntityLiving target = this.getTarget();
        if (target != null) {
            par1NBTTagCompound.a("target_unique_id_string", target.aw().toString());
        }

    }

    @Marker
    public final EntityLiving getTarget(){
        return null;
    }

    @Marker
    public boolean hasEquipment() {
        return false;
    }

    @Marker
    public void a(String par1Str) {}

    @Marker
    public void g(boolean par1) {}

    @Marker
    public void h(boolean par1) { }

    public void a(NBTTagCompound par1NBTTagCompound) {
        super.a(par1NBTTagCompound);
        if (par1NBTTagCompound.b("picked_up_held_items")){
            if (this.picked_up_a_held_item_array == null){
                this.picked_up_a_held_item_array = new boolean[5];
            }
            byte[] picked_up_held_items = par1NBTTagCompound.j("picked_up_held_items");
            for (int i = 0, jLength = picked_up_held_items.length; i < jLength; i++) {
                if (picked_up_held_items[i] == 1) {
                    this.picked_up_a_held_item_array[i] = true;
                }
            }
        }
        this.h(par1NBTTagCompound.n("CanPickUpLoot"));
        this.bt = par1NBTTagCompound.n("PersistenceRequired");
        if (par1NBTTagCompound.b("CustomName") && par1NBTTagCompound.i("CustomName").length() > 0) {
            this.a(par1NBTTagCompound.i("CustomName"));
        }

        this.g(par1NBTTagCompound.n("CustomNameVisible"));
        NBTTagList var2;
        int var3;
        if (par1NBTTagCompound.b("Equipment")) {
            var2 = par1NBTTagCompound.m("Equipment");

            for(var3 = 0; var3 < this.br.length; ++var3) {
                this.br[var3] = ItemStack.a((NBTTagCompound)var2.b(var3));
            }
        }

        if (par1NBTTagCompound.b("DropChances")) {
            var2 = par1NBTTagCompound.m("DropChances");

            for(var3 = 0; var3 < var2.c(); ++var3) {
                this.e[var3] = ((NBTTagFloat)var2.b(var3)).a;
            }
        }

        this.bv = par1NBTTagCompound.n("Leashed");
        if (this.bv && par1NBTTagCompound.b("Leash")) {
            this.bx = par1NBTTagCompound.l("Leash");
        }

        if (par1NBTTagCompound.b("picked_up_a_held_item")) {
            this.picked_up_a_held_item = par1NBTTagCompound.n("picked_up_a_held_item");
        }

        if (par1NBTTagCompound.b("spooked_until")) {
            this.spooked_until = par1NBTTagCompound.f("spooked_until");
        }

        this.is_decoy = par1NBTTagCompound.n("is_decoy");
        if (par1NBTTagCompound.b("came_from_spawner")) {
            this.came_from_spawner = true;
        }

        if (par1NBTTagCompound.b("came_from_spawn_block")) {
            this.came_from_spawn_block = true;
            this.spawn_block_x = par1NBTTagCompound.e("spawn_block_x");
            this.spawn_block_y = par1NBTTagCompound.e("spawn_block_y");
            this.spawn_block_z = par1NBTTagCompound.e("spawn_block_z");
        }

        if (par1NBTTagCompound.b("ticks_disarmed")) {
            this.ticks_disarmed = par1NBTTagCompound.e("ticks_disarmed");
        }

        if (par1NBTTagCompound.b("target_unique_id_string")) {
            this.target_unique_id_string = par1NBTTagCompound.i("target_unique_id_string");
            if (this.target_unique_id_string.isEmpty()) {
                this.target_unique_id_string = null;
            }
        }

    }

    @Marker
    private boolean bD() {
        return false;
    }

    @Marker
    public boolean setWornItem(int i, ItemStack itemStack) {
        return false;
    }

    @Marker
    public boolean isWearing(ItemStack itemStack) {
        return false;
    }

    @Marker
    public List getItemsWithinPickupDistance() {
        return null;
    }

    @Marker
    public Item getHeldItem() {
        return null;
    }

    @Marker
    public ItemStack getHeldItemStack() {
        return null;
    }

    @Marker
    public void setHeldItemStack(ItemStack itemStack) {

    }

    @Marker
    public ItemStack[] ae() {
        return new ItemStack[0];
    }

    @Marker
    public boolean canWearItem(ItemStack item_stack_on_ground) {
        return false;
    }

    @Marker
    public ItemStack n(int var5) {
        return null;
    }

    @Marker
    public boolean canNeverPickUpItem(Item item_on_ground) {
        return false;
    }

    @Marker
    public void a(String s, float v, float v1) {

    }


    @Marker
    public ItemStack[] getWornItems() {
        return new ItemStack[0];
    }

    @Marker
    public void c(int var5, ItemStack setStackSize) {

    }

    @Marker
    public void a(Entity entity_item, int i) {

    }

    @Override
    protected void az() {
        super.az();
        this.picked_up_a_held_item_array = new boolean[5];
        this.setEntityAttribute(GenericAttributes.b, 16.0D);
    }

    public boolean getM() {
        return this.M;
    }

    public float[] getE() {
        return this.e;
    }
}
