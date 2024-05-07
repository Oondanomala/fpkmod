package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelZPos extends Label {
    public LabelZPos() {
        super("Z");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Minecraft.getMinecraft().thePlayer.posZ);
    }
}
