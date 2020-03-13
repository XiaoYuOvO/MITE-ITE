package net.xiaoyu233.mitemod.miteite.liar.util;

import net.minecraft.Skill;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;
import team.unknowndomain.liar.annotation.Stealing;

@Deceive(Skill.class)
public class SkillLiar {
    @Liar
    private int id;

    @Stealing
    static Skill getByLocalizedName(String localized_name, boolean profession_name){
        return null;
    }

    @Stealing
    static String getSkillsString(int ids, boolean profession_names, String delimiter){
        return "";
    }

    public static String getSkillsStr(int ids, boolean profession_names, String delimiter){
        return getSkillsString(ids,profession_names,delimiter);
    }

    public int getID(){
        return this.id;
    }

    public static Skill getByLcName(String localized_name, boolean profession_name){
        return getByLocalizedName(localized_name,profession_name);
    }
}
