package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Config;

@Transform(EntityIronGolem.class)
public class EntityIronGolemTrans extends EntityGolem {
    public int DATA_OBJ_IS_BOOSTED;
    @Link
    private int br;

    @Marker
    public EntityIronGolemTrans(World par1World) {
        super(par1World);
    }

    @Override
    public boolean onEntityRightClicked(EntityHuman player, ItemStack item_stack) {
        if (MITEITEMod.CONFIG.get(Config.ConfigEntry.CAN_BOOST_IRON_GOLEM)) {
            if (!this.isBoosted() && item_stack != null && item_stack.isBlock() && item_stack.getItemAsBlock().getBlock() == Block.blockMithril) {
                this.a(1.9F, 3.5F);
                this.setEntityAttribute(GenericAttributes.a, this.getEntityAttributeValue(GenericAttributes.a) + 20);
                this.setEntityAttribute(GenericAttributes.e, this.getEntityAttributeValue(GenericAttributes.e) + 3);
                this.g((float) this.getEntityAttributeValue(GenericAttributes.a));
                this.ah.b(DATA_OBJ_IS_BOOSTED, (byte) 1);
                if (!this.getWorld().I) {
                    player.convertOneOfHeldItem(null);
                }
                return true;
            }
        }
        return super.onEntityRightClicked(player, item_stack);
    }

    protected void a() {
        super.a();
        this.ah.addObject(16, (byte)0);
        this.DATA_OBJ_IS_BOOSTED = this.ah.addObject(this.ah.getNextAvailableId(), (byte)0);

    }

    public EntityDamageResult attackEntityAsMob(Entity target) {
        this.br = 10;
        this.q.setEntityState(this, EnumEntityState.golem_throw);
        EntityDamageResult result = target.attackEntityFrom(new Damage(DamageSource.a(this), (float) ((float)(7 + this.ab.nextInt(15)) + this.getEntityAttributeValue(GenericAttributes.e))));
        if (result != null) {
            if (result.entityWasKnockedBack()) {
                target.y += 0.4000000059604645D;
            }

            this.a("mob.irongolem.throw", 1.0F, 1.0F);
        }
        return result;
    }

    private boolean isBoosted() {
        return this.ah.a(DATA_OBJ_IS_BOOSTED) != 0;
    }

    public void b(NBTTagCompound par1NBTTagCompound) {
        super.b(par1NBTTagCompound);
        par1NBTTagCompound.a("Boosted", this.isBoosted());
        par1NBTTagCompound.a("PlayerCreated", this.bW());
    }

    public void a(NBTTagCompound par1NBTTagCompound) {
        super.a(par1NBTTagCompound);
        if (par1NBTTagCompound.b("Boosted")){
            this.ah.b(DATA_OBJ_IS_BOOSTED,(byte)(par1NBTTagCompound.n("Boosted") ? 1 : 0));
        }
        this.i(par1NBTTagCompound.n("PlayerCreated"));
        if (this.isBoosted()){
            this.a(1.9F, 3F);
        }
    }

    @Marker
    private boolean bW() {
        return false;
    }

    @Marker
    private void i(boolean playerCreated) {

    }

    @Override
    protected void az() {
        super.az();
        this.a(GenericAttributes.a).a(100.0D);
        this.a(GenericAttributes.d).a(0.25D);
        this.setEntityAttribute(GenericAttributes.e,0);
    }
}
