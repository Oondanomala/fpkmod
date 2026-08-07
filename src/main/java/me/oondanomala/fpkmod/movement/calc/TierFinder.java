package me.oondanomala.fpkmod.movement.calc;

import com.github.bsideup.jabel.Desugar;
import me.oondanomala.fpkmod.movement.sim.SimConstants;
import me.oondanomala.fpkmod.movement.sim.YMovementSim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TierFinder {
    public final double[] floorHeights;
    public final double[] ceilingHeights;

    public TierFinder() {
        // All buildable floor and ceiling heights of 1.8.9 (excluding boats),
        // except cauldron bottom as that is useless for landing tiers. MUST remain sorted!
        this.floorHeights = new double[]{.0, .015625, .0625, .125, .1875, .25, .375, .5, .5625, .625, .75, .8125, .875, .9375};
        this.ceilingHeights = new double[]{.0, .1875, .25, .5, .5625, .625, .6875, .75, .8125};
    }

    public TierFinder(double[] floorHeights, double[] ceilingHeights) {
        Arrays.sort(floorHeights);
        Arrays.sort(ceilingHeights);
        this.floorHeights = floorHeights;
        this.ceilingHeights = ceilingHeights;
    }

    public LandGap[] getWorkingGaps(
        YMovementSim sim,
        double goodStartY, boolean jump,
        double[] badStartYPositions, BadPositionJumpMode badJumpMode
    ) {
        List<Double> badHeights = new ArrayList<>();
        for (double badStartY : badStartYPositions) {
            if (badJumpMode == BadPositionJumpMode.BOTH || badJumpMode == BadPositionJumpMode.ONLY_JUMP) {
                badHeights.addAll(sim.simulate(badStartY, true));
            }
            if (badJumpMode == BadPositionJumpMode.BOTH || badJumpMode == BadPositionJumpMode.NO_JUMP) {
                badHeights.addAll(sim.simulate(badStartY, false));
            }
        }

        return sim.simulate(goodStartY, jump).stream()
            .map(this::getSmallestGap).distinct()
            .filter(goodGap -> {
                for (Double badY : badHeights) {
                    if (goodGap.canPlayerFit(badY)) {
                        return false;
                    }
                }
                return true;
            }).toArray(LandGap[]::new);
    }

    public enum BadPositionJumpMode {
        BOTH,
        NO_JUMP,
        ONLY_JUMP;

        private static final BadPositionJumpMode[] VALUES = values();
        public static BadPositionJumpMode fromOrdinal(int ord) {
            return VALUES[ord];
        }
    }

    /**
     * Calculates the smallest buildable gap a
     * player at {@code posY} can fit through.
     *
     * @param posY   The Y position of the player
     * @return The smallest gap the player can fit through
     */
    private LandGap getSmallestGap(double posY) {
        return new LandGap(getHighestFloor(posY), getLowestCeiling(posY));
    }

    @Desugar
    public record LandGap(
        double floorY,
        double ceilingY
    ) {
        public LandGap {
            if (ceilingY <= floorY) {
                throw new IllegalArgumentException("Negative land gap size");
            }
        }

        /**
         * Whether the player can fit in this gap.
         * <p>
         * The player is assumed to be {@link SimConstants#PLAYER_HEIGHT} tall.
         *
         * @param playerY The Y position of the player
         */
        public boolean canPlayerFit(double playerY) {
            return playerY >= floorY && (playerY + SimConstants.PLAYER_HEIGHT) <= ceilingY;
        }
    }

    private double getHighestFloor(double posY) {
        double startingY = Math.floor(posY); // Guaranteed to work
        for (int i = floorHeights.length - 1; i >= 0; i--) {
            double candidateHeight = startingY + floorHeights[i];
            if (posY >= candidateHeight) {
                return candidateHeight;
            }
        }
        // FIXME: This will treat floorHeights as if it includes 0 even if it doesn't
        //  (if all floorHeights are too high then it will return a floor height of 0
        //   instead of decreasing posY by 1)
        return startingY;
    }

    private double getLowestCeiling(double posY) {
        double playerTop = posY + SimConstants.PLAYER_HEIGHT;
        double startingY = Math.ceil(playerTop); // Guaranteed to work
        for (int i = ceilingHeights.length - 1; i >= 0; i--) {
            double candidateHeight = startingY - ceilingHeights[i];
            if (playerTop <= candidateHeight) {
                return candidateHeight;
            }
        }
        // FIXME: This will treat ceilingHeights as if it includes 0 even if it doesn't
        //  (if all ceilingHeights are too low then it will return a ceiling height of 0
        //   instead of increasing posY by 1)
        return startingY;
    }
}
