package me.oondanomala.fpkmod.labels.special;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;

public class LabelGrind extends TextLabel {
    public LabelGrind() {
        super("Grind");
    }

    @Override
    protected String getLabelText() {
        return String.valueOf(ParkourHandler.grinds);
    }
}
