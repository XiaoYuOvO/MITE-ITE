package net.xiaoyu233.mitemod.miteite.trans.render;

import net.minecraft.Entity;
import net.minecraft.bgl;
import net.minecraft.bgm;
import net.xiaoyu233.mitemod.miteite.entity.EntityAncientDragon;
import net.xiaoyu233.mitemod.miteite.entity.EntityAnnihilationSkeleton;
import net.xiaoyu233.mitemod.miteite.entity.EntityWanderingWitch;
import net.xiaoyu233.mitemod.miteite.render.entity.RenderAncientDragon;
import net.xiaoyu233.mitemod.miteite.render.entity.RenderAnnihilationSkeleton;
import net.xiaoyu233.mitemod.miteite.render.entity.RenderWanderingWitch;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(bgl.class)
public class RenderManagerTrans {
   @Shadow
   private final Map<Class<? extends Entity>, bgm> q = new HashMap<>();

   private RenderManagerTrans() {}

   @Inject(
           method = "<init>",
           at = @At(value = "RETURN"))
   private void injectRegister(CallbackInfo callback){
      this.q.put(EntityAncientDragon.class, new RenderAncientDragon());
      this.q.put(EntityAnnihilationSkeleton.class, new RenderAnnihilationSkeleton());
      this.q.put(EntityWanderingWitch.class, new RenderWanderingWitch());
      for (bgm o : this.q.values()) {
         o.a(ReflectHelper.dyCast(bgl.class, this));
      }
   }
}
