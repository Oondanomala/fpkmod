package me.oondanomala.fpkmod.labels.misc;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.labels.TextLabel;
import net.minecraft.client.Minecraft;

public class LabelVersion extends TextLabel {
    public LabelVersion() {
        super("Version", 2, 2);
    }

    @Override
    protected String getLabelText() {
        return "FPKMod " + FPKMod.VERSION + "-" + Minecraft.getMinecraft().getVersion();
    }
}
