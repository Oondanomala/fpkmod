package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.util.GuiUtil;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public abstract class Label {
    public final String name;
    public final ConfigCategory configCategory;
    public boolean isUsed;
    public boolean isEnabled;
    public int posX;
    public int posY;

    public Label(String name) {
        this(name, 0, 0, false);
    }

    public Label(String name, int defaultPosX, int defaultPosY) {
        this(name, defaultPosX, defaultPosY, true);
    }

    private Label(String name, int defaultPosX, int defaultPosY, boolean defaultUsed) {
        this.name = name;
        this.configCategory = SPKMod.config.configuration.getCategory("labels" + Configuration.CATEGORY_SPLITTER + name.toLowerCase());

        String category = configCategory.getQualifiedName();
        SPKMod.config.configuration.get(category, "x", defaultPosX);
        SPKMod.config.configuration.get(category, "y", defaultPosY);
        SPKMod.config.configuration.get(category, "used", defaultUsed);
        SPKMod.config.configuration.get(category, "Enabled", true);
        SPKMod.config.saveConfig();

        loadLabelConfig();
    }

    public void drawLabel() {
        drawLabel(false);
    }

    public void drawLabel(boolean showDisabled) {
        if (isUsed && isEnabled) {
            drawLabel(TextUtil.assembleText(name, getLabelText(), ": "));
        } else if (showDisabled) {
            drawLabel(EnumChatFormatting.GRAY.toString() + EnumChatFormatting.STRIKETHROUGH + name + ": " + StringUtils.stripControlCodes(getLabelText()));
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
                GuiUtil.getRGBFromColorCode(SPKMod.config.color1.charAt(1), alpha)
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

    protected void addCustomConfig(String name, boolean defaultValue) {
        SPKMod.config.configuration.get(configCategory.getQualifiedName(), name, defaultValue);
    }

    public void move(int posX, int posY) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        this.posX = Math.min(Math.max(posX, 0), resolution.getScaledWidth() - getWidth());
        this.posY = Math.min(Math.max(posY, 0), resolution.getScaledHeight() - getHeight());
    }

    public int getWidth() {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(TextUtil.assembleText(name, getLabelText(), ": "));
    }

    public int getHeight() {
        return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
    }

    protected abstract String getLabelText();
}
