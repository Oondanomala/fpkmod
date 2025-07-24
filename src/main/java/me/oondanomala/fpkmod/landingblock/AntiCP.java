package me.oondanomala.fpkmod.landingblock;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiCP {
    public static final AntiCP INSTANCE = new AntiCP();
    private long lastLandTime;
    private boolean hasClicked;

    private AntiCP() {
    }

    public void hasLanded() {
        lastLandTime = System.currentTimeMillis();
        hasClicked = false;
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) return;

        if (FPKMod.config.enableAntiCP && System.currentTimeMillis() - lastLandTime < FPKMod.config.antiCPTime) {
            // TODO: Add option to only block if holding a CP item
            if (!hasClicked) {
                TextUtil.showChatMessage("Right-click blocked by AntiCP.");
                hasClicked = true;
            }
            event.setCanceled(true);
        }
    }
}
