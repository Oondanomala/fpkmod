package me.oondanomala.spkmod.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerMovementHandler {
    /**
     * The player state on the previous tick.
     */
    public static PlayerState prevTickState = new PlayerState();
    /**
     * The player state on jump tick.
     */
    public static PlayerState lastJumpState = new PlayerState();
    /**
     * The player state on the tick right before land tick.
     */
    public static PlayerState lastLandingState = new PlayerState();
    /**
     * The player state on land tick.
     */
    public static PlayerState lastHitState = new PlayerState();
    // Not actually the past tick for anything other than this class
    private PlayerState pastState = new PlayerState();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().isGamePaused()) {
            return;
        }
        // This will probably need refactoring... But it's fine for now
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        PlayerState currentState = getNewPlayerState(player, gameSettings);

        // Player has jumped
        if (pastState.onGround && !player.onGround && player.posY >= pastState.posY && Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
            lastJumpState = currentState;
        }

        // Player has landed
        if (player.onGround && !pastState.onGround && player.posY < pastState.posY) {
            lastHitState = currentState;
            lastLandingState = pastState;
        }

        prevTickState = pastState;
        pastState = currentState;
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
