package me.oondanomala.fpkmod.landingblock;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.movement.PlayerState;
import me.oondanomala.fpkmod.util.RenderUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Tuple;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class LandingBlock {
    private static final Color LANDING_BLOCK_COLOR = new Color(150, 150, 150, 100);
    private static final Color COND_COLOR = new Color(240, 130, 0, 85);

    //private final BlockPos position;
    private final AxisAlignedBB[] landingBoxes;
    private AxisAlignedBB[] wallBoxes;
    private AxisAlignedBB[] renderingBoxes;
    /**
     * The condition box.
     * Land offsets will only be counted if the player's {@code X} and {@code Z} coordinates are inside of this.
     */
    private final AxisAlignedBB condBox;
    public LandOffset lastOffset;
    public LandOffset pbOffset;
    public LandAxis landAxis;
    public LandMode landMode;
    private final boolean boxMode;

    public LandingBlock(BlockPos position, LandMode landMode, LandAxis landAxis, boolean box) {
        //this.position = position;
        this.landAxis = landAxis;
        this.landMode = landMode;
        this.boxMode = box;
        this.landingBoxes = getLandingBoxes(position, box);
        this.wallBoxes = getWallBoxes(landingBoxes);
        this.renderingBoxes = getRenderingBoxes(landingBoxes, wallBoxes, box);
        this.condBox = getCondBox(landingBoxes, box);
    }

    /**
     * Gets all the bounding boxes of the block present at the provided position.
     * If {@code box} is <tt>true</tt> or if the block has no bounding boxes,
     * will instead return a single 1x1 box at the provided position.
     *
     * @param pos The position of the block to get the bounding boxes of
     * @param box Whether to return a single 1x1 box instead
     */
    private AxisAlignedBB[] getLandingBoxes(BlockPos pos, boolean box) {
        World world = Minecraft.getMinecraft().theWorld;
        IBlockState blockState = world.getBlockState(pos);
        List<AxisAlignedBB> boundingBoxes = new ArrayList<>();
        AxisAlignedBB mask = new AxisAlignedBB(pos, pos.add(1, 1, 1)).expand(1, 1, 1);
        blockState.getBlock().addCollisionBoxesToList(world, pos, blockState, mask, boundingBoxes, null);

        // Special case for box mode and weirdly behaving blocks (vines already force box mode)
        if (box || boundingBoxes.isEmpty()) {
            return new AxisAlignedBB[]{new AxisAlignedBB(pos, pos.add(1, 1, 1))};
        }

        return boundingBoxes.toArray(new AxisAlignedBB[0]);
    }

    private AxisAlignedBB[] getWallBoxes(AxisAlignedBB[] landingBoxes) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        World world = Minecraft.getMinecraft().theWorld;
        AxisAlignedBB[] wallBoxes = new AxisAlignedBB[landingBoxes.length];

        for (int i = 0; i < landingBoxes.length; i++) {
            AxisAlignedBB landingBox = landingBoxes[i];
            AxisAlignedBB playerCollisionBox = new AxisAlignedBB(
                    landingBox.minX,
                    landingBox.maxY,
                    landingBox.minZ,
                    landingBox.maxX,
                    landingBox.maxY + player.height,
                    landingBox.maxZ
            ).expand(player.width, 0, player.width);

            double minWallX = Double.NEGATIVE_INFINITY;
            double maxWallX = Double.POSITIVE_INFINITY;
            double minWallZ = Double.NEGATIVE_INFINITY;
            double maxWallZ = Double.POSITIVE_INFINITY;

            // TODO: I'd like to make this smarter, and consider cornering blocks. It's already a massive improvement over MPK and CYV though.
            // TODO: Box mode should consider things that don't let you get "inside" of the landing block as walls (e.g. heads).
            // FIXME: Wall boxes can be smaller than the player's width, which makes no sense since that means you cannot fit in the gap. Maybe delete the landing box if that's the case?
            for (AxisAlignedBB wallBox : world.getCollisionBoxes(playerCollisionBox)) {
                if (wallBox.minX <= landingBox.minX && wallBox.maxX >= landingBox.maxX) {
                    // South
                    if (wallBox.minZ > landingBox.minZ && wallBox.maxZ >= landingBox.maxZ) {
                        maxWallZ = Math.min(wallBox.minZ, maxWallZ);
                    }
                    // North
                    else if (wallBox.minZ <= landingBox.minZ && wallBox.maxZ < landingBox.maxZ) {
                        minWallZ = Math.max(wallBox.maxZ, minWallZ);
                    }
                }
                if (wallBox.minZ <= landingBox.minZ && wallBox.maxZ >= landingBox.maxZ) {
                    // East
                    if (wallBox.minX > landingBox.minX && wallBox.maxX >= landingBox.maxX) {
                        maxWallX = Math.min(wallBox.minX, maxWallX);
                    }
                    // West
                    else if (wallBox.minX <= landingBox.minX && wallBox.maxX < landingBox.maxX) {
                        minWallX = Math.max(wallBox.maxX, minWallX);
                    }
                }
            }
            wallBoxes[i] = new AxisAlignedBB(minWallX, Double.NaN, minWallZ, maxWallX, Double.NaN, maxWallZ);
        }
        return wallBoxes;
    }

    private AxisAlignedBB[] getRenderingBoxes(AxisAlignedBB[] landingBoxes, AxisAlignedBB[] wallBoxes, boolean box) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        AxisAlignedBB[] renderingBoxes = new AxisAlignedBB[landingBoxes.length];
        float playerOffset = box ? 0 : player.width / 2;
        float playerWallOffset = player.width / 2;

        for (int i = 0; i < landingBoxes.length; i++) {
            AxisAlignedBB landingBox = landingBoxes[i];
            AxisAlignedBB wallBox = wallBoxes[i];
            renderingBoxes[i] = new AxisAlignedBB(
                    Math.max(landingBox.minX - playerOffset, wallBox.minX + playerWallOffset),
                    landingBox.minY,
                    Math.max(landingBox.minZ - playerOffset, wallBox.minZ + playerWallOffset),
                    Math.min(landingBox.maxX + playerOffset, wallBox.maxX - playerWallOffset),
                    landingBox.maxY,
                    Math.min(landingBox.maxZ + playerOffset, wallBox.maxZ - playerWallOffset)
            );
        }
        return renderingBoxes;
    }

    /**
     * Gets the union of all landing boxes, expanded by 1 block in the {@code X} and {@code Z} axis.
     * If {@code box} is <tt>false</tt>, it will also be expanded by half of the player's width.
     *
     * @param landingBoxes The boxes to get the union of
     * @param box          Whether to not expand the cond by half of the player's width
     */
    private AxisAlignedBB getCondBox(AxisAlignedBB[] landingBoxes, boolean box) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        AxisAlignedBB cond = landingBoxes[0];
        for (AxisAlignedBB landingBox : landingBoxes) {
            cond = cond.union(landingBox);
        }
        cond = cond.expand(1, 0, 1);
        return box ? cond : cond.expand(player.width / 2, 0, player.width / 2);
    }

    public void draw(float partialTicks) {
        GlStateManager.disableDepth();
        if (FPKMod.config.renderLandingBox) {
            for (AxisAlignedBB landingBox : renderingBoxes) {
                // Maybe render outlines too like cyv does?
                RenderUtil.drawBoundingBox(landingBox, LANDING_BLOCK_COLOR, partialTicks);
            }
        }
        if (FPKMod.config.renderCondBox) {
            RenderUtil.drawBoundingBox(condBox, COND_COLOR, partialTicks);
        }
        GlStateManager.enableDepth();
    }

    /**
     * @param pastState       The player's state on the previous tick
     * @param secondPastState The player's state on the tick before the previous tick
     */
    public void update(PlayerState pastState, PlayerState secondPastState) {
        if (canLand()) {
            Tuple<AxisAlignedBB, Vec3> landPos = getLandPos(pastState, secondPastState);
            lastOffset = getLandOffset(landPos.getFirst(), landPos.getSecond(), boxMode);

            // TODO: Add an option to not count -0 offsets as PBs
            if (pbOffset == null || lastOffset.getAxisOffset(landAxis) > pbOffset.getAxisOffset(landAxis)) {
                pbOffset = lastOffset;
                TextUtil.showChatMessage("New pb! " + TextUtil.formatDouble(pbOffset.getAxisOffset(landAxis)));
            }

            if (FPKMod.config.sendOffsetInChat) {
                if (landAxis == LandAxis.BOTH || landAxis == LandAxis.X) {
                    TextUtil.showChatMessage(EnumChatFormatting.BOLD + "X Offset" + EnumChatFormatting.RESET + ": " + TextUtil.formatDouble(lastOffset.xOffset));
                }
                if (landAxis == LandAxis.BOTH || landAxis == LandAxis.Z) {
                    TextUtil.showChatMessage(EnumChatFormatting.BOLD + "Z Offset" + EnumChatFormatting.RESET + ": " + TextUtil.formatDouble(lastOffset.zOffset));
                }
                // TODO: Remove this once offset labels are implemented
                TextUtil.showChatMessage("Both: " + TextUtil.formatDouble(lastOffset.combinedOffset));
            }
        }
    }

    /**
     * Returns <tt>true</tt> if the player's {@code X} and {@code Z} coordinates are inside of the {@link #condBox}
     * and if {@link #canLandOnBox(AxisAlignedBB)} is <tt>true</tt> for at least one of the {@link #landingBoxes},
     * <tt>false</tt> otherwise.
     */
    private boolean canLand() {
        if (condBox.isVecInXZ(Minecraft.getMinecraft().thePlayer.getPositionVector())) {
            for (AxisAlignedBB landingBox : landingBoxes) {
                if (canLandOnBox(landingBox)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canLandOnBox(AxisAlignedBB landingBox) {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (landMode == LandMode.ENTER) {
            return player.posY < landingBox.maxY && player.posY >= landingBox.minY && player.posY < player.lastTickPosY;
        } else {
            return player.posY <= landingBox.maxY && player.lastTickPosY > landingBox.maxY;
        }
    }

    private Tuple<AxisAlignedBB, Vec3> getLandPos(PlayerState pastState, PlayerState secondPastState) {
        AxisAlignedBB playerBB;
        Vec3 playerVec;
        switch (landMode) {
            case LAND:
                playerBB = pastState.boundingBox;
                playerVec = pastState.getPositionVec();
                break;
            case ZNEO:
                // TODO: Whatever cyv is doing with Neo & Landing (neoAndNormal). Call it Neo or Landing?
                AxisAlignedBB pastTickBB = pastState.boundingBox;
                AxisAlignedBB secondPastTickBB = secondPastState.boundingBox;
                playerBB = new AxisAlignedBB(pastTickBB.minX, secondPastTickBB.minY, secondPastTickBB.minZ, pastTickBB.maxX, secondPastTickBB.maxY, secondPastTickBB.maxZ);
                playerVec = new Vec3(pastState.posX, secondPastState.posY, secondPastState.posZ);
                break;
            case ENTER:
            case HIT:
                EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                playerBB = player.getEntityBoundingBox();
                playerVec = player.getPositionVector();
                break;
            default:
                // Java 8 switch statements suck.
                throw new IllegalStateException();
        }
        return new Tuple<>(playerBB, playerVec);
    }

    private LandOffset getLandOffset(AxisAlignedBB playerBB, Vec3 playerPos, boolean box) {
        LandOffset offset = null;
        for (int i = 0; i < landingBoxes.length; i++) {
            if (canLandOnBox(landingBoxes[i])) {
                LandOffset newOffset = new LandOffset(playerBB, playerPos, landingBoxes[i], wallBoxes[i], box);

                if (offset == null) {
                    offset = newOffset;
                } else if (newOffset.xOffset > offset.xOffset || newOffset.zOffset > offset.zOffset) {
                    // TODO: Not sure if the check above is needed, or if this entire thing is correct.
                    // Maybe use getAxisOffset(landAxis) if the signs of the offsets are the same?
                    if (newOffset.combinedOffset > offset.combinedOffset) {
                        offset = newOffset;
                    }
                }
            }
        }

        return offset;
    }

    public void recalculateWalls() {
        wallBoxes = getWallBoxes(landingBoxes);
        renderingBoxes = getRenderingBoxes(landingBoxes, wallBoxes, boxMode);
    }

    public void cycleLandMode() {
        LandMode[] values = LandMode.values();
        landMode = values[(landMode.ordinal() + 1) % values.length];
    }

    public void cycleAxis() {
        LandAxis[] values = LandAxis.values();
        landAxis = values[(landAxis.ordinal() + 1) % values.length];
    }
}
