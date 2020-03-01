package net.xiaoyu233.mitemod.miteite.liar.util;

import net.minecraft.Damage;
import net.minecraft.DebugAttack;
import net.minecraft.Entity;
import net.minecraft.atv;
import net.minecraft.server.MinecraftServer;
import net.xiaoyu233.mitemod.miteite.util.ReflectHelper;
import team.unknowndomain.liar.annotation.Deceive;
import team.unknowndomain.liar.annotation.Liar;

import static net.minecraft.DebugAttack.flush;

@Deceive(DebugAttack.class)
public class DebugAttackLiar {
    @Liar
    private static DebugAttack instance;
    @Liar
    private float damage_dealt_to_armor;
    @Liar
    private float resulting_damage;
    @Liar
    private Entity target;

    public static void start(Entity target, Damage damage) {
        if (target.onClient()) {
            atv.setErrorMessage("DebugAttack.start: called on client?");
        }


        if (instance != null) {
            flush();
        }
        instance = ReflectHelper.createInstance(DebugAttack.class,new Class[]{Entity.class,Damage.class}, target, damage);

    }

    private void flushInstance() {
        if (this.target.onClient()) {
            atv.setErrorMessage("flushInstance: called on client?");
        }

        if (this.damage_dealt_to_armor != 0.0F || this.resulting_damage != 0.0F) {
            MinecraftServer.F().getAuxLogAgent().a(this.toString());
        }

    }
}
