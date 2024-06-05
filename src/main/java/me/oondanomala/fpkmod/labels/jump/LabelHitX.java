package me.oondanomala.fpkmod.labels.jump;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelHitX extends Label {
    public LabelHitX() {
        super("Hit X");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(PlayerMovementHandler.lastHitState.posX);
    }
}
