package net.xiaoyu233.mitemod.miteite.config;

import net.xiaoyu233.fml.FishModLoader;
import net.xiaoyu233.fml.config.ConfigRegistry;
import net.xiaoyu233.fml.config.editor.ConfigEditor;
import net.xiaoyu233.mitemod.miteite.MITEITEMod;
import net.xiaoyu233.mitemod.miteite.util.Configs;

import java.io.File;
import java.util.Collections;

public class EditorLauncher {
    public static void main(String[] args) {
        FishModLoader.isServer();
        new ConfigEditor(Collections.singletonList(new ConfigRegistry(Configs.ROOT, Configs.CONFIG_FILE))).setVisible(true);
    }
}
