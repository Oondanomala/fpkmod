package me.oondanomala.spkmod.commands;

import me.oondanomala.spkmod.util.TextUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.util.EnumChatFormatting;

/**
 * Implement this class to add a new subcommand to {@code /spk}.
 * Don't forget to register it in {@link SPKMainCommand}'s constructor!
 */
public abstract class SPKSubCommand {
    public final String name;
    public final String helpMessage;

    /**
     * Constructs a new subcommand with the provided name and help message.
     *
     * @param name        The name of the subcommand, e.g. {@code /spk command}
     * @param helpMessage The text that will be displayed in the {@code /spk help} subcommand
     */
    protected SPKSubCommand(String name, String helpMessage) {
        this.name = name;
        this.helpMessage = helpMessage;
    }

    public void run(String[] args) {
        try {
            internalRun(args);
        } catch (SyntaxErrorException e) {
            TextUtil.showChatMessage(EnumChatFormatting.RED + "/mpk " + name + " " + e.getMessage());
        } catch (CommandException e) {
            TextUtil.showChatMessage(e.getMessage());
        }
    }

    /**
     * Called when the subcommand is run.
     *
     * @param args The subcommand arguments
     * @throws CommandException When an error occurs in the command. The exception message will be shown in chat
     */
    protected abstract void internalRun(String[] args) throws CommandException;
}
