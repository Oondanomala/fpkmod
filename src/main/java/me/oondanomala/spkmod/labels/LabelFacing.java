package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class LabelFacing extends Label {
    private boolean showAxis;

    public LabelFacing() {
        super("F", 2, 47);
        addCustomConfig("Show Axis", true);
        showAxis = configCategory.get("Show Axis").getBoolean();
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
        if (configCategory.get("Show Axis") != null) {
            showAxis = configCategory.get("Show Axis").getBoolean();
        }
    }

    @Override
    public void saveLabelConfig() {
        super.saveLabelConfig();
        configCategory.get("Show Axis").set(showAxis);
    }
}
