package com.github.n0op3.better_world_presets.widget;

import com.github.n0op3.better_world_presets.BetterWorldPreset;
import com.github.n0op3.better_world_presets.config.PresetManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class PresetListWidget extends AlwaysSelectedEntryListWidget<PresetListWidget.Entry> {

    private Consumer<Entry> selectionCallback;

    public PresetListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);
        for (BetterWorldPreset preset : PresetManager.WORLD_PRESETS) {
            addEntry(new Entry(preset));
        }
    }

    public PresetListWidget selectionCallback(Consumer<Entry> selectionCallback) {
        this.selectionCallback = selectionCallback;
        return this;
    }

    @Override
    public void setSelected(@Nullable PresetListWidget.Entry entry) {
        super.setSelected(entry);
        if (this.selectionCallback != null) {
            this.selectionCallback.accept(entry);
        }
    }

    public static class Entry extends AlwaysSelectedEntryListWidget.Entry<Entry> implements Element {

        public final BetterWorldPreset preset;
        private boolean focused;

        public Entry(BetterWorldPreset preset) {
            this.preset = preset;
        }

        @Override
        public boolean isFocused() {
            return focused;
        }

        @Override
        public void setFocused(boolean focused) {
            this.focused = focused;
        }

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            context.drawText(MinecraftClient.getInstance().textRenderer, preset.worldName(), x + 4, y + 4, 0xFFFFFFFF, true);
        }

        @Override
        public Text getNarration() {
            return Text.empty();
        }
    }
}
