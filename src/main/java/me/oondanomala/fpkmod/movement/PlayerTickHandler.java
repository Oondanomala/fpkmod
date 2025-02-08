package me.oondanomala.fpkmod.movement;

import me.oondanomala.fpkmod.commands.subcommands.TogglesprintCommand;
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
        PlayerState currentState = getNewPlayerState(player, Minecraft.getMinecraft().gameSettings);
        boolean isJumpTick = false;

        // Player has jumped
        if (pastState.onGround && !player.onGround && player.posY >= pastState.posY && player.movementInput.jump) {
            lastJumpState = currentState;
            isJumpTick = true;
        }

        // Player has landed
        if (player.onGround && !pastState.onGround && player.posY < pastState.posY) {
            lastHitState = currentState;
            lastLandingState = pastState;
        }

        // Togglesprint
        if (TogglesprintCommand.sprintToggled) {
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), true);
        }

        // Landing Blocks
        LBManager.updateLandingBlocks(pastState, secondPastState);
        // ParkourHandler
        ParkourHandler.update(player, pastState, secondPastState, isJumpTick);

        secondPastState = pastState;
        pastState = currentState;
    }

    private PlayerState getNewPlayerState(EntityPlayer player, GameSettings settings) {
        return new PlayerState(
                player.posX,
                player.posY,
                player.posZ,
                player.rotationYaw,
                player.getEntityBoundingBox(),
                player.onGround,
                settings.keyBindForward.isKeyDown(),
                settings.keyBindBack.isKeyDown(),
                settings.keyBindLeft.isKeyDown(),
                settings.keyBindRight.isKeyDown()
        );
    }
}
