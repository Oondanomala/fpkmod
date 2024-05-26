package me.oondanomala.spkmod.labels.turn;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.util.TextUtil;

public class LabelLastTurning extends Label {
    public LabelLastTurning() {
        super("Last Turning");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.lastTurning);
    }
}
