package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.PlayerTickHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelJumpY extends TextLabel {
    public LabelJumpY() {
        super("Jump Y");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerTickHandler.lastJumpState.posY);
    }
}
