package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.labels.TextLabel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class LabelLastInput extends TextLabel {
    private boolean accurateKeyNames;

    public LabelLastInput() {
        super("Last Input");
        accurateKeyNames = addCustomConfig("Accurate Key Names", false);
    }

    @Override
    protected String getLabelText() {
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        if (accurateKeyNames) {
            return (gameSettings.keyBindForward.isKeyDown() ? GameSettings.getKeyDisplayString(gameSettings.keyBindForward.getKeyCode()) : "")
                    + (gameSettings.keyBindLeft.isKeyDown() ? GameSettings.getKeyDisplayString(gameSettings.keyBindLeft.getKeyCode()) : "")
                    + (gameSettings.keyBindBack.isKeyDown() ? GameSettings.getKeyDisplayString(gameSettings.keyBindBack.getKeyCode()) : "")
                    + (gameSettings.keyBindRight.isKeyDown() ? GameSettings.getKeyDisplayString(gameSettings.keyBindRight.getKeyCode()) : "");
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
