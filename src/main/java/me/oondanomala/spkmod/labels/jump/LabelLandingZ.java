package me.oondanomala.spkmod.labels.jump;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.util.TextUtil;

public class LabelLandingZ extends Label {
    public LabelLandingZ() {
        super("Last Landing Z");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.lastLandingPosition.posZ);
    }
}
