package me.oondanomala.fpkmod.labels;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Generic class for labels. You're probably looking for {@link TextLabel}.
 */
public abstract class Label {
    private final ConfigCategory configCategory;
    protected final String id;
    public final String name;
    public boolean isUsed;
    public boolean isEnabled;
    public int posX;
    public int posY;

    protected Label(String id, String name) {
        this(id, name, 0, 0, false);
    }

    protected Label(String id, String name, int defaultPosX, int defaultPosY, boolean defaultUsed) {
        this.id = id;
        this.name = name;
        configCategory = FPKMod.config.configuration.getCategory("labels" + Configuration.CATEGORY_SPLITTER + id);

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
    public void draw() {
        draw(false);
    }

    /**
     * Draws the label to the screen, if it is used.
     * <p>
     * Subclasses should rarely need to override this,
     * and instead should draw themselves in {@link #drawLabel(boolean)}.
     *
     * @param showDisabled Whether to draw the label if it's disabled.
     */
    public void draw(boolean showDisabled) {
        if (isUsed) {
            if (isEnabled) {
                drawLabel(true);
            } else if (showDisabled) {
                drawLabel(false);
            }
        }
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
     * Adds a custom boolean config to the label and gets its value.
     * It will show up when right-clicking the label in the label GUI.
     * <p>
     * Should be used to load the config value in {@link #loadLabelConfig()}.
     *
     * @param name         The name of the config option, shown in GUIs
     * @param defaultValue The value to use if the config option did not already exist
     * @return The config value if it already exists, otherwise {@code defaultValue}
     */
    protected boolean addCustomConfig(String name, boolean defaultValue) {
        return FPKMod.config.configuration.get(configCategory.getQualifiedName(), name, defaultValue).getBoolean();
    }

    /**
     * Gets a list of all defined custom boolean configs for this label,
     * will be empty if no custom configs are defined.
     *
     * @see #addCustomConfig(String, boolean)
     */
    public List<Property> getCustomConfigs() {
        return configCategory.getOrderedValues().stream().filter(
                prop -> prop.isBooleanValue() && !prop.getName().equals("used") && !prop.getName().equals("Enabled")
        ).collect(Collectors.toList());
    }

    /**
     * Sets a custom label config option to the specified value.
     * The option must already exist.
     *
     * @param name The option name
     * @param value The value to set the option to
     * @see #addCustomConfig(String, boolean)
     */
    public void setCustomConfig(String name, boolean value) {
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
    public abstract int getWidth();

    /**
     * Gets the height of the label.
     */
    public abstract int getHeight();

    /**
     * Draws the label to the screen.
     * Will only be called if the label should be drawn.
     *
     * @param drawEnabled Whether to draw the label in its enabled state or its disabled state
     * @see me.oondanomala.fpkmod.util.TextUtil#formatAsDisabled(String)
     */
    protected abstract void drawLabel(boolean drawEnabled);
}
