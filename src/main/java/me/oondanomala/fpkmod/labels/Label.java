package me.oondanomala.fpkmod.labels;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.util.GuiUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

/**
 * Implement this class to add a new label.
 * Don't forget to register it in {@link LabelManager}'s constructor!
 */
public abstract class Label {
    public final String name;
    public final ConfigCategory configCategory;
    public boolean isUsed;
    public boolean isEnabled;
    public int posX;
    public int posY;

    /**
     * Constructs a new label which is hidden by default.
     *
     * @param name The name of the label
     */
    public Label(String name) {
        this(name, 0, 0, false);
    }

    /**
     * Constructs a new label that will be shown by default at the provided position.
     *
     * @param name        The name of the label
     * @param defaultPosX The default X position.
     * @param defaultPosY The default Y position.
     */
    public Label(String name, int defaultPosX, int defaultPosY) {
        this(name, defaultPosX, defaultPosY, true);
    }

    private Label(String name, int defaultPosX, int defaultPosY, boolean defaultUsed) {
        this.name = name;
        this.configCategory = FPKMod.config.configuration.getCategory("labels" + Configuration.CATEGORY_SPLITTER + name.toLowerCase());

        String category = configCategory.getQualifiedName();
        FPKMod.config.configuration.get(category, "x", defaultPosX);
        FPKMod.config.configuration.get(category, "y", defaultPosY);
        FPKMod.config.configuration.get(category, "used", defaultUsed);
        FPKMod.config.configuration.get(category, "Enabled", true);
        FPKMod.config.saveConfig();

        loadLabelConfig();
    }

    /**
     * Draws the label to the screen, if it is enabled and used.
     */
    public void drawLabel() {
        drawLabel(false);
    }

    /**
     * Draws the label to the screen.
     * If {@code showDisabled} is true it will be rendered as strikethrough and gray when disabled,
     * otherwise it won't be rendered at all.
     *
     * @param showDisabled Whether to draw the label if it's disabled.
     */
    public void drawLabel(boolean showDisabled) {
        if (isUsed && isEnabled) {
            drawLabel(TextUtil.assembleText(name, getLabelText(), ": "));
        } else if (showDisabled) {
            drawLabel(TextUtil.formatAsDisabled(name + ": " + getLabelText()));
        }
    }

    protected void drawLabel(String labelText) {
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(labelText, posX, posY, -1);
    }

    public void drawSelectionBox(int alpha) {
        Gui.drawRect(
                posX,
                posY,
                posX + getWidth(),
                posY + getHeight(),
                GuiUtil.getRGBFromColorCode(FPKMod.config.color1.charAt(1), alpha)
        );
    }

    public void loadLabelConfig() {
        posX = configCategory.get("x").getInt();
        posY = configCategory.get("y").getInt();
        isUsed = configCategory.get("used").getBoolean();
        isEnabled = configCategory.get("Enabled").getBoolean();
    }

    public void saveLabelConfig() {
        configCategory.get("x").set(posX);
        configCategory.get("y").set(posY);
        configCategory.get("used").set(isUsed);
        configCategory.get("Enabled").set(isEnabled);
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

    /**
     * Moves the label to the specified position, without going outside the screen.
     *
     * @param posX The X position to move to
     * @param posY The Y position to move to
     */
    public void move(int posX, int posY) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        this.posX = Math.min(Math.max(posX, 0), resolution.getScaledWidth() - getWidth());
        this.posY = Math.min(Math.max(posY, 0), resolution.getScaledHeight() - getHeight());
    }

    /**
     * Gets the width of the label.
     */
    public int getWidth() {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(TextUtil.assembleText(name, getLabelText(), ": "));
    }

    /**
     * Gets the height of the label.
     */
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
