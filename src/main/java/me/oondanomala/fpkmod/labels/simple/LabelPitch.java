package me.oondanomala.fpkmod.labels.simple;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelPitch extends Label {
    public LabelPitch() {
        super("Pitch");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatAngle(Minecraft.getMinecraft().thePlayer.rotationPitch);
    }
}
