package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.util.GuiUtil;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public abstract class Label {
    public final String labelName;
    public boolean isUsed = true;
    public boolean isEnabled = true;
    public int posX = 0;
    public int posY = 0;

    protected Label(String labelName) {
        this.labelName = labelName;
    }

    public void drawLabel() {
        if (isUsed && isEnabled) {
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(TextUtil.assembleText(labelName, getLabelText(), ": "), posX, posY, -1);
        }
    }

    public void drawSelectionBox(int alpha) {
        Gui.drawRect(
                posX,
                posY,
                posX + getWidth(),
                posY + getHeight(),
                GuiUtil.getRGBFromColorCode(SPKMod.config.color1.charAt(1), alpha)
        );
    }

    public void move(int posX, int posY) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        this.posX = Math.min(Math.max(posX, 0), resolution.getScaledWidth() - getWidth());
        this.posY = Math.min(Math.max(posY, 0), resolution.getScaledHeight() - getHeight());
    }

    public int getWidth() {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(TextUtil.assembleText(labelName, getLabelText(), ": "));
    }

    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    protected abstract String getLabelText();
}
