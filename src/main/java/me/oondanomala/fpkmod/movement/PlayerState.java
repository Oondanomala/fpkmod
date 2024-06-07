package me.oondanomala.fpkmod.movement;

public class PlayerState {
    public final double posX;
    public final double posY;
    public final double posZ;
    public final float yaw;

    public final boolean onGround;

    public final boolean keyForward;
    public final boolean keyBackward;
    public final boolean keyLeft;
    public final boolean keyRight;

    public PlayerState() {
        this(0, 0, 0, 0, true, false, false, false, false);
    }

    public PlayerState(double posX, double posY, double posZ, float yaw, boolean onGround, boolean keyForward, boolean keyBackward, boolean keyLeft, boolean keyRight) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.onGround = onGround;
        this.keyForward = keyForward;
        this.keyBackward = keyBackward;
        this.keyLeft = keyLeft;
        this.keyRight = keyRight;
    }

    public boolean isStrafing() {
        return keyLeft ^ keyRight;
    }
}
