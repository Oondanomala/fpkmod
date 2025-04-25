package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.landingblock.LandingBlock;
import me.oondanomala.fpkmod.util.CommandUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;

public class SetCondCommand extends FPKSubCommand {
    public SetCondCommand() {
        super("setcond", "Sets a landing block's condition box.");
    }

    @Override
    protected void internalRun(String[] args) throws CommandException {
        // TODO: Multiple landing blocks
        if (args.length != 4) {
            throw new SyntaxErrorException();
        }
        double minX = CommandUtil.parseDouble(args[0]);
        double maxX = CommandUtil.parseDouble(args[1]);
        double minZ = CommandUtil.parseDouble(args[2]);
        double maxZ = CommandUtil.parseDouble(args[3]);

        LandingBlock landingBlock = LBManager.getSelectedLandingBlock();
        if (landingBlock == null) {
            throw new CommandException("No landing block set.");
        }
        landingBlock.setCond(minX, minZ, maxX, maxZ);
        TextUtil.showChatMessage("Set landing block condition box.");
    }

    @Override
    protected String getUsage() {
        return "<minX> <maxX> <minZ> <maxZ>";
    }

    @Override
    public boolean canSignExecute() {
        return true;
    }
}
