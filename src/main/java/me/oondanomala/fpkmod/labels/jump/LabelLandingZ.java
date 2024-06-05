package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelLandingZ extends Label {
    public LabelLandingZ() {
        super("Last Landing Z");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.lastLandingState.posZ);
    }
}
