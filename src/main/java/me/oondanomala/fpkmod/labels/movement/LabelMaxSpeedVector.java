package me.oondanomala.fpkmod.labels.movement;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.ParkourHandler;
import me.oondanomala.fpkmod.util.TextUtil;

public class LabelMaxSpeedVector extends TextLabel {
    public LabelMaxSpeedVector() {
        super("Max Speed Vector");
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Math.hypot(ParkourHandler.maxSpeedX, ParkourHandler.maxSpeedZ)) +
                FPKMod.config.color1 + "/" + FPKMod.config.color2 +
                TextUtil.formatDouble(Math.toDegrees(-Math.atan2(ParkourHandler.maxSpeedX, ParkourHandler.maxSpeedZ) + 0)) + "°";
    }
}
