package me.oondanomala.fpkmod.gui;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.labels.LabelManager;
import me.oondanomala.fpkmod.util.GuiUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.List;

public class LabelGUI extends GuiScreen {
    private Label selectedLabel;
    RightClickMenu rightClickMenu;
    private UnusedLabelList unusedLabelList;
    private boolean unusedListOpen; // TODO: Save this value when closing, like MPK does.
    private boolean isClickingLabel;
    private int clickX;
    private int clickY;

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.add(new GuiButtonExt(0, this.width - 24, 6, 18, 18, "x"));
        buttonList.add(new GuiButtonExt(1, this.width - 24, 29, 18, 18, "+"));
        unusedLabelList = new UnusedLabelList(this);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        LabelManager.instance.saveLabelsConfig();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        for (Label label : LabelManager.instance.labels) {
            if (label == selectedLabel) {
                if (isClickingLabel) continue;
                selectedLabel.drawSelectionBox(100);
            }
            label.draw(true);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (unusedListOpen) {
            unusedLabelList.drawScreen(mouseX, mouseY, partialTicks);
        }

        if (isClickingLabel) {
            selectedLabel.drawSelectionBox(150);
            selectedLabel.draw(true);
        }
        if (rightClickMenu != null) {
            rightClickMenu.drawMenu(mouseX, mouseY);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(null);
        } else if (button.id == 1) {
            unusedListOpen = !unusedListOpen;
            button.displayString = unusedListOpen ? "-" : "+";
        }
        // Hack: Act like pressing the buttons doesn't select labels.
        // Sadly this means pressing them will deselect the currently selected label.
        isClickingLabel = false;
        selectedLabel = null;
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
                    selectedLabel.isUsed = false;
                    selectedLabel = null;
                    isClickingLabel = false;
                    unusedLabelList.rebuildEntryList();
                    break;
            }

            if (isCtrlKeyDown()) {
                horizontalOffset *= 10;
                verticalOffset *= fontRendererObj.FONT_HEIGHT;
            }

            if ((horizontalOffset != 0 || verticalOffset != 0) && !isClickingLabel) {
                selectedLabel.move(selectedLabel.posX + horizontalOffset, selectedLabel.posY + verticalOffset);
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (rightClickMenu != null && rightClickMenu.mouseClicked(mouseX, mouseY) ||
            unusedListOpen && (unusedLabelList.mouseClicked(mouseX, mouseY, mouseButton) || unusedLabelList.selectedEntry != null)) {
            return;
        }
        if (mouseButton == GuiUtil.MOUSE_LEFT) {
            selectedLabel = getHoveredLabel(mouseX, mouseY);
            rightClickMenu = null;
            if (selectedLabel != null) {
                isClickingLabel = true;
                clickX = mouseX - selectedLabel.posX;
                clickY = mouseY - selectedLabel.posY;
            }
        } else if (mouseButton == GuiUtil.MOUSE_RIGHT) {
            Label hoveredLabel = getHoveredLabel(mouseX, mouseY);
            if (hoveredLabel == null) {
                rightClickMenu = null;
            } else {
                selectedLabel = hoveredLabel;
                isClickingLabel = false;
                rightClickMenu = new RightClickMenu(hoveredLabel, mouseX, mouseY);
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (unusedListOpen) {
            unusedLabelList.mouseReleased(mouseX, mouseY, state);
            if (unusedLabelList.isMouseInList(mouseX, mouseY) && isClickingLabel) {
                selectedLabel.isUsed = false;
                selectedLabel = null;
                unusedLabelList.rebuildEntryList();
            }
        }
        isClickingLabel = false;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (isClickingLabel && rightClickMenu == null) {
            selectedLabel.move(mouseX - clickX, mouseY - clickY);
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        // FIXME: Can drag the list trough the right click menu, cant really fix without some awful code
        if (unusedListOpen && !isClickingLabel && unusedLabelList.selectedEntry == null) {
            unusedLabelList.handleMouseInput();
        }
    }

    private Label getHoveredLabel(int mouseX, int mouseY) {
        List<Label> labels = LabelManager.instance.labels;
        for (int i = labels.size() - 1; i >= 0; i--) {
            Label label = labels.get(i);
            if (label.isUsed && mouseX >= label.posX && mouseY >= label.posY && mouseX < label.posX + label.getWidth() && mouseY < label.posY + label.getHeight()) {
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
