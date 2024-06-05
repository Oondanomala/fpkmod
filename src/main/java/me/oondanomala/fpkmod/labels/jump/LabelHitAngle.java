package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelHitAngle extends Label {
    public LabelHitAngle() {
        super("Hit Angle");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatAngle(PlayerMovementHandler.lastHitState.yaw);
    }
}
