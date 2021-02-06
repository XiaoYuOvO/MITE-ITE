package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

import java.util.List;

import static net.xiaoyu233.fml.util.ReflectHelper.dyCast;

@Transform(EntityLivestock.class)
public abstract class EntityLivestockTrans extends EntityAnimal {
    @Link
    public int last_trampled_x;
    @Link
    public int last_trampled_y;
    @Link
    public int last_trampled_z;
    @Link
    public int manure_countdown;
    @Link
    public int manure_period;
    @Link
    public int production_counter;
    @Link
    public long spooked_until;
    @Link
    private boolean has_been_spooked_by_other_animal;
    private int illnessToDeathCounter;

    @Marker
    public EntityLivestockTrans(World par1World) {
        super(par1World);
    }

    public long getSpooked_until() {
        return spooked_until;
    }

    public void b(NBTTagCompound par1NBTTagCompound) {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.a("food", this.getFood());
        par1NBTTagCompound.a("water", this.getWater());
        par1NBTTagCompound.a("freedom", this.getFreedom());
        par1NBTTagCompound.a("production_counter", this.production_counter);
        par1NBTTagCompound.a("manure_countdown", this.manure_countdown);
        par1NBTTagCompound.a("has_been_spooked_by_other_animal", this.has_been_spooked_by_other_animal);
        par1NBTTagCompound.a("death_counter",this.illnessToDeathCounter);
    }

    @Marker
    private float getFreedom() {
        return 0;
    }

    @Marker
    private float getWater() {
        return 0;
    }

    @Marker
    private float getFood() {
        return 0;
    }

    public void a(NBTTagCompound par1NBTTagCompound) {
        super.a(par1NBTTagCompound);
        this.setFood(par1NBTTagCompound.g("food"));
        this.setWater(par1NBTTagCompound.g("water"));
        this.setFreedom(par1NBTTagCompound.g("freedom"));
        this.production_counter = par1NBTTagCompound.e("production_counter");
        this.manure_countdown = par1NBTTagCompound.e("manure_countdown");
        this.has_been_spooked_by_other_animal = par1NBTTagCompound.n("has_been_spooked_by_other_animal");
        this.illnessToDeathCounter = par1NBTTagCompound.e("death_counter");
    }

    @Marker
    private void setFreedom(float freedom) {

    }

    @Marker
    private void setWater(float water) {

    }

    @Marker
    private void setFood(float food) {

    }

    @Override
    public EntityAgeable a(EntityAgeable var1) {
        return null;
    }

    public void c() {
        super.c();
        if (!this.q.I) {
            if (this.ac % 100 == 0) {
                if (this.ab.nextInt(10) > 0 && this.updateWellness() && !this.g_()) {
                    ++this.production_counter;
                }

                this.produceGoods();
            }

            if (!this.isWell()){
                this.illnessToDeathCounter++;
                if (this.illnessToDeathCounter == MITEITEMod.CONFIG.get(Config.ConfigEntry.ANIMAL_ILL_TO_DEATH_TIME)){
//                    this.attackEntityFrom(new Damage(DamageSource.poison,Integer.MAX_VALUE));
                    this.g(0.0f);
                    this.getWorld().a(this,this.aP(),1,1);
                }
            }else {
                if (this.illnessToDeathCounter > 0){
                    this.illnessToDeathCounter--;
                }
            }

            if (!this.g_()) {
                if (!this.isDesperateForFood() && --this.manure_countdown <= 0) {
                    this.b(Item.manure.cv, 1);
                    this.manure_countdown = this.manure_period / 2 + this.ab.nextInt(this.manure_period);
                }

                if (this.F) {
                    if (this.ac % 1000 == 0) {
                        this.last_trampled_x = this.last_trampled_y = this.last_trampled_z = 0;
                    }

                    int x = MathHelper.c(this.u);
                    int y = MathHelper.c(this.v) - 1;
                    int z = MathHelper.c(this.w);
                    if (x != this.last_trampled_x || y != this.last_trampled_y || z != this.last_trampled_z) {
                        this.last_trampled_x = x;
                        this.last_trampled_y = y;
                        this.last_trampled_z = z;
                        Block block = Block.s[this.q.a(x, y, z)];
                        if (block != null) {
                            block.onTrampledBy(this.q, x, y, z, this);
                        }
                    }
                }
            }

            if (this.has_been_spooked_by_other_animal && this.q.total_time % 4000L == 0L) {
                this.has_been_spooked_by_other_animal = false;
            }

            if ((this.has_decided_to_flee || this.fleeing || this.isSpooked()) && this.getTicksExistedWithOffset() % 20 == 0) {
                List list = this.q.a(EntityLivestock.class, this.E.b(8.0D, 4.0D, 8.0D));

                for (Object value : list) {
                    EntityLivestock livestock = (EntityLivestock) value;
                    if (livestock != dyCast(this) && !livestock.has_decided_to_flee && !livestock.fleeing && !livestock.isSpooked() && !livestock.has_been_spooked_by_other_animal && !livestock.M && livestock.canSeeEntity(this) && livestock.canPathTo(this.getBlockPosX(), this.getFootBlockPosY(), this.getBlockPosZ(), 8)) {
//                        livestock.spooked_until = this.q.I() + 400L + (long)this.q.s.nextInt(400);
                        livestock.has_been_spooked_by_other_animal = true;
                    }
                }
            }
        }

    }

    @Marker
    private boolean isWell() {
        return false;
    }

    @Marker
    private boolean isDesperateForFood() {
        return false;
    }

    protected abstract void produceGoods();

    @Marker
    private boolean updateWellness() {
        return false;
    }
}
