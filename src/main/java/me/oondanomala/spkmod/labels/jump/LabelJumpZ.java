package me.oondanomala.spkmod.labels.jump;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.util.TextUtil;

public class LabelJumpZ extends Label {
    public LabelJumpZ() {
        super("Jump Z");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.lastJumpPosition.posZ);
    }
}
