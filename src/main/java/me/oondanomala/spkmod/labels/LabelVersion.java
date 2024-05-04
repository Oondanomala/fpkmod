package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import net.minecraft.client.Minecraft;

public class LabelVersion extends Label {
    public LabelVersion() {
        super("Version");
    }

    @Override
    protected String getLabelText() {
        return "SPKMod " + SPKMod.VERSION + "-" + Minecraft.getMinecraft().getVersion();
    }
}
