package me.oondanomala.spkmod.labels.input;

import me.oondanomala.spkmod.labels.Label;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Keyboard;

public class LabelLastInput extends Label {
    private boolean accurateKeyNames;

    public LabelLastInput() {
        super("Last Input");
        accurateKeyNames = addCustomConfig("Accurate Key Names", false);
    }

    @Override
    protected String getLabelText() {
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        if (accurateKeyNames) {
            return (gameSettings.keyBindForward.isKeyDown() ? Keyboard.getKeyName(gameSettings.keyBindForward.getKeyCode()) : "")
                    + (gameSettings.keyBindLeft.isKeyDown() ? Keyboard.getKeyName(gameSettings.keyBindLeft.getKeyCode()) : "")
                    + (gameSettings.keyBindBack.isKeyDown() ? Keyboard.getKeyName(gameSettings.keyBindBack.getKeyCode()) : "")
                    + (gameSettings.keyBindRight.isKeyDown() ? Keyboard.getKeyName(gameSettings.keyBindRight.getKeyCode()) : "");
        }
        return (gameSettings.keyBindForward.isKeyDown() ? "W" : "")
                + (gameSettings.keyBindLeft.isKeyDown() ? "A" : "")
                + (gameSettings.keyBindBack.isKeyDown() ? "S" : "")
                + (gameSettings.keyBindRight.isKeyDown() ? "D" : "");
    }

    @Override
    public void loadLabelConfig() {
        super.loadLabelConfig();
        accurateKeyNames = getCustomConfig("Accurate Key Names");
    }

    @Override
    public void saveLabelConfig() {
        super.saveLabelConfig();
        setCustomConfig("Accurate Key Names", accurateKeyNames);
    }
}
