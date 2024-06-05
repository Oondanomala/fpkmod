package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelLandingY extends Label {
    public LabelLandingY() {
        super("Last Landing Y");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.lastLandingState.posY);
    }
}
