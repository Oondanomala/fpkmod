package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class Label {
    public final String labelName;

    public Label(String labelName) {
        this.labelName = labelName;
    }

    @SubscribeEvent
    public void drawLabel(RenderGameOverlayEvent.Text event) {
        if (SPKMod.config.enableLabels && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(TextUtil.assembleText(labelName, getLabelText(), ": "), 0, 0, 255);
        }
    }

    protected abstract String getLabelText();
}
