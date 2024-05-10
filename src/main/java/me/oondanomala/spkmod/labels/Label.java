package me.oondanomala.spkmod.labels;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.util.GuiUtil;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public abstract class Label {
    public final String labelName;
    public boolean isUsed;
    public boolean isEnabled;
    public int posX;
    public int posY;

    public Label(String labelName) {
        this(labelName, 0, 0, false);
    }

    public Label(String labelName, int defaultPosX, int defaultPosY) {
        this(labelName, defaultPosX, defaultPosY, true);
    }

    private Label(String labelName, int defaultPosX, int defaultPosY, boolean defaultUsed) {
        this.labelName = labelName;

        String category = "labels" + Configuration.CATEGORY_SPLITTER + labelName.toLowerCase();
        SPKMod.config.configuration.get(category, "x", defaultPosX);
        SPKMod.config.configuration.get(category, "y", defaultPosY);
        SPKMod.config.configuration.get(category, "used", defaultUsed);
        SPKMod.config.configuration.get(category, "enabled", true);
        SPKMod.config.saveConfig();

        loadLabelConfig();
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

    public void loadLabelConfig() {
        ConfigCategory category = SPKMod.config.configuration.getCategory("labels" + Configuration.CATEGORY_SPLITTER + labelName.toLowerCase());

        posX = category.get("x").getInt();
        posY = category.get("y").getInt();
        isUsed = category.get("used").getBoolean();
        isEnabled = category.get("enabled").getBoolean();
    }

    public void saveLabelConfig() {
        ConfigCategory category = SPKMod.config.configuration.getCategory("labels" + Configuration.CATEGORY_SPLITTER + labelName.toLowerCase());

        category.get("x").set(posX);
        category.get("y").set(posY);
        category.get("used").set(isUsed);
        category.get("enabled").set(isEnabled);
        SPKMod.config.saveConfig();
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
