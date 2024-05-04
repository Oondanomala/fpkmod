package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelYPos extends Label {
    public LabelYPos() {
        super("Y");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Minecraft.getMinecraft().thePlayer.posY);
    }
}
