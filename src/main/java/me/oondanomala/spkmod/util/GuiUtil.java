package me.oondanomala.spkmod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GuiUtil {
    private static GuiScreen guiToDisplay;

    public static void displayGui(GuiScreen gui) {
        guiToDisplay = gui;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (guiToDisplay != null) {
                Minecraft.getMinecraft().displayGuiScreen(guiToDisplay);
                guiToDisplay = null;
            }
        }
    }
}
