package me.oondanomala.fpkmod.labels.misc;

import me.oondanomala.fpkmod.labels.TextLabel;
import net.minecraft.client.Minecraft;

public class LabelIP extends TextLabel {
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
