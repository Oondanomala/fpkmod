package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;

public class LabelRunTicks extends TextLabel {
    public LabelRunTicks() {
        super("Run Ticks");
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(ParkourHandler.runTicks);
    }
}
