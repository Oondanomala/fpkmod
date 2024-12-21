package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.util.TextUtil;

public class ClearLBCommand extends FPKSubCommand {
    public ClearLBCommand() {
        super("clearlb", "Clears the landing blocks.");
    }

    @Override
    protected void internalRun(String[] args) {
        LBManager.clearLandingBlocks();
        TextUtil.showChatMessage("Cleared landing block.");
        // TODO: Handle multiple landing blocks
    }
}
