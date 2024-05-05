package me.oondanomala.spkmod.commands.subcommands;

import me.oondanomala.spkmod.commands.SPKSubCommand;
import me.oondanomala.spkmod.gui.LabelGUI;
import me.oondanomala.spkmod.util.GuiUtil;

public class LabelGUICommand extends SPKSubCommand {
    public LabelGUICommand() {
        super("gui", "Opens the label config GUI.");
    }

    @Override
    protected void internalRun(String[] args) {
        GuiUtil.displayGui(new LabelGUI());
    }
}
