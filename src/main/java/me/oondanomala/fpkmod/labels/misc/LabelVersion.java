package me.oondanomala.fpkmod.labels.misc;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.labels.Label;
import net.minecraft.client.Minecraft;

public class LabelVersion extends Label {
    public LabelVersion() {
        super("Version", 2, 2);
    }

    @Override
    protected String getLabelText() {
        return "FPKMod " + FPKMod.VERSION + "-" + Minecraft.getMinecraft().getVersion();
    }
}
