package me.oondanomala.fpkmod.labels;

import me.oondanomala.fpkmod.movement.PlayerTickHandler;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

/**
 * Implement this class to add a new text based label.
 * Don't forget to register it in {@link LabelManager}'s constructor!
 */
public abstract class TextLabel extends Label {
    /**
     * Constructs a new text label which is hidden by default.
     *
     * @param name The name of the label
     */
    public TextLabel(String name) {
        // TODO: Change the id to something else
        super(name.toLowerCase(), name, 0, 0, false);
    }

    /**
     * Constructs a new text label that will be shown by default at the provided position.
     *
     * @param name        The name of the label
     * @param defaultPosX The default X position.
     * @param defaultPosY The default Y position.
     */
    public TextLabel(String name, int defaultPosX, int defaultPosY) {
        // TODO: Change the id to something else
        super(name.toLowerCase(), name, defaultPosX, defaultPosY, true);
    }

    /**
     * {@inheritDoc}
     * If {@code showDisabled} is <tt>true</tt> it will be rendered with strikethrough and gray text when disabled,
     * otherwise it won't be rendered at all.
     */
    @Override
    public void draw(boolean showDisabled) {
        if (isUsed) {
            if (isEnabled) {
                drawLabelText(TextUtil.assembleText(name, getLabelText(), ": "));
            } else if (showDisabled) {
                drawLabelText(TextUtil.formatAsDisabled(name + ": " + getLabelText()));
            }
        }
    }

    protected void drawLabelText(String labelText) {
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(labelText, posX, posY, -1);
    }

    @Override
    public int getWidth() {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(TextUtil.assembleText(name, getLabelText(), ": "));
    }

    @Override
    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    /**
     * Called every frame.
     *
     * @return The label text that will be shown next to the label name, formatted as {@code Name: Text}.
     * @see PlayerTickHandler
     * @see TextUtil#formatDouble(double)
     * @see TextUtil#formatAngle(float)
     */
    protected abstract String getLabelText();
}
