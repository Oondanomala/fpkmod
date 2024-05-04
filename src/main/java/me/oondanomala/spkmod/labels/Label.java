package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

public abstract class Label {
    public final String labelName;
    public boolean isEnabled = true;

    protected Label(String labelName) {
        this.labelName = labelName;
    }

    public void drawLabel() {
        if (isEnabled) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(TextUtil.assembleText(labelName, getLabelText(), ": "), 0, 0, 255);
        }
    }

    protected abstract String getLabelText();
}
