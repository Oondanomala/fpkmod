package me.oondanomala.spkmod.movement;

public class PlayerPosition {
    public final double posX;
    public final double posY;
    public final double posZ;
    public final float yaw;

    public final boolean onGround;

    public final boolean keyForward;
    public final boolean keyBackward;
    public final boolean keyLeft;
    public final boolean keyRight;

    public PlayerPosition() {
        this(0.0, 0.0, 0.0, 0.0F, true, false, false, false, false);
    }

    public PlayerPosition(double posX, double posY, double posZ, float yaw, boolean onGround, boolean keyForward, boolean keyBackward, boolean keyLeft, boolean keyRight) {
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
}
