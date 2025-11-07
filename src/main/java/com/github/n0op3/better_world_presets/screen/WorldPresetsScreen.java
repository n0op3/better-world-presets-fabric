package com.github.n0op3.better_world_presets.screen;

import com.github.n0op3.better_world_presets.widget.PresetListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

public class WorldPresetsScreen extends Screen {

    private final Screen parent;
    private ThreePartsLayoutWidget layout;

    public WorldPresetsScreen() {
        super(Text.literal("World Presets"));
        this.parent = MinecraftClient.getInstance().currentScreen;
        this.layout = new ThreePartsLayoutWidget(this, 33, 54);
    }

    @Override
    protected void init() {
        this.clearChildren();
        this.layout = new ThreePartsLayoutWidget(this, 33, 54);
        layout.addHeader(new TextWidget(Text.literal("Preset Menu"), MinecraftClient.getInstance().textRenderer));

        layout.addBody(new PresetListWidget(MinecraftClient.getInstance(), layout.getWidth(), layout.getContentHeight(), layout.getY() + layout.getHeaderHeight(), layout.getHeaderHeight()));

        GridWidget grid = layout.addFooter(new GridWidget(0, 0).setRowSpacing(4).setColumnSpacing(8));

        grid.add(ButtonWidget.builder(Text.literal("Create new preset"), button -> {}).build(), 0, 0);
        grid.add(ButtonWidget.builder(Text.literal("Edit preset"), button -> {}).build(), 0, 1);
        grid.add(ButtonWidget.builder(Text.literal("Delete preset"), button -> {}).build(), 1, 0);
        grid.add(ButtonWidget.builder(Text.literal("Load preset"), button -> {}).build(), 1, 1);

        layout.refreshPositions();
        layout.forEachChild(this::addDrawableChild);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}