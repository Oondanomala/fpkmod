package me.oondanomala.fpkmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

public final class RenderUtil {
    private RenderUtil() {
    }

    private static void setRenderOffset(WorldRenderer worldRenderer, float partialTicks) {
        Entity viewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        double xOffset = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
        double yOffset = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
        double zOffset = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;

        worldRenderer.setTranslation(-xOffset, -yOffset, -zOffset);
    }

    private static void finishDrawing(Tessellator tessellator, WorldRenderer worldRenderer) {
        worldRenderer.setTranslation(0, 0, 0);
        tessellator.draw();
    }

    private static void drawCube(AxisAlignedBB boundingBox, float partialTicks) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        setRenderOffset(worldRenderer, partialTicks);

        // Down
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        // Up
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        // West
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        // East
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        // North
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        // South
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();

        finishDrawing(tessellator, worldRenderer);
    }

    /**
     * Draws the provided axis aligned {@code boundingBox} in the world
     * in the provided {@code color}.
     * <p>
     * The box will render with depth disabled
     * (it will be visible behind walls).
     *
     * @param boundingBox The bounding box to draw
     * @param color       The color of the bounding box
     */
    public static void drawBoundingBox(AxisAlignedBB boundingBox, Color color, float partialTicks) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

        drawCube(boundingBox, partialTicks);

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
