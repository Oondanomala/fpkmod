package me.oondanomala.fpkmod.labels.movement;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelSpeedZ extends TextLabel {
    public LabelSpeedZ() {
        super("Z Speed");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(ParkourHandler.speedZ);
    }
}
