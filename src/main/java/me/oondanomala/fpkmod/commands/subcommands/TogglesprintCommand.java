package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.commands.FPKSubCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class TogglesprintCommand extends FPKSubCommand {
    public TogglesprintCommand() {
        super("togglesprint", "Enables/Disables togglesprint.", "Togglesprint");
    }

    @Override
    protected void internalRun(String[] args) {
        FPKMod.config.sprintToggled = !FPKMod.config.sprintToggled;
        FPKMod.config.setConfigOption("togglesprint", FPKMod.config.sprintToggled);

        if (!FPKMod.config.sprintToggled) {
            KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
        }
    }
}
