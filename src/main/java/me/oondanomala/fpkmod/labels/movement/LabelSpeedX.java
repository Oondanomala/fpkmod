package me.oondanomala.fpkmod.labels.movement;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.ParkourHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelSpeedX extends Label {
    public LabelSpeedX() {
        super("X Speed");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(ParkourHandler.speedX);
    }
}
