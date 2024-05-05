package me.oondanomala.spkmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.Color;

public class GuiUtil {
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

        return new Color(GuiUtils.getColorCode(colorCode, true) & 0xffffff | alpha << 24, true).getRGB();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (guiToDisplay != null && event.phase == TickEvent.Phase.END) {
            Minecraft.getMinecraft().displayGuiScreen(guiToDisplay);
            guiToDisplay = null;
        }
    }
}
