package me.oondanomala.fpkmod.commands;

import me.oondanomala.fpkmod.util.TextUtil;
import net.minecraft.command.CommandException;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.util.EnumChatFormatting;

/**
 * Implement this class to add a new subcommand to {@code /fpk}.
 * Don't forget to register it in {@link FPKMainCommand}'s constructor!
 */
public abstract class FPKSubCommand {
    public final String name;
    public final String helpMessage;

    /**
     * Constructs a new subcommand with the provided name and help message.
     *
     * @param name        The name of the subcommand, e.g. {@code /fpk command}
     * @param helpMessage The text that will be displayed in the {@code /fpk help} subcommand
     */
    protected FPKSubCommand(String name, String helpMessage) {
        this.name = name;
        this.helpMessage = helpMessage;
    }

    public void run(String[] args) {
        try {
            internalRun(args);
        } catch (SyntaxErrorException e) {
            TextUtil.showChatMessage(EnumChatFormatting.RED + "/fpk " + name + " " + e.getMessage());
        } catch (CommandException e) {
            TextUtil.showChatMessage(e.getMessage());
        }
    }

    /**
     * Called when the subcommand is run.
     *
     * @param args The subcommand arguments
     * @throws SyntaxErrorException When a syntax error occurs in the command. The exception message will be shown in chat, formatted appropriately
     * @throws CommandException     When a generic error occurs in the command. The exception message will be sent in chat, without additional formatting
     */
    protected abstract void internalRun(String[] args) throws CommandException;
}
