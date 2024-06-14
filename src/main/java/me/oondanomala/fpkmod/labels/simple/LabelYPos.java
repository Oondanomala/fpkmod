package me.oondanomala.fpkmod.labels.simple;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public class LabelYPos extends TextLabel {
    public LabelYPos() {
        super("Y", 2, 29);
    }

    @Override
    protected String getLabelText() {
        return TextUtil.formatDouble(Minecraft.getMinecraft().thePlayer.posY);
    }
}
