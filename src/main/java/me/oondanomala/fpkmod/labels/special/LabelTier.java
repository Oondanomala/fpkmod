package me.oondanomala.fpkmod.labels.special;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.ParkourHandler;

public class LabelTier extends Label {
    public LabelTier() {
        super("Tier");
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(ParkourHandler.tier);
    }
}
