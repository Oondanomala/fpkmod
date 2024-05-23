package me.oondanomala.spkmod.commands;

import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.command.CommandException;
import net.minecraft.util.EnumChatFormatting;

public abstract class SPKSubCommand {
    public final String name;
    public final String helpMessage;

    /**
     * Constructs a new subcommand with the provided name and help message.
     *
     * @param name        The name of the subcommand, e.g. {@code /spk name}
     * @param helpMessage The text that will be displayed in the {@code /spk help} subcommand
     */
    protected SPKSubCommand(String name, String helpMessage) {
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
