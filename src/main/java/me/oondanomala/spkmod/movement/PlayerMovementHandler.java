package me.oondanomala.spkmod.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        PlayerPosition currentPosition = new PlayerPosition(player.posX, player.posY, player.posZ, player.rotationYaw, player.onGround);

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
}
