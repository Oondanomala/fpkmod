package me.oondanomala.fpkmod.gui;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.landingblock.LandAxis;
import me.oondanomala.fpkmod.landingblock.LandMode;
import me.oondanomala.fpkmod.landingblock.LandingBlock;
import me.oondanomala.fpkmod.util.GuiUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.HoverChecker;

// TODO: Multiple landing block management
public class LandingBlockGUI extends GuiScreen {
    private EnumButton<LandMode> landModeButton;
    private EnumButton<LandAxis> axisButton;
    private GuiButton renderLBButton;
    private GuiButton renderCondButton;
    private GuiButton recalculateWallsButton;
    private HoverChecker landModeHoverChecker;

    @Override
    public void initGui() {
        // UI very much not done yet!
        final int buttonWidth = 140;
        final int buttonHeight = 20;
        final int smallDivider = 4;
        final int bigDivider = 6;
        int y = 10;
        int x = this.width - buttonWidth - 10;
        buttonList.add(landModeButton = new EnumButton<>(0, x, y, buttonWidth, buttonHeight, "Land Mode:", LandMode.LAND));
        y += buttonHeight + smallDivider;
        buttonList.add(axisButton = new EnumButton<>(1, x, y, buttonWidth, buttonHeight, "Axis:", LandAxis.BOTH));
        y += buttonHeight + bigDivider;
        buttonList.add(renderLBButton = new GuiButtonExt(2, x, y, buttonWidth, buttonHeight, "Render LB: ?"));
        y += buttonHeight + smallDivider;
        buttonList.add(renderCondButton = new GuiButtonExt(3, x, y, buttonWidth, buttonHeight, "Render Cond: ?"));
        y += buttonHeight + bigDivider;
        buttonList.add(recalculateWallsButton = new GuiButtonExt(4, x, y, buttonWidth, buttonHeight, "Recalculate Walls"));
        landModeHoverChecker = new HoverChecker(landModeButton, 300);

        LandingBlock selectedLB = LBManager.getSelectedLandingBlock();
        if (selectedLB != null) {
            landModeButton.setCurrentValue(selectedLB.landMode);
            axisButton.setCurrentValue(selectedLB.landAxis);
        } else {
            landModeButton.enabled = false;
            axisButton.enabled = false;
            recalculateWallsButton.enabled = false;
        }
        updateButtons();
    }

    @Override
    public void onGuiClosed() {
        FPKMod.config.setConfigOption("renderLB", FPKMod.config.renderLandingBox);
        FPKMod.config.setConfigOption("renderCond", FPKMod.config.renderCondBox);
        FPKMod.config.saveConfig();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (landModeHoverChecker.checkHover(mouseX, mouseY, landModeButton.enabled)) {
            final int tooltipStartX = landModeButton.xPosition;
            final int tooltipEndX = landModeButton.xPosition + landModeButton.width;
            final int tooltipY = landModeButton.yPosition + landModeButton.height + 2;
            switch (landModeButton.getCurrentValue()) {
                case LAND:
                    GuiUtil.drawTooltip(
                        "The default landing mode, and what you'll want to use most of the time.\n\nCalculates the offset using the landing tick.",
                        tooltipStartX, tooltipEndX, tooltipY, fontRendererObj
                    );
                    break;
                case ZNEO:
                    GuiUtil.drawTooltip(
                        "Useful for Z-facing neos where there is an X-facing blockage on landing (most of them). This is technical speak for a landing block which has a Z-facing wall next to it that you can slide off of (typically causing an offset of \"-0\").\n\nSimilar to Land, but uses the tick before the land tick for wall offsets on the Z axis.\n\nThis is because X-facing blockages require you to pass them a tick earlier than Z blockages.",
                        tooltipStartX, tooltipEndX, tooltipY, fontRendererObj
                    );
                    break;
                case ENTER:
                    GuiUtil.drawTooltip(
                        "Useful for climbing blocks like ladders or vines.\n\nSimilar to hit, but will consider every tick your Y position is inside the landing block (and is going down) for an offset (instead of only when it goes below it).",
                        tooltipStartX, tooltipEndX, tooltipY, fontRendererObj
                    );
                    break;
                case HIT:
                    GuiUtil.drawTooltip(
                        "Useful for slime bounces.\n\nCalculates the offset using the hit tick (the tick after land tick).",
                        tooltipStartX, tooltipEndX, tooltipY, fontRendererObj
                    );
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        LandingBlock selectedLB = LBManager.getSelectedLandingBlock();
        if (selectedLB != null) {
            if (button.id == landModeButton.id) {
                // TODO: Maybe clear pb when you do this? (config option?)
                selectedLB.setLandMode(landModeButton.getCurrentValue());
            } else if (button.id == axisButton.id) {
                selectedLB.setLandAxis(axisButton.getCurrentValue());
            } else if (button.id == recalculateWallsButton.id) {
                selectedLB.recalculateWalls();
            }
        }
        if (button.id == renderLBButton.id) {
            FPKMod.config.renderLandingBox = !FPKMod.config.renderLandingBox;
        } else if (button.id == renderCondButton.id) {
            FPKMod.config.renderCondBox = !FPKMod.config.renderCondBox;
        }

        updateButtons();
    }

    private void updateButtons() {
        renderLBButton.displayString = "Render LB: " + (FPKMod.config.renderLandingBox ? "ON" : "OFF");
        renderCondButton.displayString = "Render Cond: " + (FPKMod.config.renderCondBox ? "ON" : "OFF");
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
