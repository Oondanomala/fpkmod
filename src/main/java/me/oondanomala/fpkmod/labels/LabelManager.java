package me.oondanomala.fpkmod.labels;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.labels.input.*;
import me.oondanomala.fpkmod.labels.jump.*;
import me.oondanomala.fpkmod.labels.misc.*;
import me.oondanomala.fpkmod.labels.movement.*;
import me.oondanomala.fpkmod.labels.simple.*;
import me.oondanomala.fpkmod.labels.special.*;
import me.oondanomala.fpkmod.labels.turn.*;
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
                new LabelTogglesprint(),
                // Simple
                new LabelXPos(),
                new LabelYPos(),
                new LabelZPos(),
                new LabelFacing(),
                new LabelRawFacing(),
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
                new LabelMaxSpeedX(),
                new LabelMaxSpeedY(),
                new LabelMaxSpeedZ(),
                new LabelMaxSpeedVector(),
                // Input
                new LabelLastInput(),
                new LabelJumpInput(),
                new LabelKeystrokes(),
                new LabelLastSidestep(),
                // Turn
                new LabelLastTurning(),
                new LabelPreturn(),
                new LabelLast45(),
                // Special
                new LabelAirtime(),
                new LabelTier(),
                new LabelGrind()
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
        FPKMod.config.saveConfig();
    }

    @SubscribeEvent
    public void drawLabels(RenderGameOverlayEvent.Text event) {
        if (FPKMod.config.enableLabels && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            for (Label label : labels) {
                label.draw();
            }
        }
    }
}
