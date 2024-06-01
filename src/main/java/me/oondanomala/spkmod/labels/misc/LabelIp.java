package me.oondanomala.spkmod.labels.misc;

import me.oondanomala.spkmod.labels.Label;
import net.minecraft.client.Minecraft;

public class LabelIp extends Label {
    public LabelIp() {
        super("IP");
    }

    @Override
    protected String getLabelText() {
        if (Minecraft.getMinecraft().getCurrentServerData() == null) {
            // Can this be null in multiplayer?
            return "Singleplayer";
        }
        return Minecraft.getMinecraft().getCurrentServerData().serverIP;
    }
}
