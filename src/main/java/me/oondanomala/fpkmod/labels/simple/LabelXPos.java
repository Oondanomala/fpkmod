package me.oondanomala.fpkmod.labels.simple;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.util.TextUtil;
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
