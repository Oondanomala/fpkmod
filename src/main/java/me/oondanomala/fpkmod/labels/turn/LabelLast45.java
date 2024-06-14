package me.oondanomala.fpkmod.labels.turn;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelLast45 extends TextLabel {
    public LabelLast45() {
        super("Last 45");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(ParkourHandler.last45);
    }
}
