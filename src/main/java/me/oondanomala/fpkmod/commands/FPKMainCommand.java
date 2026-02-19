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
                new AntiCPCommand(),
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
        Arrays.setAll(args, i -> args[i].toLowerCase());
        if (args.length == 0) {
            subCommands.get("help").run(args);
        } else {
            FPKSubCommand subCommand = subCommands.get(args[0]);
            if (subCommand == null) {
                TextUtil.showChatMessage("Unknown command. Try /fpk help.");
            } else {
                subCommand.run(Arrays.copyOfRange(args, 1, args.length));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, subCommands.keySet());
        } else {
            Arrays.setAll(args, i -> args[i].toLowerCase());
            FPKSubCommand subCommand = subCommands.get(args[0]);
            if (subCommand != null) {
                return getListOfStringsMatchingLastWord(args, subCommand.getTabCompletions(Arrays.copyOfRange(args, 1, args.length - 1)));
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
