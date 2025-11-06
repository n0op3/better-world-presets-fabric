package com.github.n0op3.better_world_presets.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.Text;

public class PresetListWidget extends AlwaysSelectedEntryListWidget<PresetListWidget.Entry> {

    public PresetListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);
        addEntry(new Entry("Test entry"));
    }

    public static class Entry extends AlwaysSelectedEntryListWidget.Entry<Entry> implements Element {

        private boolean focused;
        private final String name;

        public Entry(String name) {
            this.name = name;
        }

        @Override
        public void setFocused(boolean focused) {
            this.focused = focused;
        }

        @Override
        public boolean isFocused() {
            return focused;
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
            context.drawText(MinecraftClient.getInstance().textRenderer, name, getX(), getY(), 0xFFFFFF, true);
        }

        @Override
        public Text getNarration() {
            return null;
        }
    }
}
