package net.xiaoyu233.mitemod.miteite.liar.entity;

import net.minecraft.EntityLivestock;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(EntityLivestock.class)
public class EntityLivestockLiar {
    @Stealing
    protected void setWater(float water){}
    public void sWater(float water){
        this.setWater(water);
    }
    @Stealing
    protected void setFood(float food){}
    public void sFood(float food){
        this.setFood(food);
    }
}
