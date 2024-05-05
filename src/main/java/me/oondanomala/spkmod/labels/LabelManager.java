package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LabelManager {
    public static final LabelManager instance = new LabelManager();
    public final List<Label> labels;

    public LabelManager() {
        labels = new ArrayList<>();
        labels.addAll(Arrays.asList(
                new LabelVersion(),
                new LabelFPS(),
                new LabelXPos(),
                new LabelYPos()
        ));
    }

    @SubscribeEvent
    public void drawLabels(RenderGameOverlayEvent.Text event) {
        if (SPKMod.config.enableLabels && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            for (Label label : labels) {
                label.drawLabel();
            }
        }
    }
}
