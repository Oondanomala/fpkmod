package me.oondanomala.spkmod.labels.movement;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.ParkourHandler;
import me.oondanomala.spkmod.util.TextUtil;

public class LabelSpeedVector extends Label {
    public LabelSpeedVector() {
        super("Speed Vector");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Math.hypot(ParkourHandler.speedX, ParkourHandler.speedZ)) +
                SPKMod.config.color1 + "/" + SPKMod.config.color2 +
                TextUtil.formatDouble(Math.toDegrees(-Math.atan2(ParkourHandler.speedX, ParkourHandler.speedZ) + 0)) + "°";
    }
}
