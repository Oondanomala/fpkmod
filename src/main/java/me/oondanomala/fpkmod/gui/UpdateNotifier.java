package me.oondanomala.fpkmod.gui;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.ref.WeakReference;
import java.net.URI;
import java.util.Objects;
import java.util.Random;

public class UpdateNotifier {
    private static final String[] SPLASHES = {"Floating point errors suck!", "No, the \"F\" does not stand for furry!", "You like kissing boys don't you", ":3", "UwU", "meow", "rawr", "rawr x3", "^w^", "OwO", ":3c"};
    private static final Random RANDOM = new Random();
    private final ForgeVersion.CheckResult updateResult;
    private WeakReference<GuiScreen> oldMenu;

    public UpdateNotifier() {
        updateResult = ForgeVersion.getResult(Loader.instance().activeModContainer());
    }

    @SubscribeEvent
    public void onMainMenuInit(GuiScreenEvent.InitGuiEvent.Post event) {
        if (!(event.gui instanceof GuiMainMenu)) return;
        GuiMainMenu mainMenu = (GuiMainMenu) event.gui;

        if (updateResult.status == ForgeVersion.Status.OUTDATED) {
            String updateString = TextUtil.assembleText("New " + FPKMod.NAME + " version available:", Objects.toString(updateResult.target));
            String updateStringHover = TextUtil.assembleText(EnumChatFormatting.UNDERLINE + "New " + FPKMod.NAME + " version available:", EnumChatFormatting.UNDERLINE + Objects.toString(updateResult.target));
            int buttonWidth = mainMenu.mc.fontRendererObj.getStringWidth(updateString) + 2;
            event.buttonList.add(new UpdateReminderButton(mainMenu, buttonWidth, updateString, updateStringHover));
        }

        try {
            // Only try to change the splash text if the main menu instance changes
            if (oldMenu != null && mainMenu == oldMenu.get()) {
                return;
            }
            oldMenu = new WeakReference<>(mainMenu);

            if (RANDOM.nextInt(100) == 42) {
                mainMenu.splashText = SPLASHES[RANDOM.nextInt(SPLASHES.length)];
            }
        } catch (Throwable e) {
            // Don't crash just because this failed
            FPKMod.LOGGER.error("Main menu splash fix failed!", e);
        }
    }

    private class UpdateReminderButton extends GuiButton {
        private final String displayStringHover;
        private final GuiScreen parent;

        public UpdateReminderButton(GuiScreen parent, int width, String displayString, String displayStringHover) {
            super(778, parent.width - width, 0, width, parent.mc.fontRendererObj.FONT_HEIGHT + 2, displayString);
            this.parent = parent;
            this.displayStringHover = displayStringHover;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (visible) {
                hovered = mouseX >= xPosition && mouseY < yPosition + height;
                drawString(mc.fontRendererObj, hovered ? displayStringHover : displayString, xPosition, yPosition + 2, -1);
            }
        }

        @Override
        public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
            if (super.mousePressed(mc, mouseX, mouseY) && updateResult.url != null) {
                parent.openWebLink(URI.create(updateResult.url));
                return true;
            }
            return false;
        }
    }
}
