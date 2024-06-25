package me.oondanomala.fpkmod.util;

import net.minecraft.client.Minecraft;
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

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (guiToDisplay != null) {
            Minecraft.getMinecraft().displayGuiScreen(guiToDisplay);
            guiToDisplay = null;
        }
    }
}
