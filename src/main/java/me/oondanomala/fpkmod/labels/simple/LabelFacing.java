package me.oondanomala.fpkmod.labels.simple;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.util.TextUtil;
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
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (showAxis) {
            float absYaw = Math.abs(MathHelper.wrapAngleTo180_float(yaw));
            return TextUtil.formatAngle(yaw) + FPKMod.config.color1 + (absYaw > 45 && absYaw < 135 ? " X" : " Z");
        }
        return TextUtil.formatAngle(yaw);
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
