package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.PlayerTickHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelLandingX extends TextLabel {
    public LabelLandingX() {
        super("Last Landing X");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerTickHandler.lastLandingState.posX);
    }
}
