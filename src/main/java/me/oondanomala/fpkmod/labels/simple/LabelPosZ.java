package me.oondanomala.fpkmod.labels.simple;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelPosZ extends TextLabel {
    public LabelPosZ() {
        super("Z", 2, 38);
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Minecraft.getMinecraft().thePlayer.posZ);
    }
}
