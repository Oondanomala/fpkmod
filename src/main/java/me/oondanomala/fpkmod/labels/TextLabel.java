package me.oondanomala.fpkmod.labels;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;

/**
 * Implement this class to add a new text based label.
 * Don't forget to register it in {@link LabelManager}'s constructor!
 */
public abstract class TextLabel extends Label {
    public final String name;

    /**
     * Constructs a new text label which is hidden by default.
     *
     * @param name The name of the label
     */
    public TextLabel(String name) {
        this(name, 0, 0, false);
    }

    /**
     * Constructs a new text label that will be shown by default at the provided position.
     *
     * @param name        The name of the label
     * @param defaultPosX The default X position.
     * @param defaultPosY The default Y position.
     */
    public TextLabel(String name, int defaultPosX, int defaultPosY) {
        this(name, defaultPosX, defaultPosY, true);
    }

    private TextLabel(String name, int defaultPosX, int defaultPosY, boolean defaultUsed) {
        // TODO: Change the id to something else
        super(name.toLowerCase(), defaultPosX, defaultPosY, defaultUsed);
        this.name = name;
    }

    /**
     * {@inheritDoc}
     * If {@code showDisabled} is <tt>true</tt> it will be rendered with strikethrough and gray text when disabled,
     * otherwise it won't be rendered at all.
     */
    @Override
    public void draw(boolean showDisabled) {
        if (isUsed && isEnabled) {
            drawLabelText(TextUtil.assembleText(name, getLabelText(), ": "));
        } else if (showDisabled) {
            drawLabelText(TextUtil.formatAsDisabled(name + ": " + getLabelText()));
        }
    }

    protected void drawLabelText(String labelText) {
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(labelText, posX, posY, -1);
    }

    /**
     * Don't forget to also override {@link #loadLabelConfig()} and {@link #saveLabelConfig()} to save the configs!
     *
     * @return The config value if it already exists, otherwise the default value
     * @see #getCustomConfig(String)
     * @see #setCustomConfig(String, boolean)
     */
    protected boolean addCustomConfig(String name, boolean defaultValue) {
        return FPKMod.config.configuration.get(configCategory.getQualifiedName(), name, defaultValue).getBoolean();
    }

    protected boolean getCustomConfig(String name) {
        if (configCategory.get(name) != null) {
            return configCategory.get(name).getBoolean();
        }
        return false;
    }

    protected void setCustomConfig(String name, boolean value) {
        configCategory.get(name).set(value);
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
     * @see PlayerMovementHandler
     * @see TextUtil#formatDouble(double)
     * @see TextUtil#formatAngle(float)
     */
    protected abstract String getLabelText();
}
