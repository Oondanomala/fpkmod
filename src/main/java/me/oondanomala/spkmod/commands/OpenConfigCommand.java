package me.oondanomala.spkmod.commands;

import me.oondanomala.spkmod.config.SPKConfigGUI;
import me.oondanomala.spkmod.util.GuiUtil;

public class OpenConfigCommand extends SPKSubCommand {
    public OpenConfigCommand() {
        super("config", "Opens the config menu.");
    }

    @Override
    protected void internalRun(String[] args) {
        GuiUtil.displayGui(new SPKConfigGUI(null));
    }
}
