package me.oondanomala.spkmod.labels.simple;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.util.TextUtil;
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
