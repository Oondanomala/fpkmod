package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.util.KeyBindUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class LabelLastInput extends TextLabel {
    private boolean accurateKeyNames;

    public LabelLastInput() {
        super("Last Input");
    }

    @Override
    protected String getLabelText() {
        GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
        if (accurateKeyNames) {
            return (gameSettings.keyBindForward.isKeyDown() ? KeyBindUtil.getKeybindName(gameSettings.keyBindForward) : "")
                    + (gameSettings.keyBindLeft.isKeyDown() ? KeyBindUtil.getKeybindName(gameSettings.keyBindLeft) : "")
                    + (gameSettings.keyBindBack.isKeyDown() ? KeyBindUtil.getKeybindName(gameSettings.keyBindBack) : "")
                    + (gameSettings.keyBindRight.isKeyDown() ? KeyBindUtil.getKeybindName(gameSettings.keyBindRight) : "");
        }
        return (gameSettings.keyBindForward.isKeyDown() ? "W" : "")
                + (gameSettings.keyBindLeft.isKeyDown() ? "A" : "")
                + (gameSettings.keyBindBack.isKeyDown() ? "S" : "")
                + (gameSettings.keyBindRight.isKeyDown() ? "D" : "");
    }

    @Override
    public void loadLabelConfig() {
        super.loadLabelConfig();
        accurateKeyNames = addCustomConfig("Accurate Key Names", false);
    }
}
