package me.oondanomala.fpkmod.labels.movement;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelSpeedVector extends TextLabel {
    public LabelSpeedVector() {
        super("Speed Vector");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Math.hypot(ParkourHandler.speedX, ParkourHandler.speedZ)) +
                FPKMod.config.color1 + "/" + FPKMod.config.color2 +
                TextUtil.formatDouble(Math.toDegrees(-Math.atan2(ParkourHandler.speedX, ParkourHandler.speedZ) + 0)) + "°";
    }
}
