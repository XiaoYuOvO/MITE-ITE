package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityEarthElemental.class)
public abstract class EntityEarthElementalTrans extends EntityAnimalWatcher {
   @Shadow
   @Final
   public static int CLAY_HARDENED;
   @Shadow
   @Final
   public static int END_STONE_NORMAL;
   @Shadow
   @Final
   public static int NETHERRACK_NORMAL;
   @Shadow
   @Final
   public static int OBSIDIAN_NORMAL;
   @Shadow
   @Final
   public static int STONE_NORMAL;
   @Final
   private static int WOOD;
   @Shadow
   private int heat;

   public EntityEarthElementalTrans(World world) {
      super(world);
   }

   @Shadow
   private static int getType(Block block, boolean is_magma) {
      return 0;
   }

   @Inject(method = "<clinit>" ,at =  @At("HEAD"))
   private static void injectTypeRegister(CallbackInfo ci){
      WOOD = getType(Block.planks, false);
   }

   public EntityDamageResult attackEntityFrom(Damage damage) {
      if (damage.isFireDamage() && this.isWood()) {
         damage.scaleAmount(2.0F);
      }

      return super.attackEntityFrom(damage);
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.setEntityAttribute(GenericAttributes.followRange, 20.0D);
      this.setEntityAttribute(GenericAttributes.movementSpeed, 0.20000000298023224D);
      this.setEntityAttribute(GenericAttributes.attackDamage, 12.0D);
      if (this.getWorld().isTheNether()){
         this.setEntityAttribute(GenericAttributes.maxHealth, 60.0D);
      }else {
         this.setEntityAttribute(GenericAttributes.maxHealth, 30.0D);
      }
   }

   @Overwrite
   public void convertToMagma() {
      this.heat = 1000;
      if (!this.isWood()) {
         if (this.isClay()) {
            if (this.getType() != this.setType(CLAY_HARDENED)) {
               this.entityFX(EnumEntityFX.smoke);
            }
         } else if (this.getType() != this.setType(this.getType() | 256)) {
            this.entityFX(EnumEntityFX.smoke);
         }
      }

   }

   @Shadow
   private Block getBlock() {
      return null;
   }

   @Shadow
   private int getType() {
      return 0;
   }

   @Shadow
   private boolean isClay() {
      return false;
   }

   @Overwrite
   public boolean isHarmedByFire() {
      return this.isWood();
   }

   @Overwrite
   public boolean isHarmedByLava() {
      return this.isWood();
   }

   @Overwrite
   public boolean isImmuneTo(DamageSource damage_source) {
      if (this.isNormalClay()) {
         return super.isImmuneTo(damage_source);
      } else if (damage_source == DamageSource.fall) {
         return false;
      } else if (damage_source.isMelee() && damage_source.getResponsibleEntity() instanceof EntityIronGolem) {
         return false;
      } else {
         ItemStack item_stack = damage_source.getItemAttackedWith();
         if (item_stack != null && item_stack.getItem() instanceof ItemTool && item_stack.getItemAsTool().isEffectiveAgainstBlock(this.getBlock(), 0)) {
            return false;
         } else if (!this.isWood()) {
            return !damage_source.isExplosion();
         } else {
            return !damage_source.isLavaDamage() && !damage_source.isFireDamage();
         }
      }
   }

   @Shadow
   private boolean isNormalClay() {
      return false;
   }

   @Overwrite
   public boolean isValidBlock(Block block) {
      return block == Block.stone || block == Block.obsidian || block == Block.netherrack || block == Block.whiteStone || block == Block.planks;
   }

   private boolean isWood() {
      return this.getType() == WOOD;
   }

   @Overwrite
   public GroupDataEntity onSpawnWithEgg(GroupDataEntity data) {
      Block blockBelow = this.getBlockBelow();
      blockBelow = blockBelow == Block.stone ? (this.getRNG().nextBoolean() ? blockBelow : Block.planks) : blockBelow;
      this.setTypeForBlock(blockBelow, this.getBlockMaterialAtFeet() == Material.lava || this.isClay() && this.getBlockMaterialAtFeet() == Material.fire);
      return super.onSpawnWithEgg(data);
   }

   @Shadow
   private int setType(int i) {
      return i;
   }

   @Shadow @Final public static int NETHERRACK_MAGMA;

   @Shadow protected abstract void entityInit();

   @Overwrite
   public void setTypeForBlock(Block block, boolean heated) {
      this.setType(block == Block.planks ? WOOD : (block == Block.whiteStone ? END_STONE_NORMAL : (block == Block.netherrack ? NETHERRACK_NORMAL : (block == Block.obsidian ? OBSIDIAN_NORMAL : STONE_NORMAL))));
      if (heated) {
         this.convertToMagma();
      }

   }
}
