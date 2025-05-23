package me.oondanomala.fpkmod.commands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.commands.subcommands.*;
import me.oondanomala.fpkmod.util.KeyBindUtil;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FPKMainCommand extends CommandBase {
    public final Map<String, FPKSubCommand> subCommands;

    public FPKMainCommand() {
        FPKSubCommand[] subCommands = new FPKSubCommand[]{
                new HelpCommand(),
                new DecimalPrecisionCommand(),
                new FlyCommand(),
                new TogglesprintCommand(),
                new ClearMaxSpeedCommand(),
                new TeleportCommand(),
                new CoordsCommand(),
                new OpenConfigCommand(),
                new ReloadConfigCommand(),
                new LabelGUICommand(),
                new LandingBlockGUICommand(),
                new ToggleLabelsCommand(),
                new ResetLabelsCommand(),
                new SetLBCommand(),
                new SetCondCommand(),
                new ClearPBCommand(),
                new ClearLBCommand()
        };
        Arrays.sort(subCommands, Comparator.comparing(c -> c.name));

        Map<String, FPKSubCommand> subCommandMap = new LinkedHashMap<>();
        for (FPKSubCommand subCommand : subCommands) {
            subCommandMap.put(subCommand.name, subCommand);
        }
        this.subCommands = Collections.unmodifiableMap(subCommandMap);

        KeyBindUtil.registerKeybinds(this.subCommands.values());
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        String[] lowercaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);

        if (lowercaseArgs.length == 0) {
            subCommands.get("help").run(lowercaseArgs);
        } else {
            FPKSubCommand subCommand = subCommands.get(lowercaseArgs[0]);
            if (subCommand == null) {
                TextUtil.showChatMessage("Unknown command. Try /fpk help.");
            } else {
                subCommand.run(Arrays.copyOfRange(lowercaseArgs, 1, lowercaseArgs.length));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, subCommands.keySet());
        } else {
            FPKSubCommand subCommand = subCommands.get(args[0]);
            if (subCommand != null) {
                String[] lowercaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
                return getListOfStringsMatchingLastWord(lowercaseArgs, subCommand.getTabCompletions(Arrays.copyOfRange(lowercaseArgs, 1, lowercaseArgs.length - 1)));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public String getCommandName() {
        return "fpk";
    }

    @Override
    public List<String> getCommandAliases() {
        if (FPKMod.config.mpkCommand) {
            return Collections.singletonList("mpk");
        }
        return Collections.emptyList();
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/fpk help for help";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
