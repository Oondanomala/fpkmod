package me.oondanomala.fpkmod.landingblock;

import me.oondanomala.fpkmod.movement.PlayerState;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class LBManager {
    private static final List<LandingBlock> landingBlocks = new ArrayList<>();

    public static void addLandingBlock(LandingBlock landingBlock) {
        // TODO: Multiple landing blocks
        landingBlocks.clear(); // temporary, tho I probably do want to set a limit on them
        landingBlocks.add(landingBlock);
    }

    // getCurrentLandingBlock ?
    public static LandingBlock getSelectedLandingBlock() {
        if (landingBlocks.isEmpty()) return null;
        // TODO: Actually choose the selected one
        return landingBlocks.get(landingBlocks.size() - 1);
    }

    public static void clearLandingBlocks() {
        landingBlocks.clear();
    }

    public static void updateLandingBlocks(PlayerState pastState, PlayerState secondPastState) {
        for (LandingBlock landingBlock : landingBlocks) {
            landingBlock.update(pastState, secondPastState);
        }
    }

    @SubscribeEvent
    public void renderLandingBlocks(RenderWorldLastEvent event) {
        for (LandingBlock landingBlock : landingBlocks) {
            landingBlock.draw(event.partialTicks);
        }
    }
}
