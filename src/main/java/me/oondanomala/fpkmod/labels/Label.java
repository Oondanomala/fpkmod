package me.oondanomala.fpkmod.labels;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.util.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

/**
 * Generic class for labels. You're probably looking for {@link TextLabel}.
 */
public abstract class Label {
    protected final String id;
    public final ConfigCategory configCategory; // FIXME: This should be protected
    public boolean isUsed;
    public boolean isEnabled;
    public int posX;
    public int posY;

    protected Label(String id) {
        this(id, 0, 0, false);
    }

    protected Label(String id, int defaultPosX, int defaultPosY, boolean defaultUsed) {
        this.id = id;
        configCategory = FPKMod.config.configuration.getCategory("labels" + Configuration.CATEGORY_SPLITTER + id);

        String category = configCategory.getQualifiedName();
        FPKMod.config.configuration.get(category, "x", defaultPosX);
        FPKMod.config.configuration.get(category, "y", defaultPosY);
        // FIXME: This should use defaultUsed, but since there's currently no way to change that, use true
        FPKMod.config.configuration.get(category, "used", true);
        FPKMod.config.configuration.get(category, "enabled", true);
        FPKMod.config.saveConfig();
        loadLabelConfig();
    }

    /**
     * Draws the label to the screen, if it is enabled and used.
     */
    public void draw() {
        draw(false);
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
        isEnabled = configCategory.get("enabled").getBoolean();
    }

    public void saveLabelConfig() {
        configCategory.get("x").set(posX);
        configCategory.get("y").set(posY);
        configCategory.get("used").set(isUsed);
        configCategory.get("enabled").set(isEnabled);
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
     *
     * @param showDisabled Whether to draw the label if it's disabled.
     */
    public abstract void draw(boolean showDisabled);
}
