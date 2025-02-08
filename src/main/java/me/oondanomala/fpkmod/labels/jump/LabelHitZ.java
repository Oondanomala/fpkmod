package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.PlayerTickHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelHitZ extends TextLabel {
    public LabelHitZ() {
        super("Hit Z");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerTickHandler.lastHitState.posZ);
    }
}
