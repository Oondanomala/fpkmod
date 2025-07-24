package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.util.CommandUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;

public class AntiCPCommand extends FPKSubCommand {
    public AntiCPCommand() {
        super("anticp", "Toggles antiCP and changes its duration.");
    }

    @Override
    protected void internalRun(String[] args) throws CommandException {
        if (args.length > 1) {
            throw new SyntaxErrorException();
        }

        if (args.length == 0) {
            FPKMod.config.enableAntiCP = !FPKMod.config.enableAntiCP;
            FPKMod.config.setConfigOption("AntiCP", FPKMod.config.enableAntiCP);
            TextUtil.showChatMessage("AntiCP is now " + (FPKMod.config.enableAntiCP ? "enabled" : "disabled") + ".");
        } else {
            double antiCPDuration = CommandUtil.parseDouble(args[0]);
            if (antiCPDuration < 0) {
                throw new CommandException("Minimum antiCP duration is 0.");
            } else if (antiCPDuration > 20) {
                throw new CommandException("Maximum antiCP duration is 20.");
            }

            FPKMod.config.antiCPTime = (int) (antiCPDuration * 1000);
            FPKMod.config.setConfigOption("AntiCP Time", antiCPDuration);
            TextUtil.showChatMessage("Changed antiCP duration to " + antiCPDuration + " seconds.");
        }
        FPKMod.config.saveConfig();
    }

    @Override
    protected String getUsage() {
        return "[duration]";
    }
}
