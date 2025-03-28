package me.oondanomala.fpkmod.movement;

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
     * The highest speed achieved on the X axis. Resets when the movement direction changes,
     * or when {@link me.oondanomala.fpkmod.commands.subcommands.ClearMaxSpeedCommand /fpk clearmaxspeed} is run.
     */
    public static double maxSpeedX;
    /**
     * The highest speed achieved on the Y axis. Resets when the movement direction changes,
     * or when {@link me.oondanomala.fpkmod.commands.subcommands.ClearMaxSpeedCommand /fpk clearmaxspeed} is run.
     */
    public static double maxSpeedY;
    /**
     * The highest speed achieved on the Z axis. Resets when the movement direction changes,
     * or when {@link me.oondanomala.fpkmod.commands.subcommands.ClearMaxSpeedCommand /fpk clearmaxspeed} is run.
     */
    public static double maxSpeedZ;
    /**
     * The size of the turn made in the current tick.
     */
    public static float lastTurning;
    /**
     * The size of the turn made in the tick right before jump tick.
     */
    public static float preturn;
    /**
     * The size of the turn made in the tick right after jump tick, if the player started strafing.
     */
    public static float last45;
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
    /**
     * The amount of grinds chained. Resets when a jump isn't a grind.
     *
     * @see <a href="https://www.mcpk.wiki/wiki/Jump_Cancel#Ceiling_Variant">mcpk.wiki/wiki/Jump_Cancel</a>
     */
    public static int grinds;
    /**
     * Type of sidestep where 0 is wdwa and anything else is wad
     */
    public static int sidestep;

    static void update(EntityPlayerSP player, PlayerState pastState, PlayerState secondPastState, boolean isJumpTick) {
        // Speed
        speedX = player.posX - pastState.posX;
        speedY = player.posY - pastState.posY;
        speedZ = player.posZ - pastState.posZ;

        // Max Speed
        // TODO: Reset on stop option
        if (speedX != 0) {
            if (Math.abs(speedX) > Math.abs(maxSpeedX) || Math.signum(speedX) != Math.signum(maxSpeedX)) {
                maxSpeedX = speedX;
            }
        }
        if (speedY != 0) {
            if (Math.abs(speedY) > Math.abs(maxSpeedY) || Math.signum(speedY) != Math.signum(maxSpeedY)) {
                maxSpeedY = speedY;
            }
        }
        if (speedZ != 0) {
            if (Math.abs(speedZ) > Math.abs(maxSpeedZ) || Math.signum(speedZ) != Math.signum(maxSpeedZ)) {
                maxSpeedZ = speedZ;
            }
        }

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

        if (isJumpTick) {
            // Grind
            // TODO: Add stairboosts?
            if (player.posY == pastState.posY) {
                grinds++;
            } else {
                grinds = 0;
            }
            // Preturn
            preturn = pastState.yaw - secondPastState.yaw;
        }

        // Last Turning
        if (player.rotationYaw != pastState.yaw) {
            lastTurning = player.rotationYaw - pastState.yaw;
        }

        // Last 45
        if (secondPastState.onGround && !pastState.onGround && player.movementInput.moveStrafe != 0 && !pastState.isStrafing()) {
            last45 = player.rotationYaw - pastState.yaw;
        }

        // Sidestep
        if (isJumpTick) {
            boolean strafingLeft = player.movementInput.moveStrafe > 0.0;
            if (pastState.isStrafing() && player.movementInput.moveStrafe != 0.0 && (strafingLeft != pastState.keyLeft)) {
            boolean strafingLeft = player.movementInput.moveStrafe > 0f;
            if (pastState.isStrafing() && player.movementInput.moveStrafe != 0f && (strafingLeft != pastState.keyLeft)) {
                sidestep = 0;
            } else {
                sidestep = 1;
            }
        } else if (sidestep > 0 && !player.onGround) {
            if (!pastState.isStrafing() && player.movementInput.moveStrafe == 0f) {
                sidestep++;
            } else {
                sidestep *= -1;
            }
        }
    }
}
