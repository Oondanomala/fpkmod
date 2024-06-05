package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.util.TextUtil;

public class ToggleLabelsCommand extends FPKSubCommand {
    public ToggleLabelsCommand() {
        super("toggleall", "Toggles visibility of all labels.");
    }

    @Override
    protected void internalRun(String[] args) {
        FPKMod.config.enableLabels = !FPKMod.config.enableLabels;
        FPKMod.config.setConfigOption("Enable Labels", FPKMod.config.enableLabels);
        TextUtil.showChatMessage("Labels are now " + (FPKMod.config.enableLabels ? "enabled" : "disabled") + ".");
    }
}
