package me.oondanomala.fpkmod.gui;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.labels.LabelManager;
import me.oondanomala.fpkmod.util.GuiUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UnusedLabelList extends GuiListExtended {
    private static final int HEADER_HEIGHT = 24;
    private final LabelGUI parent;
    private final List<LabelEntry> labels = new ArrayList<>();
    LabelEntry selectedEntry;
    private int clickX;
    private int clickY;

    public UnusedLabelList(LabelGUI parent) {
        // The "79" is the other gui buttons (47) + button padding (6) + the header height (26).
        super(Minecraft.getMinecraft(), calculateWidth(), parent.height - 79, 79, parent.height, Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * 2);
        this.parent = parent;
        left = parent.width - width;
        right = parent.width;

        rebuildEntryList();
    }

    private static int calculateWidth() {
        int width = 0;
        for (Label label : LabelManager.instance.labels) {
            width = Math.max(width, Minecraft.getMinecraft().fontRendererObj.getStringWidth(label.name));
        }
        // 8 for text padding, 4 for list padding, 6 for the scrollbar and 1 for the scrollbar padding.
        final int padding = 19;
        return Math.max(width + padding, Minecraft.getMinecraft().fontRendererObj.getStringWidth("Unused Labels") + 10);
    }

    public void rebuildEntryList() {
        labels.clear();
        for (Label label : LabelManager.instance.labels) {
            if (!label.isUsed) {
                labels.add(new LabelEntry(label));
            }
        }
        labels.sort(Comparator.comparing(labelEntry -> labelEntry.label.name));
    }

    @Override
    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isMouseInList(mouseX, mouseY) && (mouseButton == GuiUtil.MOUSE_LEFT || mouseButton == GuiUtil.MOUSE_RIGHT)) {
            parent.rightClickMenu = null;
        }

        int index = getSlotIndexFromScreenCoords(mouseX, mouseY);
        if (index >= 0) {
            LabelEntry entry = (LabelEntry) getListEntry(index);
            if (mouseButton == GuiUtil.MOUSE_LEFT) {
                selectedEntry = entry;
                // 2 for list padding, and 4 for text padding.
                clickX = mouseX - left - 6;
                clickY = mouseY - (top + headerPadding + 8 + slotHeight * index - (int) amountScrolled);
            } else if (mouseButton == GuiUtil.MOUSE_RIGHT && selectedEntry == null) {
                parent.rightClickMenu = new RightClickMenu(entry.label, mouseX, mouseY);
            }
        }

        return isMouseInList(mouseX, mouseY);
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == GuiUtil.MOUSE_LEFT) {
            if (selectedEntry != null && !isMouseInList(mouseX, mouseY)) {
                selectedEntry.label.isUsed = true;
                selectedEntry.label.move(mouseX - clickX, mouseY - clickY);
                rebuildEntryList();
            }
            selectedEntry = null;
        }
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (selectedEntry != null) {
            ScaledResolution resolution = new ScaledResolution(mc);
            int clampedX = Math.min(Math.max(mouseX - clickX, 0), resolution.getScaledWidth() - mc.fontRendererObj.getStringWidth(selectedEntry.label.name));
            int clampedY = Math.min(Math.max(mouseY - clickY, 0), resolution.getScaledHeight() - mc.fontRendererObj.FONT_HEIGHT);
            selectedEntry.drawEntryText(clampedX, clampedY);
        }
    }

    @Override
    protected void drawContainerBackground(Tessellator tessellator) {
        GuiUtil.drawTooltipBackground(left - 2, top - HEADER_HEIGHT - 2, right + 2, bottom + 2);
    }

    @Override
    protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
        if (startY == 0) {
            Gui.drawRect(left, top - HEADER_HEIGHT, right, top, Color.BLACK.getRGB());
            GuiUtil.drawCenteredString(EnumChatFormatting.UNDERLINE + "Unused Labels", left, top - HEADER_HEIGHT - 2, width, HEADER_HEIGHT + 2, -1);
        }
    }

    @Override
    public IGuiListEntry getListEntry(int index) {
        return labels.get(index);
    }

    @Override
    protected int getSize() {
        return labels.size();
    }

    @Override
    public boolean isMouseYWithinSlotBounds(int mouseY) {
        return isMouseInList(mouseX, mouseY);
    }

    public boolean isMouseInList(int mouseX, int mouseY) {
        return mouseY >= top - HEADER_HEIGHT && mouseY <= bottom && mouseX >= left && mouseX <= right;
    }

    @Override
    public int getSlotIndexFromScreenCoords(int x, int y) {
        int index = (y - top - headerPadding + (int) amountScrolled - 4) / slotHeight;
        if (x >= left + 4 && x < getScrollBarX() - 2 && y >= top && y <= bottom && index >= 0 && index < getSize()) {
            return index;
        }
        return -1;
    }

    @Override
    protected int getScrollBarX() {
        // Scrollbar width, and one pixel of padding.
        return right - 7;
    }

    @Override
    public int getListWidth() {
        return width;
    }

    private class LabelEntry implements GuiListExtended.IGuiListEntry {
        private final Label label;

        public LabelEntry(Label label) {
            this.label = label;
        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            if (y > top - HEADER_HEIGHT) {
                // 3 for text padding, 6 for the scrollbar and 1 for the scrollbar padding.
                final int endPadding = 10;
                GuiUtil.drawTooltipBackground(x - 1, y - 1, x + listWidth - endPadding, y + slotHeight + 3);
                drawEntryText(x + 4, y + 4);
            }
        }

        private void drawEntryText(int x, int y) {
            String formattedName = label.isEnabled ? FPKMod.config.color1 + label.name : TextUtil.formatAsDisabled(label.name);
            mc.fontRendererObj.drawStringWithShadow(formattedName, x, y, -1);
        }

        @Override
        public boolean mousePressed(int slotIndex, int mouseX, int mouseY, int mouseButton, int x, int y) {
            return false;
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseButton, int relativeX, int relativeY) {
        }

        @Override
        public void setSelected(int index, int x, int y) {
        }
    }
}
