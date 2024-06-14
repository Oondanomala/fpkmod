package me.oondanomala.fpkmod.labels.turn;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelLastTurning extends TextLabel {
    public LabelLastTurning() {
        super("Last Turning");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(ParkourHandler.lastTurning);
    }
}
