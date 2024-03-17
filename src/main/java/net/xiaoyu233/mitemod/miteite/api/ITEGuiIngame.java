package net.xiaoyu233.mitemod.miteite.api;

public interface ITEGuiIngame {
    default void setOverlayMsg(String var1, int var2, int var3){
        throw new IllegalStateException("Should be implemented in Mixin");
    };
}
