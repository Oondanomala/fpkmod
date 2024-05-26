package me.oondanomala.spkmod.labels.movement;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelSpeedVector extends Label {
    public LabelSpeedVector() {
        super("Speed Vector");
    }

    @Override
    protected String getLabelText() {
        double speedX = Minecraft.getMinecraft().thePlayer.posX - PlayerMovementHandler.prevTickState.posX;
        double speedZ = Minecraft.getMinecraft().thePlayer.posZ - PlayerMovementHandler.prevTickState.posZ;

        return TextUtil.formatDouble(Math.hypot(speedX, speedZ)) +
                SPKMod.config.color1 + "/" + SPKMod.config.color2 +
                TextUtil.formatDouble(Math.toDegrees(-Math.atan2(speedX, speedZ) + 0)) + "°";
    }
}
