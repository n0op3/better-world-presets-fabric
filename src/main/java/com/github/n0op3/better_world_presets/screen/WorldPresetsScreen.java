package com.github.n0op3.better_world_presets.screen;

import com.github.n0op3.better_world_presets.BetterWorldPresets;
import com.github.n0op3.better_world_presets.widget.PresetListWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;

public class WorldPresetsScreen extends Screen {

    private final Screen parent;
    private final ThreePartsLayoutWidget layout;

    public WorldPresetsScreen() {
        super(Text.literal("World Presets"));
        this.parent = MinecraftClient.getInstance().currentScreen;
        this.layout = new ThreePartsLayoutWidget(this, 33, 54);
    }

    @Override
    protected void init() {
        layout.addHeader(new TextWidget(Text.literal("Preset Menu"), MinecraftClient.getInstance().textRenderer));

        layout.addBody(new PresetListWidget(MinecraftClient.getInstance(), layout.getWidth(), layout.getContentHeight(), layout.getY() + layout.getHeaderHeight(), layout.getHeaderHeight()));

        DirectionalLayoutWidget layoutOfLayouts = layout.addFooter(DirectionalLayoutWidget.vertical().spacing(8));

        DirectionalLayoutWidget directionalLayout = DirectionalLayoutWidget.horizontal().spacing(8);
        directionalLayout.add(ButtonWidget.builder(Text.literal("Create new preset"), button -> {}).build());
        directionalLayout.add(ButtonWidget.builder(Text.literal("Edit preset"), button -> {}).build());

        directionalLayout.refreshPositions();
        layoutOfLayouts.add(directionalLayout);

        DirectionalLayoutWidget directionalLayout2 = DirectionalLayoutWidget.horizontal().spacing(8);
        directionalLayout2.add(ButtonWidget.builder(Text.literal("Delete preset"), button -> {}).build());
        directionalLayout2.add(ButtonWidget.builder(Text.literal("Load preset"), button -> {}).build());

        directionalLayout2.refreshPositions();
        layoutOfLayouts.add(directionalLayout2);

        layout.refreshPositions();
        layout.forEachChild(this::addDrawableChild);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}