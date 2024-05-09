package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.labels.misc.LabelDate;
import me.oondanomala.spkmod.labels.misc.LabelFPS;
import me.oondanomala.spkmod.labels.misc.LabelTime;
import me.oondanomala.spkmod.labels.misc.LabelVersion;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

public class LabelManager {
    public static final LabelManager instance = new LabelManager();
    public final List<Label> labels;

    public LabelManager() {
        labels = Arrays.asList(
                new LabelVersion(),
                new LabelFPS(),
                new LabelXPos(),
                new LabelYPos(),
                new LabelZPos(),
                new LabelFacing(),
                new LabelPitch(),
                new LabelDate(),
                new LabelTime()
        );
    }

    public void loadLabelsConfig() {
        for (Label label : labels) {
            label.loadLabelConfig();
        }
    }

    public void saveLabelsConfig() {
        for (Label label : labels) {
            label.saveLabelConfig();
        }
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
