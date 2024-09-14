package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.labels.Label;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

import java.util.ArrayList;
import java.util.List;

public class LabelKeystrokes extends Label {
    private final List<KeystrokeKey> keys = new ArrayList<>();

    public LabelKeystrokes() {
        super("keystrokes", "Keystrokes");

        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        keys.add(new KeystrokeKey(gameSettings.keyBindForward, 25, 30, 0));
        keys.add(new KeystrokeKey(gameSettings.keyBindLeft, 25, 0, 30));
        keys.add(new KeystrokeKey(gameSettings.keyBindBack, 25, 30, 30));
        keys.add(new KeystrokeKey(gameSettings.keyBindRight, 25, 60, 30));
        keys.add(new KeystrokeKey(gameSettings.keyBindSprint, 40, 25, 0, 60));
        keys.add(new KeystrokeKey(gameSettings.keyBindSneak, 40, 25, 45, 60));
        keys.add(new KeystrokeKey(gameSettings.keyBindJump, 85, 20, 0, 90));
    }

    @Override
    public int getWidth() {
        int width = 0;
        for (KeystrokeKey key : keys) {
            width = Math.max(width, key.width + key.offsetX);
        }
        return width;
    }

    @Override
    public int getHeight() {
        int height = 0;
        for (KeystrokeKey key : keys) {
            height = Math.max(height, key.height + key.offsetY);
        }
        return height;
    }

    @Override
    public void draw(boolean showDisabled) {
        if (isUsed) {
            if (isEnabled) {
                drawKeys(true);
            } else if (showDisabled) {
                drawKeys(false);
            }
        }
    }

    private void drawKeys(boolean isEnabled) {
        for (KeystrokeKey key : keys) {
            key.draw(posX + key.offsetX, posY + key.offsetY, isEnabled);
        }
    }
}
