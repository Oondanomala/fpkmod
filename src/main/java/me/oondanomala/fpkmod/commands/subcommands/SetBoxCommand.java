package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.landingblock.LBManager;
import me.oondanomala.fpkmod.landingblock.LandingBlock;
import me.oondanomala.fpkmod.util.CommandUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.util.AxisAlignedBB;

public class SetBoxCommand extends FPKSubCommand {
    public SetBoxCommand() {
        super("setbox", "Sets the landing block's landing box");
    }

    @Override
    protected void internalRun(String[] args) throws CommandException {
        if (args.length != 6) {
            throw new SyntaxErrorException();
        }
        // Can't change the argument order to keep compatibility
        // with MPK command signs
        double minX = CommandUtil.parseDouble(args[0]);
        double maxX = CommandUtil.parseDouble(args[1]);
        double minY = CommandUtil.parseDouble(args[2]);
        double maxY = CommandUtil.parseDouble(args[3]);
        double minZ = CommandUtil.parseDouble(args[4]);
        double maxZ = CommandUtil.parseDouble(args[5]);

        LandingBlock landingBlock = LBManager.getSelectedLandingBlock();
        if (landingBlock == null) {
            throw new CommandException("No landing block set.");
        }
        landingBlock.setLandingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
        TextUtil.showChatMessage("Set landing block landing box.");
    }

    @Override
    protected String getUsage() {
        return "<minX> <maxX> <minY> <maxY> <minZ> <maxZ>";
    }

    @Override
    public boolean canSignExecute() {
        return true;
    }
}
