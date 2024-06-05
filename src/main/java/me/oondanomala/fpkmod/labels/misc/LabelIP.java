package me.oondanomala.fpkmod.labels.misc;

import me.oondanomala.fpkmod.labels.Label;
import net.minecraft.client.Minecraft;

public class LabelIP extends Label {
    public LabelIP() {
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
