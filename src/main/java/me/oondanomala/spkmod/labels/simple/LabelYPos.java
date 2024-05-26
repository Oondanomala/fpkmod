package me.oondanomala.spkmod.labels.simple;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelYPos extends Label {
    public LabelYPos() {
        super("Y", 2, 29);
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Minecraft.getMinecraft().thePlayer.posY);
    }
}
