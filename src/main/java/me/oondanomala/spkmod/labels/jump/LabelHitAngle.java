package me.oondanomala.spkmod.labels.jump;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.util.TextUtil;

public class LabelHitAngle extends Label {
    public LabelHitAngle() {
        super("Hit Angle");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatAngle(PlayerMovementHandler.lastHitPosition.yaw);
    }
}