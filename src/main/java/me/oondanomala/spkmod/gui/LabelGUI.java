package me.oondanomala.spkmod.gui;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.labels.LabelManager;
import me.oondanomala.spkmod.util.GuiUtil;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;

public class LabelGUI extends GuiScreen {
    private Label selectedLabel;
    private boolean isClickingLabel;
    private int clickX = 0;
    private int clickY = 0;

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        // TODO: save positions to config here
        super.onGuiClosed();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        for (Label label : LabelManager.instance.labels) {
            // TODO: make it crossed out when its disabled
            if (label == selectedLabel) {
                if (isClickingLabel) continue;
                selectedLabel.drawSelectionBox(100);
            }
            label.drawLabel();
        }

        if (selectedLabel != null && isClickingLabel) {
            selectedLabel.drawSelectionBox(150);
            selectedLabel.drawLabel();
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (selectedLabel != null) {
            int horizontalOffset = 0;
            int verticalOffset = 0;

            switch (keyCode) {
                case Keyboard.KEY_UP:
                    verticalOffset = -1;
                    break;
                case Keyboard.KEY_DOWN:
                    verticalOffset = 1;
                    break;
                case Keyboard.KEY_RIGHT:
                    horizontalOffset = 1;
                    break;
                case Keyboard.KEY_LEFT:
                    horizontalOffset = -1;
                    break;
                case Keyboard.KEY_BACK:
                case Keyboard.KEY_DELETE:
                    // TODO: Remove label
                    break;
            }

            if (isCtrlKeyDown()) {
                horizontalOffset *= 10;
                verticalOffset *= selectedLabel.getHeight();
            }

            if ((horizontalOffset != 0 || verticalOffset != 0) && !isClickingLabel) {
                selectedLabel.move(selectedLabel.posX + horizontalOffset, selectedLabel.posY + verticalOffset);
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == GuiUtil.MOUSE_LEFT) {
            selectedLabel = getHoveredLabel(mouseX, mouseY);
            if (selectedLabel != null) {
                isClickingLabel = true;
                clickX = mouseX - selectedLabel.posX;
                clickY = mouseY - selectedLabel.posY;
            }
        } else if (mouseButton == GuiUtil.MOUSE_RIGHT) {
            Label hoveredLabel = getHoveredLabel(mouseX, mouseY);
            if (hoveredLabel != null) {
                selectedLabel = hoveredLabel;
                // TODO: a small menu to let you disable each label manually
                //  or maybe just disable them on right click without a menu?
                //this.buttonList.add(a = new GuiCheckBox(0, mouseX, mouseY, "Enabled", selectedLabel.isEnabled));
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        isClickingLabel = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (clickedMouseButton == GuiUtil.MOUSE_LEFT && selectedLabel != null) {
            selectedLabel.move(mouseX - clickX, mouseY - clickY);
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    private Label getHoveredLabel(int mouseX, int mouseY) {
        List<Label> labels = LabelManager.instance.labels;
        for (int i = labels.size() - 1; i >= 0; i--) {
            Label label = labels.get(i);
            if (mouseX >= label.posX && mouseY >= label.posY && mouseX < label.posX + label.getWidth() && mouseY < label.posY + label.getHeight()) {
                return label;
            }
        }
        return null;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
// TODO: use scaled coordinates (based on screen size) so that it doesn't matter if you resize the game
