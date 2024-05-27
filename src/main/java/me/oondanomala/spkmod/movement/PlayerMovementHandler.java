package me.oondanomala.spkmod.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerMovementHandler {
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

    // TODO: Move to another class
    /**
     * The player speed on the X axis.
     * Implemented as:
     * <pre>{@code player.posX - lastTick.posX;}</pre>
     */
    public static double speedX;
    /**
     * The player speed on the Y axis.
     * Implemented as:
     * <pre>{@code player.posY - lastTick.posY;}</pre>
     */
    public static double speedY;
    /**
     * The player speed on the Z axis.
     * Implemented as:
     * <pre>{@code player.posZ - lastTick.posZ;}</pre>
     */
    public static double speedZ;
    /**
     * The size of the turn made in the current tick.
     * Implemented as:
     * <pre>{@code
     * if (player.rotationYaw != lastTick.yaw) {
     *     player.rotationYaw - lastTick.yaw;
     * }
     * }</pre>
     */
    public static float lastTurning;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().isGamePaused()) {
            return;
        }
        // This will probably need refactoring... But it's fine for now
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        PlayerState currentState = getNewPlayerState(player, Minecraft.getMinecraft().gameSettings);

        // Player has jumped
        if (pastState.onGround && !player.onGround && player.posY >= pastState.posY && player.movementInput.jump) {
            lastJumpState = currentState;
        }

        // Player has landed
        if (player.onGround && !pastState.onGround && player.posY < pastState.posY) {
            lastHitState = currentState;
            lastLandingState = pastState;
        }

        // Update [CLASS NAME]
        if (player.rotationYaw != pastState.yaw) {
            lastTurning = player.rotationYaw - pastState.yaw;
        }

        speedX = player.posX - pastState.posX;
        speedY = player.posY - pastState.posY;
        speedZ = player.posZ - pastState.posZ;
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
