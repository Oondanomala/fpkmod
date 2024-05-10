package me.oondanomala.spkmod.labels.jump;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.util.TextUtil;

public class LabelHitY extends Label {
    public LabelHitY() {
        super("Hit Y");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.lastHitPosition.posY);
    }
}
