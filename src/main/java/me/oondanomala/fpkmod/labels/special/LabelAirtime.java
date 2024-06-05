package me.oondanomala.fpkmod.labels.special;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.ParkourHandler;

public class LabelAirtime extends Label {
    public LabelAirtime() {
        super("Airtime");
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(ParkourHandler.airtime);
    }
}
