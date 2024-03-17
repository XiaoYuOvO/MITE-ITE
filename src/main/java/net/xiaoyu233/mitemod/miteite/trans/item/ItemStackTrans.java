package net.xiaoyu233.mitemod.miteite.trans.item;

import com.google.common.collect.Multimap;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.*;
import net.xiaoyu233.mitemod.miteite.api.ITEStack;
import net.xiaoyu233.mitemod.miteite.item.ArmorModifierTypes;
import net.xiaoyu233.mitemod.miteite.item.ToolModifierTypes;
import net.xiaoyu233.mitemod.miteite.util.Constant;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nonnull;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackTrans implements ITEStack {
   @Shadow
   public int animationsToGo;
   @Shadow
   public int itemID;
   @Shadow
   public int stackSize;
   @Shadow
   public NBTTagCompound stackTagCompound;
   @Shadow
   private int damage;
   @Shadow
   private boolean is_artifact;
   @Shadow
   private EnumQuality quality;
   @Shadow
   private int subtype;
   private boolean toolNbtFixed;

   public ItemStackTrans(int id, int stack_size, int subtype) {
      this.itemID = id;
      this.stackSize = stack_size;
      this.setItemSubtype(subtype);
   }

   public double getEnhanceFactor() {
      return Constant.ENHANCE_FACTORS[this.getForgingGrade()];
   }

   public int getForgingGrade() {
      return this.stackTagCompound != null ? this.stackTagCompound.getInteger("forging_grade") : 0;
   }

   public int getEmergencyCooldown(){
      return this.stackTagCompound != null && this.stackTagCompound.hasKey("emergencyCooldown") ? this.stackTagCompound.getInteger("emergencyCooldown") : 0;
   }

   public void setEmergencyCooldown(int cooldown){
      if (this.stackTagCompound == null) {
         this.stackTagCompound = new NBTTagCompound();
      }

      this.stackTagCompound.setInteger("emergencyCooldown", cooldown);
   }

   public void setForgingGrade(int grade) {
      if (this.stackTagCompound == null) {
         this.stackTagCompound = new NBTTagCompound();
      }

      this.stackTagCompound.setInteger("forging_grade", grade);
   }

   public void setIsArtifact(boolean is_artifact) {
      this.is_artifact = is_artifact;
   }

   public void fixNBT() {
      if (!this.toolNbtFixed) {
         this.toolNbtFixed = true;
         if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
            this.stackTagCompound.setInteger("tool_level", 0);
            this.stackTagCompound.setInteger("tool_exp", 0);
            this.stackTagCompound.setCompoundTag("modifiers", new NBTTagCompound());
         } else if (!this.stackTagCompound.hasKey("tool_level")) {
            this.stackTagCompound.setInteger("tool_level", 0);
            this.stackTagCompound.setInteger("tool_exp", 0);
            this.stackTagCompound.setCompoundTag("modifiers", new NBTTagCompound());
         }

         if (this.stackTagCompound.hasKey("modifiers")) {
            NBTTagCompound compound = this.stackTagCompound.getCompoundTag("modifiers");
            int var3;
            int var4;
            float origin;
            if (this.getItem() instanceof ItemArmor) {
               if (!compound.hasNoTags()) {
                  ArmorModifierTypes[] var2 = ArmorModifierTypes.values();
                  var3 = var2.length;

                  for(var4 = 0; var4 < var3; ++var4) {
                     ArmorModifierTypes value = var2[var4];
                     if (compound.getTag(value.nbtName) instanceof NBTTagFloat) {
                        origin = compound.getFloat(value.nbtName);
                        compound.setInteger(value.nbtName, (int)(origin / value.levelAddition));
                     }
                  }
               }
            } else if (!compound.hasNoTags()) {
               ToolModifierTypes[] var7 = ToolModifierTypes.values();
               var3 = var7.length;

               for(var4 = 0; var4 < var3; ++var4) {
                  ToolModifierTypes value = var7[var4];
                  if (compound.getTag(value.nbtName) instanceof NBTTagFloat) {
                     origin = compound.getFloat(value.nbtName);
                     compound.setInteger(value.nbtName, (int)(origin / value.levelAddition));
                  }
               }
            }
         }
      }

   }

   @Redirect(method = "getTooltip",at = @At(value = "INVOKE",target = "Lnet/minecraft/Translator;addToList(Lnet/minecraft/EnumChatFormatting;Ljava/lang/String;Ljava/util/List;)V",ordinal = 0))
   private void removeChangeQualityInfo(EnumChatFormatting enum_chat_formatting, String key, List list){
      //Do nothing to remove
      list.remove(list.size() - 1);
   }

   @Redirect(method = "copy", at = @At(value = "NEW", target = "(III)Lnet/minecraft/ItemStack;"))
   public ItemStack copy(int id, int stack_size, int subtype) {
      ItemStack var1 = new ItemStack(this.itemID, this.stackSize, this.subtype);
      var1.setItemDamage(this.damage);
      var1.setQuality(this.getQuality());
      var1.setIsArtifact(this.is_artifact);
      if (this.stackTagCompound != null) {
         var1.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
      }
      return var1;
   }

   @Inject(method = "copy", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
   private void injectFixNBT(CallbackInfoReturnable<ItemStack> cir, @Local(name = "var1", ordinal = 1) ItemStack var1){
      if (this.getItem().hasExpAndLevel()) {
         var1.fixNBT();
      }

   }

   @Redirect(method = "getAttributeModifiers", at = @At(value = "INVOKE", target = "Lnet/minecraft/Item;getItemAttributeModifiers()Lcom/google/common/collect/Multimap;"))
   public Multimap<String, AttributeModifier> getAttributeModifiers(Item instance) {
      return instance.getAttrModifiers(ReflectHelper.dyCast(this));
   }

   @Shadow
   @Nonnull
   public abstract Item getItem();

   @Redirect(method = "getMeleeDamageBonus", at = @At(value = "INVOKE", target = "Lnet/minecraft/Item;getMeleeDamageBonus()F"))
   public float redirectMeleeDamage(Item instance) {
      return instance.getMeleeDamageBonus(ReflectHelper.dyCast(this));
   }

   @Shadow
   @Nonnull
   public abstract EnumQuality getQuality();

   @Inject(method = "<init>(III)V",at = @At("RETURN"))
   private void injectCtorFix(CallbackInfo callback){
      if (this.getItem() != null){
         if (this.getItem().hasExpAndLevel()) {
            this.fixNBT();
         }
      }
   }

   @Inject(method = "setTagCompound",at = @At(value = "FIELD",target = "Lnet/minecraft/ItemStack;stackTagCompound:Lnet/minecraft/NBTTagCompound;",shift = At.Shift.AFTER))
   private void injectSetTagFix(CallbackInfoReturnable<ItemStack> callback){
      if (this.getItem().hasExpAndLevel()) {
         this.fixNBT();
      }
   }

   @Inject(method = "readFromNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/ItemStack;isItemStackDamageable()Z", ordinal = 0, shift = At.Shift.BEFORE))
   public void injectFixNBT(NBTTagCompound par1NBTTagCompound, CallbackInfo ci) {
      if (this.getItem().hasExpAndLevel() && this.stackTagCompound == null) {
         NBTTagCompound tagCompound = new NBTTagCompound();
         tagCompound.setInteger("tool_level", 0);
         tagCompound.setInteger("tool_exp", 0);
         tagCompound.setCompoundTag("modifiers", new NBTTagCompound());
         tagCompound.setInteger("forging_grade", 0);
         tagCompound.setInteger("emergencyCooldown",0);
         this.setTagCompound(tagCompound);
      }
   }

   @Shadow
   public abstract ItemStack setItemSubtype(int subtype);

   @Shadow
   public abstract ItemStack setTagCompound(NBTTagCompound par1NBTTagCompound);

   @Inject(method = "writeToNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/NBTTagCompound;setTag(Ljava/lang/String;Lnet/minecraft/NBTBase;)V", shift = At.Shift.BEFORE))
   private void injectAddDefaultForgingGrade(NBTTagCompound par1NBTTagCompound, CallbackInfoReturnable<NBTTagCompound> cir){
      if (!this.stackTagCompound.hasKey("forging_grade")) {
         this.stackTagCompound.setInteger("forging_grade", 0);
      }
   }
   @Inject(method = "writeToNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/Item;hasQuality()Z" ,shift = At.Shift.BEFORE))
   public void writeToNBT(NBTTagCompound par1NBTTagCompound, CallbackInfoReturnable<NBTTagCompound> cir) {
      if (this.getItem().hasExpAndLevel() && this.stackTagCompound == null) {
         NBTTagCompound effective_stackTagCompound = new NBTTagCompound();
         effective_stackTagCompound.setInteger("tool_level", 0);
         effective_stackTagCompound.setInteger("tool_exp", 0);
         effective_stackTagCompound.setCompoundTag("modifiers", new NBTTagCompound());
         effective_stackTagCompound.setInteger("forging_grade", 0);
         par1NBTTagCompound.setCompoundTag("tag", effective_stackTagCompound);
      }
   }
}
