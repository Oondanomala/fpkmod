package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.util.GuiUtil;
import me.oondanomala.fpkmod.util.KeyBindUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;

import java.awt.Color;

public class KeystrokeKey {
    private static final int BACKGROUND_COLOR = new Color(25, 25, 25, 90).getRGB();
    private static final int BACKGROUND_PRESSED_COLOR = new Color(255, 255, 255, 155).getRGB();
    private static final int TEXT_COLOR = Color.WHITE.getRGB();
    private static final int TEXT_PRESSED_COLOR = Color.BLACK.getRGB();

    private final KeyBinding key;
    public final int width;
    public final int height;
    public final int offsetX;
    public final int offsetY;

    public KeystrokeKey(KeyBinding key, int size, int offsetX, int offsetY) {
        this(key, size, size, offsetX, offsetY);
    }

    public KeystrokeKey(KeyBinding key, int width, int height, int offsetX, int offsetY) {
        this.key = key;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public String getKeyName() { // private?
        return KeyBindUtil.getKeybindName(key);
    }

    public void draw(int posX, int posY, boolean isEnabled) {
        // Background
        Gui.drawRect(posX, posY, posX + width, posY + height, key.isKeyDown() ? BACKGROUND_PRESSED_COLOR : BACKGROUND_COLOR);

        // Text
        String formattedName;
        if (isEnabled) {
            formattedName = getKeyName();
        } else if (key.isKeyDown()) {
            formattedName = TextUtil.formatAsDisabled(getKeyName(), EnumChatFormatting.DARK_GRAY);
        } else {
            formattedName = TextUtil.formatAsDisabled(getKeyName());
        }

        GuiUtil.drawCenteredString(formattedName, posX, posY, width, height, key.isKeyDown() ? TEXT_PRESSED_COLOR : TEXT_COLOR);
    }
}
