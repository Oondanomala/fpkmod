package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.labels.jump.LabelHitAngle;
import me.oondanomala.spkmod.labels.jump.LabelHitX;
import me.oondanomala.spkmod.labels.jump.LabelHitY;
import me.oondanomala.spkmod.labels.jump.LabelHitZ;
import me.oondanomala.spkmod.labels.jump.LabelJumpAngle;
import me.oondanomala.spkmod.labels.jump.LabelJumpX;
import me.oondanomala.spkmod.labels.jump.LabelJumpY;
import me.oondanomala.spkmod.labels.jump.LabelJumpZ;
import me.oondanomala.spkmod.labels.jump.LabelLandingX;
import me.oondanomala.spkmod.labels.jump.LabelLandingY;
import me.oondanomala.spkmod.labels.jump.LabelLandingZ;
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
                // Misc
                new LabelVersion(),
                new LabelFPS(),
                new LabelDate(),
                new LabelTime(),
                // Other
                new LabelXPos(),
                new LabelYPos(),
                new LabelZPos(),
                new LabelFacing(),
                new LabelPitch(),
                // Jump
                new LabelJumpAngle(),
                new LabelJumpX(),
                new LabelJumpY(),
                new LabelJumpZ(),
                new LabelHitAngle(),
                new LabelHitX(),
                new LabelHitY(),
                new LabelHitZ(),
                new LabelLandingX(),
                new LabelLandingY(),
                new LabelLandingZ()
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
