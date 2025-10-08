package me.oondanomala.fpkmod.movement;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class PlayerState {
    public final double posX;
    public final double posY;
    public final double posZ;
    public final float yaw;
    public final AxisAlignedBB boundingBox;

    public final boolean onGround;

    public final boolean keyForward;
    public final boolean keyBackward;
    public final boolean keyLeft;
    public final boolean keyRight;

    public float forwardMove = 0.0f;
    public float strafeMove = 0.0f;
    public boolean sprinting = true;
    public boolean jumping = false;
    public boolean sneaking = false;
    public int duration = 1;

    public PlayerState() {
        this(0, 0, 0, 0, null, true, false, false, false, false);
    }

    public PlayerState(double posX, double posY, double posZ, float yaw, AxisAlignedBB boundingBox,
            boolean onGround, boolean keyForward, boolean keyBackward, boolean keyLeft,
            boolean keyRight) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.boundingBox = boundingBox;
        this.onGround = onGround;
        this.keyForward = keyForward;
        this.keyBackward = keyBackward;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
    }

    public boolean isStrafing() {
        return keyLeft ^ keyRight;
    }

    public Vec3 getPositionVec() {
        return new Vec3(posX, posY, posZ);
    }

    public void walk() {
        this.sprinting = false;
    }

    public void jump() {
        this.jumping = true;
    }

    public void sneak() {
        this.sneaking = true;
    }

    public void move(float forwards, float sideways) {
        this.forwardMove = forwards;
        this.strafeMove = sideways;
    }

    public boolean isMoving() {
        return Math.abs(forwardMove) > 0f || Math.abs(strafeMove) > 0f;
    }

    public boolean isMovementEqual(PlayerState other) {
        return forwardMove == other.forwardMove && strafeMove == other.strafeMove
                && sprinting == other.sprinting && jumping == other.jumping
                && sneaking == other.sneaking;
    }
}
