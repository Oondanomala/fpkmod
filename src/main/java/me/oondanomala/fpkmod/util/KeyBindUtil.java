package me.oondanomala.fpkmod.util;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

import java.util.Collection;

/**
 * Utilities to deal with keybindings.
 */
public final class KeyBindUtil {
    // Hack! §r makes the category show below vanilla ones
    public static final String KEYBIND_CATEGORY = "§rFPK Mod";

    private KeyBindUtil() {
    }

    /**
     * Returns a shorter version of the key name the keybind is bound to than {@link GameSettings#getKeyDisplayString(int)}.
     * If the keybind is unbound, returns its description.
     */
    public static String getKeybindName(KeyBinding keybind) {
        return switch (keybind.getKeyCode()) {
            case -100 -> "LMB";
            case -99 -> "RMB";
            case -98 -> "WMB";
            case Keyboard.KEY_NONE -> I18n.format(keybind.getKeyDescription());
            case Keyboard.KEY_LCONTROL -> "LCTRL";
            case Keyboard.KEY_RCONTROL -> "RCTRL";
            default -> GameSettings.getKeyDisplayString(keybind.getKeyCode());
        };
    }

    /**
     * Registers the given subcommand keybindings to
     * the keybind array using {@link ClientRegistry#registerKeyBinding(KeyBinding)},
     * if they have any.
     *
     * @param subCommands A collection of subcommands that may or may not have keybinds to register
     */
    public static void registerKeybinds(Collection<FPKSubCommand> subCommands) {
        for (FPKSubCommand subCommand : subCommands) {
            if (subCommand.keybind != null) {
                ClientRegistry.registerKeyBinding(subCommand.keybind);
            }
        }
    }
}
