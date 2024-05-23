package me.oondanomala.spkmod.labels.input;

import me.oondanomala.spkmod.labels.Label;
import me.oondanomala.spkmod.movement.PlayerMovementHandler;
import me.oondanomala.spkmod.movement.PlayerState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Keyboard;

public class LabelJumpInput extends Label {
    private boolean accurateKeyNames;

    public LabelJumpInput() {
        super("Jump Input");
        accurateKeyNames = addCustomConfig("Accurate Key Names", false);
    }

    @Override
    protected String getLabelText() {
        PlayerState jumpPosition = PlayerMovementHandler.lastJumpPosition;
        String jumpInput;

        if (accurateKeyNames) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            jumpInput = (jumpPosition.keyForward ? Keyboard.getKeyName(gameSettings.keyBindForward.getKeyCode()) : "") +
                    (jumpPosition.keyLeft ? Keyboard.getKeyName(gameSettings.keyBindLeft.getKeyCode()) : "") +
                    (jumpPosition.keyBackward ? Keyboard.getKeyName(gameSettings.keyBindBack.getKeyCode()) : "") +
                    (jumpPosition.keyRight ? Keyboard.getKeyName(gameSettings.keyBindRight.getKeyCode()) : "");
        } else {
            jumpInput = (jumpPosition.keyForward ? "W" : "") +
                    (jumpPosition.keyLeft ? "A" : "") +
                    (jumpPosition.keyBackward ? "S" : "") +
                    (jumpPosition.keyRight ? "D" : "");
        }

        return jumpInput.isEmpty() ? "None" : jumpInput;
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
