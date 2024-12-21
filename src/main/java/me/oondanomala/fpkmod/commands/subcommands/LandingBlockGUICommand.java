package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.gui.LandingBlockGUI;
import me.oondanomala.fpkmod.util.GuiUtil;

public class LandingBlockGUICommand extends FPKSubCommand {
    public LandingBlockGUICommand() {
        super("lb", "Opens the landing block GUI.");
    }

    @Override
    protected void internalRun(String[] args) {
        GuiUtil.displayGui(new LandingBlockGUI());
    }
}
