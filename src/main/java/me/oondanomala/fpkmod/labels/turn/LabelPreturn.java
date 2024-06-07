package me.oondanomala.fpkmod.labels.turn;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.ParkourHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelPreturn extends Label {
    public LabelPreturn() {
        super("Preturn");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(ParkourHandler.preturn);
    }
}
