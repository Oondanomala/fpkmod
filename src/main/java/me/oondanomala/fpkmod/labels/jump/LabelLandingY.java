package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.PlayerTickHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelLandingY extends TextLabel {
    public LabelLandingY() {
        super("Last Landing Y");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerTickHandler.lastLandingState.posY);
    }
}
