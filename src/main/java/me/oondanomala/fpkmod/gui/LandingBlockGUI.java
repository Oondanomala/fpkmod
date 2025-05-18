package me.oondanomala.fpkmod.gui;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.landingblock.LandingBlock;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiButtonExt;

// TODO: Multiple landing block management
public class LandingBlockGUI extends GuiScreen {
    private GuiButton landModeButton;
    private GuiButton axisButton;
    private GuiButton renderLBButton;
    private GuiButton renderCondButton;
    private GuiButton recalculateWallsButton;

    @Override
    public void initGui() {
        // UI very much not done yet!
        final int buttonWidth = 140;
        final int buttonHeight = 20;
        final int smallDivider = 4;
        final int bigDivider = 6;
        int y = 10;
        int x = this.width - buttonWidth - 10;
        buttonList.add(landModeButton = new GuiButtonExt(0, x, y, buttonWidth, buttonHeight, "Land Mode: Land"));
        y += buttonHeight + smallDivider;
        buttonList.add(axisButton = new GuiButtonExt(1, x, y, buttonWidth, buttonHeight, "Axis: BOTH"));
        y += buttonHeight + bigDivider;
        buttonList.add(renderLBButton = new GuiButtonExt(2, x, y, buttonWidth, buttonHeight, "Render LB: ?"));
        y += buttonHeight + smallDivider;
        buttonList.add(renderCondButton = new GuiButtonExt(3, x, y, buttonWidth, buttonHeight, "Render Cond: ?"));
        y += buttonHeight + bigDivider;
        buttonList.add(recalculateWallsButton = new GuiButtonExt(4, x, y, buttonWidth, buttonHeight, "Recalculate Walls"));

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
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        LandingBlock landingBlock = LBManager.getSelectedLandingBlock();
        if (landingBlock != null) {
            if (button.id == 0) {
                // TODO: Maybe clear pb when you do this? (config option?)
                landingBlock.cycleLandMode();
            } else if (button.id == 1) {
                landingBlock.cycleAxis();
            } else if (button.id == 4) {
                landingBlock.recalculateWalls();
            }
        }
        if (button.id == 2) {
            FPKMod.config.renderLandingBox = !FPKMod.config.renderLandingBox;
        } else if (button.id == 3) {
            FPKMod.config.renderCondBox = !FPKMod.config.renderCondBox;
        }

        updateButtons();
    }

    private void updateButtons() {
        LandingBlock landingBlock = LBManager.getSelectedLandingBlock();
        if (landingBlock != null) {
            landModeButton.displayString = "Land Mode: " + landingBlock.landMode;
            axisButton.displayString = "Axis: " + landingBlock.landAxis;
        } else {
            landModeButton.enabled = false;
            axisButton.enabled = false;
            recalculateWallsButton.enabled = false;
        }

        renderLBButton.displayString = "Render LB: " + (FPKMod.config.renderLandingBox ? "ON" : "OFF");
        renderCondButton.displayString = "Render Cond: " + (FPKMod.config.renderCondBox ? "ON" : "OFF");
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
