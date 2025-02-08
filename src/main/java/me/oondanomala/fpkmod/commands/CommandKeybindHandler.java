package me.oondanomala.fpkmod.commands;

import me.oondanomala.fpkmod.FPKMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class CommandKeybindHandler {
    private void handleInput() {
        for (FPKSubCommand subCommand : FPKMod.fpkCommand.subCommands.values()) {
            if (subCommand.keybind != null && subCommand.keybind.isPressed()) {
                subCommand.run(new String[0]);
            }
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        handleInput();
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        handleInput();
    }
}
