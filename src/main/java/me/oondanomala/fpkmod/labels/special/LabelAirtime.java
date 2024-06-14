package me.oondanomala.fpkmod.labels.special;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;

public class LabelAirtime extends TextLabel {
    public LabelAirtime() {
        super("Airtime");
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(ParkourHandler.airtime);
    }
}
