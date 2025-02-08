package me.oondanomala.fpkmod.commands.subcommands;

import me.oondanomala.fpkmod.FPKMod;
import me.oondanomala.fpkmod.commands.FPKSubCommand;
import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.util.EnumChatFormatting;

public class HelpCommand extends FPKSubCommand {
    public HelpCommand() {
        super("help", "Sends a list of all commands and their help text in the chat.");
    }

    @Override
    public void internalRun(String[] args) {
        TextUtil.showChatMessage(FPKMod.config.color1 + EnumChatFormatting.BOLD + "FPK Command Help:", false);
        for (FPKSubCommand command : FPKMod.fpkCommand.subCommands.values()) {
            TextUtil.showChatMessage(TextUtil.assembleText(command.name, command.helpMessage, ": "), false);
        }
    }
}
