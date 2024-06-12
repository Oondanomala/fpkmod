package me.oondanomala.fpkmod.labels.misc;

import me.oondanomala.fpkmod.commands.subcommands.TogglesprintCommand;
import me.oondanomala.fpkmod.labels.Label;

public class LabelTogglesprint extends Label {
    public LabelTogglesprint() {
        super("Togglesprint");
    }

    @Override
    protected String getLabelText() {
        return TogglesprintCommand.sprintToggled ? "Enabled" : "Disabled";
    }
}
