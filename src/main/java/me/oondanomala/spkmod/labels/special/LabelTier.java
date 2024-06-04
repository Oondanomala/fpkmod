package me.oondanomala.spkmod.labels.special;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.ParkourHandler;

public class LabelTier extends Label {
    public LabelTier() {
        super("Tier");
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(ParkourHandler.tier);
    }
}
