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
                            item.addExpForTool(stack, ((EntityHuman) attacker), (int) Math.min(this.aT(), result.getAmountOfHealthLost()));
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
