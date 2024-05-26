package me.oondanomala.spkmod.labels.simple;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class LabelFacing extends Label {
    private boolean showAxis;

    public LabelFacing() {
        super("F", 2, 47);
        showAxis = addCustomConfig("Show Axis", true);
    }

    @Override
    protected String getLabelText() {
        float facing = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (showAxis) {
            float absFacing = Math.abs(MathHelper.wrapAngleTo180_float(facing));
            return TextUtil.formatAngle(facing) + SPKMod.config.color1 + (absFacing > 45 && absFacing < 135 ? " X" : " Z");
        }
        return TextUtil.formatAngle(facing);
    }

    @Override
    public void loadLabelConfig() {
        super.loadLabelConfig();
        showAxis = getCustomConfig("Show Axis");
    }

    @Override
    public void saveLabelConfig() {
        super.saveLabelConfig();
        setCustomConfig("Show Axis", showAxis);
    }
}
