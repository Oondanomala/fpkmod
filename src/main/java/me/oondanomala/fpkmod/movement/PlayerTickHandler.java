package me.oondanomala.fpkmod.movement;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.landingblock.LBManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @see ParkourHandler
 */
public class PlayerTickHandler {
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

    private PlayerState pastState = new PlayerState();
    private PlayerState secondPastState = new PlayerState();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().isGamePaused()) {
            return;
        }

        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        boolean isJumpTick = pastState.onGround && !player.onGround && player.posY >= pastState.posY && player.movementInput.jump;
        PlayerState currentState = getNewPlayerState(player, Minecraft.getMinecraft().gameSettings, isJumpTick);

        if (isJumpTick) {
            lastJumpState = currentState;
        }

        // Player has landed
        boolean isLandTick = player.onGround && !pastState.onGround && player.posY < pastState.posY;
        if (isLandTick) {
            lastHitState = currentState;
            lastLandingState = pastState;
        }

        // Togglesprint
        if (FPKMod.config.sprintToggled) {
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), true);
        }

        // Landing Blocks
        LBManager.updateLandingBlocks(pastState, secondPastState);
        // ParkourHandler
        ParkourHandler.update(player, currentState, pastState, secondPastState, isLandTick);

        secondPastState = pastState;
        pastState = currentState;
    }

    private PlayerState getNewPlayerState(EntityPlayer player, GameSettings settings, boolean isJumpTick) {
        return new PlayerState(
                player.posX,
                player.posY,
                player.posZ,
                player.rotationYaw,
                player.rotationPitch,
                player.getEntityBoundingBox(),
                player.onGround,
                isJumpTick,
                settings.keyBindForward.isKeyDown(),
                settings.keyBindBack.isKeyDown(),
                settings.keyBindLeft.isKeyDown(),
                settings.keyBindRight.isKeyDown(),
                settings.keyBindJump.isKeyDown(),
                settings.keyBindSprint.isKeyDown(),
                settings.keyBindSneak.isKeyDown()
        );
    }
}
