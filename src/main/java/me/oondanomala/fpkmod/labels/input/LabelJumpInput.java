package me.oondanomala.fpkmod.labels.input;

import me.oondanomala.fpkmod.labels.TextLabel;
import me.oondanomala.fpkmod.movement.PlayerTickHandler;
import me.oondanomala.fpkmod.movement.PlayerState;
import me.oondanomala.fpkmod.util.KeyBindUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

public class LabelJumpInput extends TextLabel {
    private boolean accurateKeyNames;

    public LabelJumpInput() {
        super("Jump Input");
    }

    @Override
    protected String getLabelText() {
        PlayerState jumpState = PlayerTickHandler.lastJumpState;
        String jumpInput;

        if (accurateKeyNames) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            jumpInput = (jumpState.keyForward ? KeyBindUtil.getKeybindName(gameSettings.keyBindForward) : "") +
                    (jumpState.keyLeft ? KeyBindUtil.getKeybindName(gameSettings.keyBindLeft) : "") +
                    (jumpState.keyBackward ? KeyBindUtil.getKeybindName(gameSettings.keyBindBack) : "") +
                    (jumpState.keyRight ? KeyBindUtil.getKeybindName(gameSettings.keyBindRight) : "");
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
        accurateKeyNames = addCustomConfig("Accurate Key Names", false);
    }
}
