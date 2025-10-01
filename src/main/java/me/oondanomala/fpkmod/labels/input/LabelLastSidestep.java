package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;

public class LabelLastSidestep extends TextLabel {
    public LabelLastSidestep() {
        super("Last Sidestep");
    }

    @Override
    protected String getLabelText() {
        int lastSidestep = ParkourHandler.sidestep;
        return lastSidestep == 0 ? "WDWA" : "WAD " + Math.abs(lastSidestep) + "t";
    }
}
