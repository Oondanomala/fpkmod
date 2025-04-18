package me.oondanomala.fpkmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class GuiUtil {
    public static final int MOUSE_LEFT = 0;
    public static final int MOUSE_RIGHT = 1;
    private static GuiScreen guiToDisplay;

    public static void displayGui(GuiScreen gui) {
        guiToDisplay = gui;
    }

    public static int getRGBFromColorCode(char colorCode, int alpha) {
        if (alpha < 0 || alpha > 255) {
            throw new IllegalArgumentException("Alpha value must be between 0 and 255 (inclusive)");
        }

        return GuiUtils.getColorCode(colorCode, true) & 0xffffff | alpha << 24;
    }

    public static void drawCenteredString(String text, int posX, int posY, int width, int height, int color) {
        int textWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) / 2;
        int textHeight = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT / 2;
        int halfwayX = posX + Math.round(width / 2f);
        int halfwayY = posY + Math.round(height / 2f);
        Minecraft.getMinecraft().fontRendererObj.drawString(text, halfwayX - textWidth, halfwayY - textHeight, color);
    }

    public static void drawTooltipBackground(int startX, int startY, int endX, int endY) {
        final int backgroundColor = 0xF0100010;
        final int borderColorStart = 0x505000FF;
        final int borderColorEnd = 0x5028007F;
        // Center
        Gui.drawRect(startX, startY + 1, endX, endY - 1, backgroundColor);
        // Outer Border
        Gui.drawRect(startX + 1, startY, endX - 1, startY + 1, backgroundColor);
        Gui.drawRect(startX + 1, endY, endX - 1, endY - 1, backgroundColor);
        // Gradient Border
        Gui.drawRect(startX + 1, startY + 1, endX - 1, startY + 2, borderColorStart);
        Gui.drawRect(startX + 1, endY - 1, endX - 1, endY - 2, borderColorEnd);
        GuiUtils.drawGradientRect(0, startX + 1, startY + 2, startX + 2, endY - 2, borderColorStart, borderColorEnd);
        GuiUtils.drawGradientRect(0, endX - 2, startY + 2, endX - 1, endY - 2, borderColorStart, borderColorEnd);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (guiToDisplay != null) {
            Minecraft.getMinecraft().displayGuiScreen(guiToDisplay);
            guiToDisplay = null;
        }
    }
}
