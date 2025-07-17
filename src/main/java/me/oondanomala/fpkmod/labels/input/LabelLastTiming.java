package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;

public class LabelLastTiming extends TextLabel {
    public LabelLastTiming() {
        super("Last Timing");
    }

    @Override
    protected String getLabelText() {
        return ParkourHandler.lastTiming;
    }
}
