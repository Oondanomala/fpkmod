package me.oondanomala.fpkmod.movement.sim;

/**
 * Useful game constants used in movement simulation code.
 */
public final class SimConstants {
    private SimConstants() {
    }

    /**
     * The threshold of velocity below which
     * velocity gets set to {@code 0}.
     */
    public static final double INERTIA = 0.005;
    /**
     * The (approximate) player hitbox height.
     * <p>
     * Note that the true size is calculated from
     * the player's hitbox height,
     * as it can actually change because of
     * floating point inaccuracies.
     */
    public static final float PLAYER_HEIGHT = 1.8f;
}
