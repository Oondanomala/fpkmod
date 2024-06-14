package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelJumpAngle extends TextLabel {
    public LabelJumpAngle() {
        super("Jump Angle");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatAngle(PlayerMovementHandler.lastJumpState.yaw);
    }
}
