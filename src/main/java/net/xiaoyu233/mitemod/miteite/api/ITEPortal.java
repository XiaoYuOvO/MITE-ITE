package net.xiaoyu233.mitemod.miteite.api;

import net.minecraft.BlockRunestone;

public interface ITEPortal {
    void setRuneTypeOverride(BlockRunestone runeTypeOverride);
    void setPortalSeedOverride(int newSeed);
}
