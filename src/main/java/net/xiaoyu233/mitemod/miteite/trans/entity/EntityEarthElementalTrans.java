package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityEarthElemental.class)
public abstract class EntityEarthElementalTrans extends EntityAnimalWatcher {
   @Unique
   private static int WOOD;

   public EntityEarthElementalTrans(World world) {
      super(world);
   }

   @Shadow
   public static int getType(Block block, boolean is_magma) {
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
      this.setEntityAttribute(SharedMonsterAttributes.followRange, 20.0D);
      this.setEntityAttribute(SharedMonsterAttributes.movementSpeed, 0.20000000298023224D);
      this.setEntityAttribute(SharedMonsterAttributes.attackDamage, 12.0D);
      if (this.getWorld().isTheNether()){
         this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 60.0D);
      }else {
         this.setEntityAttribute(SharedMonsterAttributes.maxHealth, 30.0D);
      }
   }

   @Inject(method = "convertToMagma", at = @At("HEAD"), cancellable = true)
   public void preventWoodConvert(CallbackInfo ci) {
       if (this.isWood()) {
           ci.cancel();
       }
   }

   @Shadow
   public Block getBlock() {
      return null;
   }

   @Shadow
   public int getType() {
      return 0;
   }

   /**
    * @author XiaoYu233
    * @reason Performance
    */
   @Overwrite
   public boolean isHarmedByFire() {
      return this.isWood();
   }

   /**
    * @author XiaoYu233
    * @reason Performance
    */
   @Overwrite
   public boolean isHarmedByLava() {
      return this.isWood();
   }

   @Inject(method = "isImmuneTo", at = @At("HEAD"), cancellable = true)
   public void modifyWoodImmune(DamageSource damage_source, CallbackInfoReturnable<Boolean> cir) {
      if (this.isWood()) {
         ItemStack item_stack = damage_source.getItemAttackedWith();
         if (item_stack != null && item_stack.getItem() instanceof ItemTool && item_stack.getItemAsTool().isEffectiveAgainstBlock(this.getBlock(), 0)) {
            cir.setReturnValue(false);
         }
         cir.setReturnValue(!damage_source.isLavaDamage() && !damage_source.isFireDamage());
      }
   }

   @Inject(method = "isValidBlock", at = @At("HEAD"), cancellable = true)
   public void injectWoodValid(Block block, CallbackInfoReturnable<Boolean> cir) {
      if (block == Block.planks){
         cir.setReturnValue(true);
      }
   }

   @Unique
   private boolean isWood() {
      return this.getType() == WOOD;
   }

   @Redirect(method = "onSpawnWithEgg", at = @At(value = "INVOKE", target = "Lnet/minecraft/EntityEarthElemental;getBlockBelow()Lnet/minecraft/Block;"))
   public Block injectWoodSpawn(EntityEarthElemental instance) {
      Block blockBelow = this.getBlockBelow();
      return blockBelow == Block.stone ? (this.getRNG().nextBoolean() ? blockBelow : Block.planks) : blockBelow;
   }

   @Shadow
   protected int setType(int i) {
      return i;
   }

   @Shadow protected abstract void entityInit();

   @Inject(method = "setTypeForBlock", at = @At("HEAD"), cancellable = true)
   public void setTypeForBlock(Block block, boolean heated, CallbackInfo ci) {
      if (block == BlockSand.planks){
         this.setType(WOOD);
         ci.cancel();
      }
   }
}
