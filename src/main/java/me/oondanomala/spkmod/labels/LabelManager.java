package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.labels.input.*;
import me.oondanomala.spkmod.labels.jump.*;
import me.oondanomala.spkmod.labels.misc.*;
import me.oondanomala.spkmod.labels.movement.*;
import me.oondanomala.spkmod.labels.simple.*;
import me.oondanomala.spkmod.labels.special.*;
import me.oondanomala.spkmod.labels.turn.*;
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
                new LabelIP(),
                // Simple
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
                new LabelLandingZ(),
                // Movement
                new LabelSpeedX(),
                new LabelSpeedY(),
                new LabelSpeedZ(),
                new LabelSpeedVector(),
                // Input
                new LabelLastInput(),
                new LabelJumpInput(),
                // Turn
                new LabelLastTurning(),
                // Special
                new LabelAirtime(),
                new LabelTier()
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
        SPKMod.config.saveConfig();
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
