package me.oondanomala.fpkmod.labels.misc;

import me.oondanomala.fpkmod.labels.Label;
import net.minecraft.client.Minecraft;

public class LabelFPS extends Label {
    public LabelFPS() {
        super("FPS", 2, 11);
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(Minecraft.getDebugFPS());
    }
}
