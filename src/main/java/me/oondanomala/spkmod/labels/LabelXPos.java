package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelXPos extends Label {
    public LabelXPos() {
        super("X", 2, 20);
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Minecraft.getMinecraft().thePlayer.posX);
    }
}
