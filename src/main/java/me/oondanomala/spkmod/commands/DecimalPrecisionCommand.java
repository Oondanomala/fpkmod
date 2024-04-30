package me.oondanomala.spkmod.commands;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.command.CommandException;
import net.minecraftforge.common.config.Configuration;

public class DecimalPrecisionCommand extends SPKSubCommand {
    public DecimalPrecisionCommand() {
        super("df", "Changes the decimal precision.");
    }

    @Override
    public void internalRun(String[] args) throws CommandException {
        if (args.length != 1) {
            throw new CommandException("<precision>");
        }
        try {
            int decimals = Integer.parseInt(args[0]);
            if (decimals < 0) {
                TextUtil.showChatMessage("Minimum coord precision is 0 decimals.");
            } else if (decimals > 16) {
                TextUtil.showChatMessage("Maximum coord precision is 16 decimals.");
            } else {
                // TODO: Proper config system?
                SPKMod.config.doublePrecision = decimals;
                SPKMod.config.configuration.get(Configuration.CATEGORY_CLIENT, "Coord Precision", 5).setMaxValue(16).setMinValue(0).set(decimals);
                SPKMod.config.configuration.save();
                TextUtil.showChatMessage("Changed coord precision to " + decimals + " decimals");
            }
        } catch (NumberFormatException e) {
            throw new CommandException("<precision>");
        }
    }
}
