package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.labels.Label;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

import java.util.ArrayList;
import java.util.List;

public class LabelKeystrokes extends Label {
    private final List<KeystrokeKey> keys = new ArrayList<>();
    private int width;
    private int height;

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
        calcSize();
    }

    private void calcSize() {
        int width = 0;
        int height = 0;
        for (KeystrokeKey key : keys) {
            width = Math.max(width, key.width + key.offsetX);
            height = Math.max(height, key.height + key.offsetY);
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void drawLabel(boolean drawEnabled) {
        for (KeystrokeKey key : keys) {
            key.draw(posX + key.offsetX, posY + key.offsetY, drawEnabled);
        }
    }
}
