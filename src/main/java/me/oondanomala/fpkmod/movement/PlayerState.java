package me.oondanomala.fpkmod.movement;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class PlayerState {
    public final double posX;
    public final double posY;
    public final double posZ;
    public final float yaw;
    public final float pitch;
    public final AxisAlignedBB boundingBox;

    public final boolean onGround;
    public final boolean isJumpTick;

    public final boolean keyForward;
    public final boolean keyBackward;
    public final boolean keyLeft;
    public final boolean keyRight;
    public final boolean keyJump;
    public final boolean keySprint;
    public final boolean keySneak;

    public PlayerState() {
        this(0, 0, 0, 0, 0, null, true, false, false, false, false, false, false, false, false);
    }

    public PlayerState(double posX, double posY, double posZ, float yaw, float pitch, AxisAlignedBB boundingBox, boolean onGround, boolean isJumpTick, boolean keyForward, boolean keyBackward, boolean keyLeft, boolean keyRight, boolean keyJump, boolean keySprint, boolean keySneak) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.boundingBox = boundingBox;
        this.onGround = onGround;
        this.isJumpTick = isJumpTick;
        this.keyForward = keyForward;
        this.keyBackward = keyBackward;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
        this.keyJump = keyJump;
        this.keySprint = keySprint;
        this.keySneak = keySneak;
    }

    /**
     * Returns <tt>true</tt> if either the left or right movement keys are pressed,
     * and <tt>false</tt> if none or both are pressed.
     * In other words, a {@code XOR} operation.
     */
    public boolean isStrafing() {
        return keyLeft ^ keyRight;
    }

    /**
     * @return A vector composed of the {@code X}, {@code Y}, and {@code Z} coordinates of this player state
     */
    public Vec3 getPositionVec() {
        return new Vec3(posX, posY, posZ);
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", posZ=" + posZ +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", boundingBox=" + boundingBox +
                ", onGround=" + onGround +
                ", isJumpTick=" + isJumpTick +
                ", keyForward=" + keyForward +
                ", keyBackward=" + keyBackward +
                ", keyLeft=" + keyLeft +
                ", keyRight=" + keyRight +
                ", keyJump=" + keyJump +
                ", keySprint=" + keySprint +
                ", keySneak=" + keySneak +
                '}';
    }
}
