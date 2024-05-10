package me.oondanomala.spkmod.movement;

public class PlayerPosition {
    public final double posX;
    public final double posY;
    public final double posZ;
    public final float yaw;

    public final boolean onGround;

    public PlayerPosition() {
        this(0.0, 0.0, 0.0, 0.0F, true);
    }

    public PlayerPosition(double posX, double posY, double posZ, float yaw, boolean onGround) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.onGround = onGround;
    }
}
