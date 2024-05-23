package me.oondanomala.spkmod.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerMovementHandler {
    /**
     * The player state on jump tick.
     */
    public static PlayerState lastJumpPosition = new PlayerState();
    /**
     * The player state on the tick right before land tick.
     */
    public static PlayerState lastLandingPosition = new PlayerState();
    /**
     * The player state on land tick.
     */
    public static PlayerState lastHitPosition = new PlayerState();
    private PlayerState pastPosition = new PlayerState();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().isGamePaused()) {
            return;
        }
        // This will probably need refactoring... But it's fine for now
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        PlayerState currentPosition = getNewPlayerState(player, gameSettings);

        // Player has jumped
        if (pastPosition.onGround && !player.onGround && player.posY >= pastPosition.posY && Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
            lastJumpPosition = currentPosition;
        }

        // Player has landed
        if (player.onGround && !pastPosition.onGround && player.posY < pastPosition.posY) {
            lastHitPosition = currentPosition;
            lastLandingPosition = pastPosition;
        }

        pastPosition = currentPosition;
    }

    private PlayerState getNewPlayerState(EntityPlayer player, GameSettings settings) {
        return new PlayerState(
                player.posX,
                player.posY,
                player.posZ,
                player.rotationYaw,
                player.onGround,
                settings.keyBindForward.isKeyDown(),
                settings.keyBindBack.isKeyDown(),
                settings.keyBindLeft.isKeyDown(),
                settings.keyBindRight.isKeyDown()
        );
    }
}
