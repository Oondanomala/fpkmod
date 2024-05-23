package me.oondanomala.spkmod.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerMovementHandler {
    public static PlayerPosition lastJumpPosition = new PlayerPosition();
    public static PlayerPosition lastLandingPosition = new PlayerPosition();
    public static PlayerPosition lastHitPosition = new PlayerPosition();
    private PlayerPosition pastPosition = new PlayerPosition();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().isGamePaused()) {
            return;
        }
        // This will probably need refactoring... But it's fine for now
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        PlayerPosition currentPosition = getNewPlayerPosition(player, gameSettings);

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

    private PlayerPosition getNewPlayerPosition(EntityPlayer player, GameSettings settings) {
        return new PlayerPosition(
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