package me.oondanomala.fpkmod.gui;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.util.GuiUtil;
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
        GuiUtil.drawTooltipBackground(posX, posY, posX + width, posY + height);
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
}
