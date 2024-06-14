package me.oondanomala.fpkmod.labels.misc;

import me.oondanomala.fpkmod.commands.subcommands.TogglesprintCommand;
import me.oondanomala.fpkmod.labels.TextLabel;

public class LabelTogglesprint extends TextLabel {
    public LabelTogglesprint() {
        super("Togglesprint");
    }

    @Override
    protected String getLabelText() {
        return TogglesprintCommand.sprintToggled ? "Enabled" : "Disabled";
    }
}
