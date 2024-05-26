package me.oondanomala.spkmod.labels.movement;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.util.TextUtil;

public class LabelSpeedZ extends Label {
    public LabelSpeedZ() {
        super("Z Speed");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.speedZ);
    }
}
