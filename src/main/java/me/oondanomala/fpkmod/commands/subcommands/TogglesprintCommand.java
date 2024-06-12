package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class TogglesprintCommand extends FPKSubCommand {
    public static boolean sprintToggled;

    public TogglesprintCommand() {
        super("togglesprint", "Enables/Disables togglesprint.", "Togglesprint");
    }

    @Override
    protected void internalRun(String[] args) {
        // TODO: Save toggle state on close
        sprintToggled = !sprintToggled;
        if (!sprintToggled) {
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
        }
    }
}
