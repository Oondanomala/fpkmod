package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.labels.Label;
import me.oondanomala.fpkmod.movement.PlayerMovementHandler;
import me.oondanomala.fpkmod.movement.PlayerState;
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
        PlayerState jumpState = PlayerMovementHandler.lastJumpState;
        String jumpInput;

        if (accurateKeyNames) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            jumpInput = (jumpState.keyForward ? Keyboard.getKeyName(gameSettings.keyBindForward.getKeyCode()) : "") +
                    (jumpState.keyLeft ? Keyboard.getKeyName(gameSettings.keyBindLeft.getKeyCode()) : "") +
                    (jumpState.keyBackward ? Keyboard.getKeyName(gameSettings.keyBindBack.getKeyCode()) : "") +
                    (jumpState.keyRight ? Keyboard.getKeyName(gameSettings.keyBindRight.getKeyCode()) : "");
        } else {
            jumpInput = (jumpState.keyForward ? "W" : "") +
                    (jumpState.keyLeft ? "A" : "") +
                    (jumpState.keyBackward ? "S" : "") +
                    (jumpState.keyRight ? "D" : "");
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
