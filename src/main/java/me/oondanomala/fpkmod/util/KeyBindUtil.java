package me.oondanomala.fpkmod.util;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

import java.util.List;

public final class KeyBindUtil {
    // Hack! §r makes the category show below vanilla ones
    public static final String KEYBIND_CATEGORY = "§rFPK Mod";

    public static String getKeybindName(KeyBinding keybind) {
        switch (keybind.getKeyCode()) {
            case -100:
                return "LMB";
            case -99:
                return "RMB";
            case -98:
                return "WMB";
            case Keyboard.KEY_NONE:
                return I18n.format(keybind.getKeyDescription());
            case Keyboard.KEY_LCONTROL:
                return "LCTRL";
            case Keyboard.KEY_RCONTROL:
                return "RCTRL";
        }

        return GameSettings.getKeyDisplayString(keybind.getKeyCode());
    }

    public static void registerKeybinds(List<FPKSubCommand> subCommands) {
        for (FPKSubCommand subCommand : subCommands) {
            if (subCommand.keybind != null) {
                ClientRegistry.registerKeyBinding(subCommand.keybind);
            }
        }
    }
}
