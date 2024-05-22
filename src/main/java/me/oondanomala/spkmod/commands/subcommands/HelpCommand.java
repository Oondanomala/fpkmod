package me.oondanomala.spkmod.commands.subcommands;

import me.oondanomala.spkmod.SPKMod;
import me.oondanomala.spkmod.commands.SPKMainCommand;
import me.oondanomala.spkmod.commands.SPKSubCommand;
import me.oondanomala.spkmod.util.TextUtil;

public class HelpCommand extends SPKSubCommand {
    public HelpCommand() {
        super("help", "Sends a list of all commands and their help text in the chat.");
    }

    @Override
    public void internalRun(String[] args) {
        TextUtil.showChatMessage(SPKMod.config.color1 + "§lSPK Command Help:", false);
        for (SPKSubCommand command : SPKMainCommand.instance.subCommandList) {
            TextUtil.showChatMessage(TextUtil.assembleText(command.name, command.helpMessage, ": "), false);
        }
    }
}
