package me.oondanomala.spkmod.labels.movement;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.util.TextUtil;

public class LabelSpeedVector extends Label {
    public LabelSpeedVector() {
        super("Speed Vector");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Math.hypot(PlayerMovementHandler.speedX, PlayerMovementHandler.speedZ)) +
                SPKMod.config.color1 + "/" + SPKMod.config.color2 +
                TextUtil.formatDouble(Math.toDegrees(-Math.atan2(PlayerMovementHandler.speedX, PlayerMovementHandler.speedZ) + 0)) + "°";
    }
}
