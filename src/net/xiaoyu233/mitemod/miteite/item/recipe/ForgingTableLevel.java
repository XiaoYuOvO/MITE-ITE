package net.xiaoyu233.mitemod.miteite.item.recipe;

public enum ForgingTableLevel {
    IRON(0),
    MITHRIL(1),
    ADAMANTIUM(2),
    VIBRANIUM(3);
    private final int level;

    ForgingTableLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
