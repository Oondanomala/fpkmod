package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelJumpY extends Label {
    public LabelJumpY() {
        super("Jump Y");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.lastJumpState.posY);
    }
}
