package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.gui.LabelGUI;
import me.oondanomala.fpkmod.util.GuiUtil;

public class LabelGUICommand extends FPKSubCommand {
    public LabelGUICommand() {
        super("gui", "Opens the label config GUI.");
    }

    @Override
    protected void internalRun(String[] args) {
        GuiUtil.displayGui(new LabelGUI());
    }
}
