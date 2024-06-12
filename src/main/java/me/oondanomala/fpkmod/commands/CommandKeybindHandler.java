package me.oondanomala.fpkmod.commands;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.List;
// Move to a KeybindUtil?
public class CommandKeybindHandler {
    // Hack! §r makes the category show below vanilla ones
    public static final String KEYBIND_CATEGORY = "§rFPK Mod";

    public static void registerKeybinds(List<FPKSubCommand> subCommands) {
        for (FPKSubCommand subCommand : subCommands) {
            if (subCommand.keybind != null) {
                ClientRegistry.registerKeyBinding(subCommand.keybind);
            }
        }
    }

    private void handleInput() {
        for (FPKSubCommand subCommand : FPKMainCommand.instance.subCommandList) {
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
