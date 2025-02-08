package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.PlayerTickHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelHitAngle extends TextLabel {
    public LabelHitAngle() {
        super("Hit Angle");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatAngle(PlayerTickHandler.lastHitState.yaw);
    }
}
