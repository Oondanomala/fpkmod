package me.oondanomala.fpkmod.movement;

import com.github.bsideup.jabel.Desugar;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

@Desugar
public record PlayerState(
    double posX,
    double posY,
    double posZ,
    float yaw,
    float pitch,
    AxisAlignedBB boundingBox,
    boolean onGround,
    boolean isJumpTick,
    boolean keyForward,
    boolean keyBackward,
    boolean keyLeft,
    boolean keyRight,
    boolean keyJump,
    boolean keySprint,
    boolean keySneak
) {
    public PlayerState() {
        this(0, 0, 0, 0, 0, null, true, false, false, false, false, false, false, false, false);
    }

    public boolean isHoldingMovementKeys() {
        return (keyForward ^ keyBackward) || (keyLeft ^ keyRight);
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
}
