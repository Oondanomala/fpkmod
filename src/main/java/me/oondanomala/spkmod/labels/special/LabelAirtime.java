package me.oondanomala.spkmod.labels.special;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.ParkourHandler;

public class LabelAirtime extends Label {
    public LabelAirtime() {
        super("Airtime");
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(ParkourHandler.airtime);
    }
}
