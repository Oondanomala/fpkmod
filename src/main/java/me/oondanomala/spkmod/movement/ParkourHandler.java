package me.oondanomala.spkmod.movement;

import net.minecraft.client.entity.EntityPlayerSP;

// Better class name?
public class ParkourHandler {
    /**
     * The player speed on the X axis.
     */
    public static double speedX;
    /**
     * The player speed on the Y axis.
     */
    public static double speedY;
    /**
     * The player speed on the Z axis.
     */
    public static double speedZ;
    /**
     * The size of the turn made in the current tick.
     */
    public static float lastTurning;
    /**
     * The amount of ticks the player has been in the air for.
     */
    public static int airtime;
    /**
     * The jump tier, positive when going up, negative when going down.
     *
     * @see <a href="https://www.mcpk.wiki/wiki/Tiers">mcpk.wiki/wiki/Tiers</a>
     */
    public static int tier;

    static void update(EntityPlayerSP player, PlayerState pastState, boolean isJumpTick) {
        // Speed
        speedX = player.posX - pastState.posX;
        speedY = player.posY - pastState.posY;
        speedZ = player.posZ - pastState.posZ;

        // Airtime & Tier
        if (pastState.onGround && !player.onGround) {
            if (isJumpTick) {
                airtime = 1;
                tier = 11;
            } else {
                airtime = 0;
                tier = 0;
            }
        } else if (!pastState.onGround) {
            airtime++;
            tier--;
        }
        // TODO: Is resetting airtime when in liquids valid?
        if (player.capabilities.isFlying) {
            airtime = 0;
            tier = 0;
        }

        // Last Turning
        if (player.rotationYaw != pastState.yaw) {
            lastTurning = player.rotationYaw - pastState.yaw;
        }
    }
}
