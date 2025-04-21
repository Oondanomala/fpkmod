package me.oondanomala.fpkmod.landingblock;

import me.oondanomala.fpkmod.util.MathUtil;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class LandOffset {
    public final double xOffset;
    public final double zOffset;
    public final double combinedOffset;

    public LandOffset(AxisAlignedBB playerBB, Vec3 playerPos, AxisAlignedBB landingBox, AxisAlignedBB wallBox, boolean box) {
        double landOffsetX = box ? getXOffset(landingBox, playerPos) : getXOffset(landingBox, playerBB);
        double landOffsetZ = box ? getZOffset(landingBox, playerPos) : getZOffset(landingBox, playerBB);

        this.xOffset = Math.min(landOffsetX, getWallXOffset(wallBox, playerBB));
        this.zOffset = Math.min(landOffsetZ, getWallZOffset(wallBox, playerBB));
        this.combinedOffset = getCombinedOffset();
    }

    /**
     * Calculates either the distance between the player box maximum and the landing box minimum {@code X} coordinates,
     * or the player box minimum and the landing box maximum {@code X} coordinates, whichever is smaller.
     *
     * @param landingBox The landing box
     * @param playerBox  The player's bounding box
     * @return The distance between {@code landingBox} and {@code playerBox} in the {@code X} axis
     */
    private double getXOffset(AxisAlignedBB landingBox, AxisAlignedBB playerBox) {
        // Note: the signs are flipped, then flipped again, to turn 0 into -0.
        return -Math.max(landingBox.minX - playerBox.maxX, playerBox.minX - landingBox.maxX);
    }

    /**
     * Calculates the distance between the player vector {@code X} coordinate and
     * either the minimum or maximum bounding box {@code X} coordinate, whichever is smaller.
     *
     * @param landingBox The bounding box
     * @param playerPos  The player's position vector
     * @return The distance between {@code landingBox} and {@code playerPos} in the {@code X} axis
     */
    private double getXOffset(AxisAlignedBB landingBox, Vec3 playerPos) {
        // Note: the signs are flipped, then flipped again, to turn 0 into -0.
        return -Math.max(landingBox.minX - playerPos.xCoord, playerPos.xCoord - landingBox.maxX);
    }

    /**
     * Calculates either the distance between the player box maximum and the landing box minimum {@code Z} coordinates,
     * or the player box minimum and the landing box maximum {@code Z} coordinates, whichever is smaller.
     *
     * @param landingBox The landing box
     * @param playerBox  The player's bounding box
     * @return The distance between {@code landingBox} and {@code playerBox} in the {@code Z} axis
     */
    private double getZOffset(AxisAlignedBB landingBox, AxisAlignedBB playerBox) {
        // Note: the signs are flipped, then flipped again, to turn 0 into -0.
        return -Math.max(landingBox.minZ - playerBox.maxZ, playerBox.minZ - landingBox.maxZ);
    }

    /**
     * Calculates the distance between the player vector {@code Z} coordinate and
     * either the minimum or maximum bounding box {@code Z} coordinate, whichever is smaller.
     *
     * @param landingBox The bounding box
     * @param playerPos  The player's position vector
     * @return The distance between {@code landingBox} and {@code playerPos} in the {@code Z} axis
     */
    private double getZOffset(AxisAlignedBB landingBox, Vec3 playerPos) {
        // Note: the signs are flipped, then flipped again, to turn 0 into -0.
        return -Math.max(landingBox.minZ - playerPos.zCoord, playerPos.zCoord - landingBox.maxZ);
    }

    private double getWallXOffset(AxisAlignedBB wallBox, AxisAlignedBB playerBox) {
        return Math.min(playerBox.minX - wallBox.minX, wallBox.maxX - playerBox.maxX);
    }

    private double getWallZOffset(AxisAlignedBB wallBox, AxisAlignedBB playerBox) {
        return Math.min(playerBox.minZ - wallBox.minZ, wallBox.maxZ - playerBox.maxZ);
    }

    private double getCombinedOffset() {
        if (MathUtil.compareSign(xOffset, zOffset)) {
            return Math.copySign(Math.hypot(xOffset, zOffset), xOffset);
        }
        return Math.min(xOffset, zOffset);
    }

    public double getAxisOffset(LandAxis axis) {
        switch (axis) {
            case BOTH:
                return combinedOffset;
            case X:
                return xOffset;
            case Z:
                return zOffset;
        }
        throw new IllegalStateException();
    }
}
