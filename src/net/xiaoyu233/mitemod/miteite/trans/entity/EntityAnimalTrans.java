package net.xiaoyu233.mitemod.miteite.trans.entity;

import net.minecraft.EntityAgeable;
import net.minecraft.EntityAnimal;
import net.minecraft.World;
import net.xiaoyu233.fml.asm.annotations.Marker;
import net.xiaoyu233.fml.asm.annotations.Transform;

@Transform(EntityAnimal.class)
public class EntityAnimalTrans extends EntityAgeable {
    @Marker
    public EntityAnimalTrans(World par1World) {
        super(par1World);
    }

    @Marker
    @Override
    public EntityAgeable a(EntityAgeable var1) {
        return null;
    }

    public int getBreedExp(){
        return this.ab.nextInt(7) + 1;
    }
}
