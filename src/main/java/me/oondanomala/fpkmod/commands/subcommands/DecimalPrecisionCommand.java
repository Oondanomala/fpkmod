package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.util.CommandUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;

public class DecimalPrecisionCommand extends FPKSubCommand {
    public DecimalPrecisionCommand() {
        super("df", "Changes the decimal precision.");
    }

    @Override
    public void internalRun(String[] args) throws CommandException {
        if (args.length != 1) {
            throw new SyntaxErrorException();
        }

        int decimals = CommandUtil.parseInt(args[0]);
        if (decimals < 0) {
            throw new CommandException("Minimum coord precision is 0 decimals.");
        }
        if (decimals > 16) {
            throw new CommandException("Maximum coord precision is 16 decimals.");
        }

        FPKMod.config.doublePrecision = decimals;
        FPKMod.config.setConfigOption("Coord Precision", decimals);
        FPKMod.config.saveConfig();
        TextUtil.setDecimalPrecision(decimals, FPKMod.config.trimZeroes);
        TextUtil.showChatMessage("Changed coord precision to " + decimals + " decimals.");
    }

    @Override
    protected String getUsage() {
        return "<precision>";
    }
}
