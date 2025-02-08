package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.landingblock.LandingBlock;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.command.CommandException;

public class ClearPBCommand extends FPKSubCommand {
    public ClearPBCommand() {
        super("clearpb", "Clears a landing block PB.");
    }

    @Override
    protected void internalRun(String[] args) throws CommandException {
        // TODO: Multiple landing blocks
        LandingBlock landingBlock = LBManager.getSelectedLandingBlock();
        if (landingBlock == null) {
            throw new CommandException("No landing block set.");
        }
        landingBlock.pbOffset = null;
        TextUtil.showChatMessage("Cleared landing block PB.");
    }
}
