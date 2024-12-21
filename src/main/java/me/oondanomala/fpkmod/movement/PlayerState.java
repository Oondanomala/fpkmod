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

    public PlayerState() {
        this(0, 0, 0, 0, null, true, false, false, false, false);
    }

    public PlayerState(double posX, double posY, double posZ, float yaw, AxisAlignedBB boundingBox, boolean onGround, boolean keyForward, boolean keyBackward, boolean keyLeft, boolean keyRight) {
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
}
