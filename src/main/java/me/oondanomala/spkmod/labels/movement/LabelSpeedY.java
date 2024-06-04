package me.oondanomala.spkmod.labels.movement;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.ParkourHandler;
import me.oondanomala.spkmod.util.TextUtil;

public class LabelSpeedY extends Label {
    public LabelSpeedY() {
        super("Y Speed");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(ParkourHandler.speedY);
    }
}
