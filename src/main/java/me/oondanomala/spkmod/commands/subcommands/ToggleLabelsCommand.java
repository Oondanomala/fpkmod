package me.oondanomala.spkmod.commands.subcommands;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.commands.SPKSubCommand;
import me.oondanomala.spkmod.util.TextUtil;

public class ToggleLabelsCommand extends SPKSubCommand {
    public ToggleLabelsCommand() {
        super("toggleall", "Toggles visibility of all labels.");
    }

    @Override
    protected void internalRun(String[] args) {
        SPKMod.config.enableLabels = !SPKMod.config.enableLabels;
        SPKMod.config.setConfigOption("Enable Labels", SPKMod.config.enableLabels);
        TextUtil.showChatMessage("Labels are now " + (SPKMod.config.enableLabels ? "enabled" : "disabled") + ".");
    }
}
