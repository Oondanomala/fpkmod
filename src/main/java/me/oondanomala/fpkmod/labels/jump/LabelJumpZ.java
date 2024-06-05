package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelJumpZ extends Label {
    public LabelJumpZ() {
        super("Jump Z");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.lastJumpState.posZ);
    }
}
