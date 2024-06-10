package me.oondanomala.fpkmod.labels.special;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.ParkourHandler;

public class LabelGrind extends Label {
    public LabelGrind() {
        super("Grind");
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(ParkourHandler.grinds);
    }
}
