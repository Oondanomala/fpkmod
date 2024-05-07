package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class LabelFacing extends Label {
    public LabelFacing() {
        super("F");
    }

    @Override
    protected String getLabelText() {
        float facing = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float absFacing = Math.abs(MathHelper.wrapAngleTo180_float(facing));

        return TextUtil.formatAngle(facing) + SPKMod.config.color1 + (absFacing > 45 && absFacing < 135 ? " X" : " Z");
    }
}
