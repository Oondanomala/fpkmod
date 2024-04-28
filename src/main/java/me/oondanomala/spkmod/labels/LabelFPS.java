package me.oondanomala.spkmod.labels;

import net.minecraft.client.Minecraft;

public class LabelFPS extends Label {
    public LabelFPS() {
        super("FPS");
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(Minecraft.getDebugFPS());
    }
}
