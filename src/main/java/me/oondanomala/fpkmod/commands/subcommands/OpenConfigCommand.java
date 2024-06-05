package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.config.FPKConfigGUI;
import me.oondanomala.fpkmod.util.GuiUtil;

public class OpenConfigCommand extends FPKSubCommand {
    public OpenConfigCommand() {
        super("config", "Opens the config menu.");
    }

    @Override
    protected void internalRun(String[] args) {
        GuiUtil.displayGui(new FPKConfigGUI(null));
    }
}
