package me.oondanomala.spkmod.commands;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.commands.subcommands.DecimalPrecisionCommand;
import me.oondanomala.spkmod.commands.subcommands.HelpCommand;
import me.oondanomala.spkmod.commands.subcommands.LabelGUICommand;
import me.oondanomala.spkmod.commands.subcommands.OpenConfigCommand;
import me.oondanomala.spkmod.commands.subcommands.ResetLabelsCommand;
import me.oondanomala.spkmod.commands.subcommands.ToggleLabelsCommand;
import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SPKMainCommand extends CommandBase {
    public static final SPKMainCommand instance = new SPKMainCommand();
    private final HashMap<String, SPKSubCommand> subCommands;
    public final List<SPKSubCommand> subCommandList;

    public SPKMainCommand() {
        subCommands = new HashMap<>();
        subCommandList = new ArrayList<>();
        registerSubCommands(
                new HelpCommand(),
                new DecimalPrecisionCommand(),
                new OpenConfigCommand(),
                new LabelGUICommand(),
                new ToggleLabelsCommand(),
                new ResetLabelsCommand()
        );
    }

    private void registerSubCommands(SPKSubCommand... subCommands) {
        for (SPKSubCommand subCommand : subCommands) {
            this.subCommands.put(subCommand.name, subCommand);
            this.subCommandList.add(subCommand);
        }
        subCommandList.sort(Comparator.comparing(o -> o.name));
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        String[] lowercaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);

        if (lowercaseArgs.length == 0) {
            subCommands.get("help").run(lowercaseArgs);
        } else {
            SPKSubCommand subCommand = subCommands.get(lowercaseArgs[0]);
            if (subCommand == null) {
                TextUtil.showChatMessage("Unknown command. Try /spk help.");
            } else {
                subCommand.run(Arrays.copyOfRange(lowercaseArgs, 1, lowercaseArgs.length));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, subCommands.keySet());
        }
        return Collections.emptyList();
    }

    @Override
    public String getCommandName() {
        return "spk";
    }

    @Override
    public List<String> getCommandAliases() {
        if (SPKMod.config.mpkCommand) {
            return Collections.singletonList("mpk");
        }
        return Collections.emptyList();
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/spk help for help";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
