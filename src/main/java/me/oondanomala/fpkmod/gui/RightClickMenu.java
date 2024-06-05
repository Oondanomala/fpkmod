package me.oondanomala.fpkmod.gui;

import me.oondanomala.fpkmod.labels.Label;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.client.config.GuiCheckBox;

import java.util.ArrayList;
import java.util.List;

public class RightClickMenu extends Gui {
    private final int posX;
    private final int posY;
    private final int width;
    private final int height;
    private final Label label;
    private final List<GuiCheckBox> buttons = new ArrayList<>();

    public RightClickMenu(Label label, int posX, int posY) {
        this.label = label;

        buttons.add(new GuiCheckBox(10, 0, 0, "Enabled", label.isEnabled));
        label.configCategory.forEach((name, value) -> {
            if (!name.equals("Enabled") && !name.equals("used") && value.isBooleanValue()) {
                buttons.add(new GuiCheckBox(10, 0, 0, name, value.getBoolean()));
            }
        });

        int width = 0;
        int height = 5;
        for (GuiButton button : buttons) {
            if (button.getButtonWidth() > width) {
                width = button.getButtonWidth();
            }
            height += button.height + 3;
        }
        this.width = width + 8;
        this.height = height;

        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        this.posX = Math.min(Math.max(posX, 0), resolution.getScaledWidth() - this.width);
        this.posY = Math.min(Math.max(posY, 0), resolution.getScaledHeight() - this.height);

        int heightOffset = 4;
        for (GuiButton button : buttons) {
            button.xPosition = this.posX + 4;
            button.yPosition = this.posY + heightOffset;
            heightOffset += button.height + 3;
        }
    }

    public void drawMenu(int mouseX, int mouseY) {
        drawMenuBackground(posX, posY, posX + width, posY + height);
        for (GuiButton button : buttons) {
            button.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY) {
        for (GuiCheckBox button : buttons) {
            if (button.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)) {
                button.playPressSound(Minecraft.getMinecraft().getSoundHandler());
                label.saveLabelConfig();
                label.configCategory.get(button.displayString).set(button.isChecked());
                label.loadLabelConfig();
                return true;
            }
        }
        return false;
    }

    private void drawMenuBackground(int startX, int startY, int endX, int endY) {
        final int backgroundColor = 0xF0100010;
        final int borderColorStart = 0x505000FF;
        final int borderColorEnd = 0x5028007F;
        // Center
        drawRect(startX, startY + 1, endX, endY - 1, backgroundColor);
        // Out Border
        drawRect(startX + 1, startY, endX - 1, startY + 1, backgroundColor);
        drawRect(startX + 1, endY, endX - 1, endY - 1, backgroundColor);
        // Gradient Border
        drawRect(startX + 1, startY + 1, endX - 1, startY + 2, borderColorStart);
        drawRect(startX + 1, endY - 1, endX - 1, endY - 2, borderColorEnd);
        drawGradientRect(startX + 1, startY + 2, startX + 2, endY - 2, borderColorStart, borderColorEnd);
        drawGradientRect(endX - 2, startY + 2, endX - 1, endY - 2, borderColorStart, borderColorEnd);
    }
}
