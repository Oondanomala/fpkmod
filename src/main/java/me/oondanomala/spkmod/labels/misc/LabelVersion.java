package me.oondanomala.spkmod.labels.misc;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.labels.Label;
import net.minecraft.client.Minecraft;

public class LabelVersion extends Label {
    public LabelVersion() {
        super("Version", 2, 2);
    }

    @Override
    protected String getLabelText() {
        return "SPKMod " + SPKMod.VERSION + "-" + Minecraft.getMinecraft().getVersion();
    }
}
