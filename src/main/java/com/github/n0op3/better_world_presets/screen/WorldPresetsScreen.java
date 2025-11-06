package com.github.n0op3.better_world_presets.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class WorldPresetsScreen extends Screen {
    private final Screen parent;

    public WorldPresetsScreen() {
        super(Text.literal("World Presets"));
        this.parent = MinecraftClient.getInstance().currentScreen;
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}