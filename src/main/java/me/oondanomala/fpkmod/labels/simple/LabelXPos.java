package me.oondanomala.fpkmod.labels.simple;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelXPos extends TextLabel {
    public LabelXPos() {
        super("X", 2, 20);
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Minecraft.getMinecraft().thePlayer.posX);
    }
}
