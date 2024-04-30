package me.oondanomala.spkmod.commands;

import me.oondanomala.spkmod.util.TextUtil;

public class HelpCommand extends SPKSubCommand {
    public HelpCommand() {
        super("help", "Sends a list of all commands in the chat.");
    }

    @Override
    public void internalRun(String[] args) {
        TextUtil.showChatMessage("§lSPK Command Help:", false);
        for (SPKSubCommand command : SPKMainCommand.instance.subCommandList) {
            TextUtil.showChatMessage(TextUtil.assembleText(command.name, command.helpMessage, ": "), false);
        }
    }
}
