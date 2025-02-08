package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.PlayerTickHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelJumpX extends TextLabel {
    public LabelJumpX() {
        super("Jump X");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerTickHandler.lastJumpState.posX);
    }
}
