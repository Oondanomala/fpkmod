package me.oondanomala.fpkmod.labels.simple;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelZPos extends Label {
    public LabelZPos() {
        super("Z", 2, 38);
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Minecraft.getMinecraft().thePlayer.posZ);
    }
}
