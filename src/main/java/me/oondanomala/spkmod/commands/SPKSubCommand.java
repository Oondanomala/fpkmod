package me.oondanomala.spkmod.commands;

import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.command.CommandException;
import net.minecraft.util.EnumChatFormatting;

public abstract class SPKSubCommand {
    public final String name;
    public final String helpMessage;

    public SPKSubCommand(String name, String helpMessage) {
        this.name = name;
        this.helpMessage = helpMessage;
    }

    public void run(String[] args) {
        try {
            internalRun(args);
        } catch (CommandException e) {
            TextUtil.showChatMessage(EnumChatFormatting.RED + "/mpk " + name + " " + e.getMessage());
        }
    }

    protected abstract void internalRun(String[] args) throws CommandException;
}
