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

    private static void drawOutline(Tessellator tessellator, WorldRenderer renderer, AxisAlignedBB boundingBox) {
        renderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);

        // Bottom
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        // Top
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        // Sides
        renderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }

    private static void drawCube(Tessellator tessellator, WorldRenderer renderer, AxisAlignedBB boundingBox) {
        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);

        // Down
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        // Up
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        // West
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        // East
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        // North
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        // South
        renderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        renderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();

        tessellator.draw();
    }

    /**
     * Draws the provided axis aligned {@code boundingBox} in the world
     * in the provided {@code color}.
     *
     * @param boundingBox The bounding box to draw, not <tt>null</tt>
     * @param color       The color of the bounding box, not <tt>null</tt>
     */
    public static void drawBoundingBox(AxisAlignedBB boundingBox, Color color, float partialTicks) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();

        GlStateManager.disableTexture2D();
        GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        setRenderOffset(renderer, partialTicks);

        GL11.glLineWidth(2);
        drawOutline(tessellator, renderer, boundingBox);
        GL11.glLineWidth(1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        drawCube(tessellator, renderer, boundingBox);

        renderer.setTranslation(0, 0, 0);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
