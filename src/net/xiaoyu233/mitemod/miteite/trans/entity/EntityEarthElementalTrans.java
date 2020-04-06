package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import net.xiaoyu233.fml.asm.annotations.Link;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityEarthElemental.class)
public class EntityEarthElementalTrans extends EntityAnimalWatcher {
    @Link
    public static final int STONE_NORMAL;
    @Link
    public static final int STONE_MAGMA;
    @Link
    public static final int OBSIDIAN_NORMAL;
    @Link
    public static final int OBSIDIAN_MAGMA;
    @Link
    public static final int NETHERRACK_NORMAL;
    @Link
    public static final int NETHERRACK_MAGMA;
    @Link
    public static final int END_STONE_NORMAL;
    @Link
    public static final int END_STONE_MAGMA;
    @Link
    public static final int CLAY_NORMAL;
    @Link
    public static final int CLAY_HARDENED;
    @Link
    public static final int WOOD;
    @Link
    private int heat;

    @Marker
    public EntityEarthElementalTrans(World world) {
        super(world);
    }
    static {
        STONE_NORMAL = getType(Block.y, false);
        STONE_MAGMA = getType(Block.y, true);
        OBSIDIAN_NORMAL = getType(Block.au, false);
        OBSIDIAN_MAGMA = getType(Block.au, true);
        NETHERRACK_NORMAL = getType(Block.bg, false);
        NETHERRACK_MAGMA = getType(Block.bg, true);
        END_STONE_NORMAL = getType(Block.bO, false);
        END_STONE_MAGMA = getType(Block.bO, true);
        CLAY_NORMAL = getType(Block.bb, false);
        CLAY_HARDENED = getType(Block.cD, false);
        WOOD = getType(Block.C,false);
    }

    public static int getType(Block block, boolean is_magma) {
        return block.cF + (is_magma ? 256 : 0);
    }

    public boolean isHarmedByLava() {
        return this.isWood();
    }

    public boolean isValidBlock(Block block) {
        return block == Block.y || block == Block.au || block == Block.bg || block == Block.bO || block == Block.C;
    }

    public void setTypeForBlock(Block block, boolean heated) {
        this.setType(block == Block.C ? WOOD : block == Block.bO ? END_STONE_NORMAL : (block == Block.bg ? NETHERRACK_NORMAL : (block == Block.au ? OBSIDIAN_NORMAL : STONE_NORMAL)));
        if (heated) {
            this.convertToMagma();
        }

    }

    public void convertToMagma() {
        this.heat = 1000;
        if (!this.isWood()) {
            if (this.isClay()) {
                if (this.getType() != this.setType(CLAY_HARDENED)) {
                    this.entityFX(EnumEntityFX.smoke);
                }

            } else {
                if (this.getType() != this.setType(this.getType() | 256)) {
                    this.entityFX(EnumEntityFX.smoke);
                }

            }
        }
    }

    @Marker
    private int setType(int i) {
        return i;
    }

    public GroupDataEntity a(GroupDataEntity data) {
        Block blockBelow = this.getBlockBelow();
        blockBelow = blockBelow == Block.y ? (this.aD().nextBoolean() ? blockBelow : Block.C) : blockBelow;
        this.setTypeForBlock(blockBelow, this.getBlockMaterialAtFeet() == Material.i || this.isClay() && this.getBlockMaterialAtFeet() == Material.o);
        return super.a(data);
    }

    @Marker
    private boolean isClay() {
        return false;
    }

    private boolean isWood(){
        return this.getType() == WOOD;
    }

    @Marker
    private int getType() {
        return 0;
    }

    public boolean isHarmedByFire() {
        return isWood();
    }

    @Override
    public EntityDamageResult attackEntityFrom(Damage damage) {
        if (damage.isFireDamage() && this.isWood()){
            damage.scaleAmount(2.0f);
        }
        return super.attackEntityFrom(damage);
    }

    public boolean isImmuneTo(DamageSource damage_source) {
        if (this.isNormalClay()) {
            return super.isImmuneTo(damage_source);
        } else if (damage_source == DamageSource.h) {
            return false;
        } else if (damage_source.isMelee() && damage_source.getResponsibleEntity() instanceof EntityIronGolem) {
            return false;
        } else {
            ItemStack item_stack = damage_source.getItemAttackedWith();
            if (item_stack != null && item_stack.b() instanceof ItemTool && item_stack.getItemAsTool().isEffectiveAgainstBlock(this.getBlock(), 0)) {
                return false;
            } else if (this.isWood()){
                return !(damage_source.isLavaDamage()) && !(damage_source.m());
            }else{
                return !damage_source.c();
            }
        }
    }

    @Marker
    private Block getBlock() {
        return null;
    }

    @Marker
    private boolean isNormalClay() {
        return false;
    }
}
