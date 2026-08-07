package me.oondanomala.fpkmod.movement.sim;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulator for Y axis movement.
 * <p>
 * Supports jump boost.
 */
public class YMovementSim {
    private final int jumpBoost;

    public YMovementSim() {
        this(0);
    }

    /**
     * Constructs a new movement sim with the default settings.
     */
    public YMovementSim(int jumpBoostLevel) {
        this.jumpBoost = jumpBoostLevel;
    }

    public List<Double> simulate(double startY, boolean jump) {
        // TODO: Implement:
        //  - Slimes
        //  - endY
        //  - Ceilings
        //  - Webs?

        List<Double> heights = new ArrayList<>();
        double velocityY = jump ? (0.42 + jumpBoost * 0.1f) : 0;
        double posY = startY;

        while (true) {
            posY += velocityY;

            // Too low to matter
            if (posY < 0) {
                break;
            }

            velocityY = (velocityY - 0.08) * 0.98;
            if (Math.abs(velocityY) < SimConstants.INERTIA) {
                velocityY = 0;
            }
            heights.add(posY);
        }

        return heights;
    }
}
